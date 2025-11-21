/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkListener;

import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * NetworkListener abre un puerto para escuchar activamente mensajes de otros clientes
 * a través de un ServerSocket.
 *
 * - Por cada conexión entrante crea un hilo que lee el mensaje
 *   y lo agrega a la cola de entrada.
 * - De esta forma, el hilo principal del listener puede volver rápido a accept().
 */
public class NetworkListener implements Runnable {

    // Puerto en el que escuchará el servidor
    private final int puerto;

    // Cola donde se encolarán los mensajes recibidos (por ejemplo como JSON String)
    private final ColaDePrioridad<String> colaEntrada;

    // ServerSocket para aceptar conexiones
    private ServerSocket serverSocket;

    // Bandera para controlar el ciclo del servidor
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * Constructor del NetworkListener.
     *
     * @param puerto      Puerto utilizado para crear el ServerSocket
     * @param colaEntrada Cola donde se almacenarán los mensajes entrantes
     */
    public NetworkListener(int puerto, ColaDePrioridad<String> colaEntrada) {
        this.puerto = puerto;
        this.colaEntrada = colaEntrada;
    }

    /**
     * Inicia el servidor en un hilo separado.
     */
    public void start() {
        if (running.compareAndSet(false, true)) {
            Thread t = new Thread(this, "NetworkListener-Thread");
            t.start();
            System.out.println("[NetworkListener] Servidor iniciado en puerto " + puerto);
        }
    }

    /**
     * Detiene el servidor cerrando el ServerSocket.
     */
    public void stop() {
        running.set(false);
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Esto hará que el accept() lance IOException y salga del ciclo
            }
        } catch (IOException e) {
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("[NetworkListener] Servidor detenido.");
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(puerto)) {
            this.serverSocket = ss;

            while (running.get()) {
                System.out.println("[NetworkListener] Escuchando en puerto " + puerto + "...");

                // Acepta la conexión de un cliente (bloqueante)
                Socket socket = ss.accept();
                System.out.println("[NetworkListener] Cliente conectado: " + socket.getRemoteSocketAddress());

                // Crear un hilo para manejar al cliente
                Thread clienteThread = new Thread(() -> manejarCliente(socket),
                        "NetworkListener-ClientHandler");
                clienteThread.start();
            }

        } catch (IOException e) {
            if (running.get()) {
                // Si running aún es true, el error no fue por stop()
                System.err.println("[NetworkListener] Error de servidor: " + e.getMessage());
                Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, e);
            } else {
                // Si ya no está corriendo, es probable que sea por un stop() y el cierre del socket
                System.out.println("[NetworkListener] ServerSocket cerrado, fin de run().");
            }
        }
    }

    /**
     * Maneja la comunicación con un cliente:
     * - Lee una línea (un mensaje)
     * - La agrega a la cola de entrada en este mismo hilo
     * - Cierra el socket al final
     */
    private void manejarCliente(Socket socket) {
        try (Socket s = socket;
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8))) {

            // Leer UNA línea (asumiendo que cada mensaje llega como una línea)
            String mensaje = reader.readLine();

            if (mensaje != null) {
                System.out.println("[NetworkListener] Mensaje recibido: " + mensaje);

                // Aquí podrías delegar a otro hilo también si quisieras,
                // pero normalmente con esto basta: este hilo ya es “independiente”.
                try {
                    // Ojo con el TipoAdd: si es cola de ENTRADA, quizá use TipoAdd.Entrada
                    colaEntrada.add(mensaje, TipoAdd.Entrada); 
                    System.out.println("[NetworkListener] Mensaje encolado en cola de entrada.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("[NetworkListener] Hilo de cliente interrumpido al encolar.");
                }
            } else {
                System.out.println("[NetworkListener] Cliente cerró conexión sin enviar datos.");
            }

        } catch (IOException e) {
            System.err.println("[NetworkListener] Error con cliente: " + e.getMessage());
            Logger.getLogger(NetworkListener.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            System.out.println("[NetworkListener] Conexión con cliente cerrada.");
        }
    }
}

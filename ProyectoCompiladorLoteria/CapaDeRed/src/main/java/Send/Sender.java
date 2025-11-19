/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Send;

import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverSalida;
import interfaces.ISender;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Clase Sender
 *
 * - Implementa ISender para enviar mensajes por TCP.
 * - Implementa ObserverSalida para escuchar el flujo de salida.
 * - Envía cada mensaje como una línea de texto (JSON por línea).
 */
public class Sender implements ISender, ObserverSalida {

    private final Socket socket;
    private final ColaDePrioridad<String> colaSalida;

    private BufferedWriter writer;
    private volatile boolean conectado;
    private final Object lock = new Object();

    /**
     * Constructor.
     * @param socket Socket ya conectado.
     * @param colaSalida Cola desde la que se leerán los mensajes a enviar.
     */
    public Sender(Socket socket, ColaDePrioridad<String> colaSalida) {
        this.socket = socket;
        this.colaSalida = colaSalida;
        inicializar();
    }

    /**
     * Inicializa el writer sobre el socket.
     */
    private void inicializar() {
        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)
            );
            this.conectado = true;
            System.out.println("[Sender] Conectado. Listo para enviar mensajes.");
        } catch (IOException e) {
            this.conectado = false;
            System.err.println("[Sender] No se pudo obtener el OutputStream: " + e.getMessage());
        }
    }

    /**
     * Envía un mensaje JSON por el socket como una línea de texto.
     * @param json Mensaje serializado.
     */
    @Override
    public void send(String json) {
        if (!conectado || socket == null || socket.isClosed()) {
            System.err.println("[Sender] No conectado. Mensaje no enviado: " + json);
            return;
        }

        synchronized (lock) {
            try {
                writer.write(json);
                writer.newLine();   // MUY IMPORTANTE para que readLine() lo lea
                writer.flush();
                System.out.println("[Sender] Mensaje enviado: " + json);
            } catch (IOException e) {
                System.err.println("[Sender] Error al enviar mensaje: " + e.getMessage());
                conectado = false;
                close(); // en servidor normalmente cerramos la conexión
            }
        }
    }

    /** Cierra la conexión TCP. */
    @Override
    public void close() {
        conectado = false;
        try {
            if (writer != null) writer.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("[Sender] Conexión cerrada correctamente.");
        } catch (IOException e) {
            System.err.println("[Sender] Error al cerrar conexión: " + e.getMessage());
        }
    }

    /**
     * Método llamado por la cola cuando se agrega un nuevo mensaje de salida.
     */
    @Override
    public void updateSalida() {
        try {
            String mensaje = colaSalida.take();
            send(mensaje);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("[Sender] Hilo de envío interrumpido.");
        }
    }
}

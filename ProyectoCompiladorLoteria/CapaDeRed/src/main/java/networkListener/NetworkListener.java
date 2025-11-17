/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkListener;

import ColaGenerica.ColaGenerica;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import utilidades.TipoAddCola;

/**
 * NetworkListener se encarga de escuchar mensajes provenientes del broker (o
 * servidor) a traves de un socket. Cada mensaje recibido se agrega a la cola de
 * entrada, que luego sera observada por el Dispatcher.
 */
public class NetworkListener implements Runnable {

    // Referencia a la cola donde se encolaran los mensajes recibidos
    private final ColaDePrioridad<String> colaEntrada;

    // Socket de conexion
    private final Socket socket;

    // Flujo de entrada para leer mensajes
    private BufferedReader entrada;

    // Controla si el hilo sigue activo
    private volatile boolean activo = true;

    /**
     * Constructor: inicializa el listener con el socket y la cola de entrada.
     *
     * @param socket Socket de conexion
     * @param colaEntrada Cola donde se almacenaran los mensajes entrantes
     */
    public NetworkListener(Socket socket, ColaDePrioridad<String> colaEntrada) {
        this.socket = socket;
        this.colaEntrada = colaEntrada;
    }

    /**
     * Metodo que se ejecuta en un hilo separado. Lee continuamente los mensajes
     * entrantes en formato JSON y los encola en la cola de entrada.
     */
    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {

            System.out.println("[NetworkListener] Esperando mensajes...");

            String mensaje;
            // Si llega un mensaje, lo agregamos a la cola
            while (activo && (mensaje = entrada.readLine()) != null) {
                System.out.println("[NetworkListener] Mensaje recibido: " + mensaje);
                try {
                    colaEntrada.add(mensaje, TipoAdd.Entrada);
                } catch (Exception ex) {
                    System.err.println(ex);
                }
            }

        } catch (IOException e) {
            if (activo) {
                System.err.println("[NetworkListener] Error de conexion: " + e.getMessage());
            } else {
                System.out.println("[NetworkListener] Hilo detenido correctamente.");
            }
        }
    }

    /**
     * Detiene la escucha y cierra los recursos asociados al socket.
     */
    public void detener() {
        activo = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("[NetworkListener] Error al cerrar socket: " + e.getMessage());
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Send;

import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverSalida;
import eventoRed.EventoRed;
import interfaces.ISender;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Sender
 *
 * - Implementa ISender para enviar mensajes por TCP.
 * - Implementa ObserverSalida para escuchar el flujo de salida.
 * - Envía cada mensaje como una línea de texto (JSON por línea).
 * - Crea un socket nuevo por cada mensaje y lo cierra al terminar.
 */
public class Sender implements ISender, ObserverSalida {

    // Cola desde la que se leerán los eventos de red a enviar
    private final ColaDePrioridad<EventoRed> colaSalida;

    // Lock para sincronizar el envío (por si se usa desde varios hilos)
    private final Object lock = new Object();

    /**
     * Constructor.
     *
     * @param colaSalida Cola desde la que se leerán los mensajes a enviar.
     */
    public Sender(ColaDePrioridad<EventoRed> colaSalida) {
        this.colaSalida = colaSalida;
    }

    @Override
    public void send(EventoRed evento) {
        // Creamos el Socket y el Writer como recursos locales
        try (Socket socket = new Socket(evento.getIpDestino(), evento.getPuertoDestino());
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))
        ) {
            System.out.println("[Sender] Conectado a " + evento.getIpDestino()
                    + ":" + evento.getPuertoDestino() + ". Listo para enviar.");

            // Sincronizamos por si varios hilos llaman send al mismo tiempo
            synchronized (lock) {
                // Escribimos el JSON del evento
                writer.write(evento.getEvento());
                writer.newLine();   // MUY IMPORTANTE para que el servidor pueda usar readLine()
                writer.flush();

                System.out.println("[Sender] Mensaje enviado: " + evento.getEvento());
            }

            // Al salir del try, writer y socket se cierran automáticamente

        } catch (IOException e) {
            System.err.println("[Sender] Error al enviar mensaje: " + e.getMessage());
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Método llamado por la cola cuando se agrega un nuevo mensaje de salida.
     * Toma un evento de la cola (bloqueante) y lo envía.
     */
    @Override
    public void updateSalida() {
        try {
            // Bloquea hasta que haya un evento disponible
            EventoRed evento = colaSalida.take();
            send(evento);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.err.println("[Sender] Hilo de envío interrumpido.");
        }
    }
}

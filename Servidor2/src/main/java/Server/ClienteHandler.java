/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import ConvertidorJSON.ToJSON;
import java.io.*;
import java.net.Socket;

public class ClienteHandler implements Runnable {

    private final Socket socket;
    private final ColaGenerica<Evento> colaEnvio = new ColaGenerica<>();
    private final ColaGenerica<String> colaRecepcion = new ColaGenerica<>();

    public ClienteHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
 
    try {
        NetworkFactory factory = NetworkFactory.getInstancia();
        Sender sender = factory.crearSender(socket);
        Dispatcher dispatcher = factory.crearDispatcher(colaEnvio, sender);
        ReceiverHelper receiver = factory.crearReceiverHelper(socket, colaRecepcion);

        new Thread(dispatcher).start();
        new Thread(receiver).start();

        while (true) {
            String json = colaRecepcion.take();

            // 🔒 Evita intentar convertir líneas vacías o nulas
            if (json == null || json.trim().isEmpty()) {
                System.out.println("[Servidor] Mensaje vacío ignorado.");
                continue;
            }

            try {
                Evento evento = ConvertidorJSON.ToJSON.convertirDesdeJson(json);
                System.out.println("[Servidor] Evento recibido: " + evento.getCartaSeleccionada());

                // Lógica de respuesta
                String resultado = evento.getCartaSeleccionada().equalsIgnoreCase("El gallo")
                        ? "ACIERTAS" : "FALLAS";

                Evento respuesta = new Evento(
                        evento.getTipo(),
                        evento.getIdJugador(),
                        resultado,
                        evento.getPosicion()
                );

                colaEnvio.put(respuesta);

            } catch (com.google.gson.JsonSyntaxException e) {
                System.err.println("[Servidor] JSON inválido recibido: " + json);
            }
        }

    } catch (IOException | InterruptedException e) {
        System.err.println("Error con cliente: " + e.getMessage());
    }
}

}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author Arell
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class NetworkFactory {
    private static NetworkFactory instancia;

    private NetworkFactory() {}

    public static synchronized NetworkFactory getInstancia() {
        if (instancia == null)
            instancia = new NetworkFactory();
        return instancia;
    }

    // Crea el Dispatcher
    public Dispatcher crearDispatcher(ColaGenerica<String> colaEnvio, Sender sender) {
        return new Dispatcher(colaEnvio, sender);
    }

    // Crea el ReceiverHelper correctamente
    public ReceiverHelper crearReceiverHelper(Socket socket, ColaGenerica<String> colaRecepcion) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return new ReceiverHelper(reader, colaRecepcion);
    }

    // Crea el Sender
    public Sender crearSender(Socket socket) throws IOException {
        return new Sender(socket.getOutputStream());
    }
}

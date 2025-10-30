/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import java.io.IOException;
import java.net.Socket;

public class NetworkFactory {
    private static NetworkFactory instancia;

    private NetworkFactory() {}

    public static synchronized NetworkFactory getInstancia() {
        if (instancia == null) instancia = new NetworkFactory();
        return instancia;
    }

    public Dispatcher crearDispatcher(ColaGenerica colaEnvio, Sender sender) {
        return new Dispatcher(colaEnvio, sender);
    }

    public ReceiverHelper crearReceiverHelper(Socket socket, ColaGenerica colaRecepcion) throws IOException {
        return new ReceiverHelper(socket, colaRecepcion);
    }

    public Sender crearSender(Socket socket) throws IOException {
        return new Sender(socket.getOutputStream());
    }
}

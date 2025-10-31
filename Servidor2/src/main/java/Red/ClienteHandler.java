/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author Arell
 */

import Brok.BrokerTCP;
import java.io.*;
import java.net.Socket;

public class ClienteHandler implements Runnable {
    private final Socket socket;
    private final ColaGenerica<String> colaIn = new ColaGenerica<>();
    private final ColaGenerica<String> colaOut = new ColaGenerica<>();
    private final Sender sender;
    private final BufferedReader reader;
    private final String id;

    public ClienteHandler(Socket socket, String id) throws IOException {
        this.socket = socket;
        this.id = id;
        this.sender = new Sender(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        new Thread(new Dispatcher(colaOut, sender)).start();
        new Thread(new ReceiverHelper(reader, colaIn)).start();

        try {
            while (true) {
                String mensaje = colaIn.tomar();
                BrokerTCP.procesarMensaje(id, mensaje, this);
            }
        } catch (InterruptedException e) {
            System.err.println("[ClienteHandler] desconectado: " + id);
        }
    }

    public void enviar(String msg) throws InterruptedException {
        colaOut.agregar(msg);
    }

    public String getId() { return id; }
}

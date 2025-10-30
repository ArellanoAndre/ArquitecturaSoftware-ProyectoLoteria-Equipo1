/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReceiverHelper implements Runnable {

    private final ColaGenerica<String> colaRecepcion;
    private final BufferedReader reader;

    public ReceiverHelper(Socket socket, ColaGenerica<String> colaRecepcion) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.colaRecepcion = colaRecepcion;
    }

    @Override
    public void run() {
        try {
            String linea;
            while ((linea = reader.readLine()) != null) {
                colaRecepcion.put(linea.trim());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en ReceiverHelper: " + e.getMessage());
        }
    }
}


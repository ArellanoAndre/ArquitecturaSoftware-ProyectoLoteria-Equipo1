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

public class ReceiverHelper implements Runnable {
    private final BufferedReader in;
    private final ColaGenerica<String> cola;

    public ReceiverHelper(BufferedReader in, ColaGenerica<String> cola) {
        this.in = in;
        this.cola = cola;
    }

    @Override
    public void run() {
        try {
            String linea;
            while ((linea = in.readLine()) != null) {
                cola.agregar(linea);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("[ReceiverHelper] Error: " + e.getMessage());
        }
    }
}
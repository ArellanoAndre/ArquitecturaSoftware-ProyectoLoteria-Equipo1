/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;


// ----------------- ReceiverHelper (Recepción) -----------------
class ReceiverHelper implements Runnable {

    private final BufferedReader reader;
    private final BlockingQueue<String> colaRecepcion;

    public ReceiverHelper(BufferedReader reader, BlockingQueue<String> colaRecepcion) {
        this.reader = reader;
        this.colaRecepcion = colaRecepcion;
    }

    @Override
    public void run() {
        try {
            String linea;
            StringBuilder mensaje = new StringBuilder();
            boolean leyendoJSON = false;

            while ((linea = reader.readLine()) != null) {
                if (linea.contains("{")) {
                    leyendoJSON = true;
                    mensaje.setLength(0);
                }
                if (leyendoJSON) {
                    mensaje.append(linea.trim()).append(" ");
                }
                if (linea.contains("}")) {
                    leyendoJSON = false;
                    colaRecepcion.put(mensaje.toString().trim());
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en Helper: " + e.getMessage());
        }
    }
}
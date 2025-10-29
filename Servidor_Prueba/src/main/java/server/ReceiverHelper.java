
package server;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ReceiverHelper implements Runnable {
    private final Socket socket;
    private final BlockingQueue<String> colaRecepcion;

    public ReceiverHelper(Socket socket, BlockingQueue<String> colaRecepcion) {
        this.socket = socket;
        this.colaRecepcion = colaRecepcion;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String mensaje;
            while ((mensaje = reader.readLine()) != null) {
                colaRecepcion.put(mensaje);
                System.out.println("Recibido del servidor: " + mensaje);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en ReceiverHelper: " + e.getMessage());
        }
    }
}

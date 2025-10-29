/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

// ----------------- ClienteHandler -----------------
public class ClienteHandler implements Runnable {

    private final Socket clienteSocket;
    private final int BUFSIZE;
    private static int cartaCantada; // 1 o 2

    private final BlockingQueue<String> colaEnvio = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> colaRecepcion = new LinkedBlockingQueue<>();

    public ClienteHandler(Socket socket, int BUFSIZE) {
        this.clienteSocket = socket;
        this.BUFSIZE = BUFSIZE;
    }

    @Override
    public void run() {
        try (
                InputStream in = clienteSocket.getInputStream(); OutputStream out = clienteSocket.getOutputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            // Crear e iniciar Dispatcher y ReceiverHelper
            Dispatcher dispatcher = new Dispatcher(colaEnvio, out);
            ReceiverHelper helper = new ReceiverHelper(reader, colaRecepcion);

            new Thread(dispatcher).start();
            new Thread(helper).start();

            // Generar carta cantada aleatoria
            cartaCantada = (int) (Math.random() * 2) + 1;
            String carta = (cartaCantada == 1) ? "El gallo" : "La estrella";
            colaEnvio.put("CARTA_CANTADA:" + carta);

            System.out.println("Carta cantada: " + carta);

            // Procesar mensajes del cliente desde la cola
            while (true) {
                String json = colaRecepcion.take();
                System.out.println("Mensaje recibido: " + json);

                boolean acierto = json.contains(carta);
                String respuesta = acierto ? "ACIERTAS" : "FALLAS";
                colaEnvio.put(respuesta);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error con cliente: " + e.getMessage());
        } finally {
            try {
                clienteSocket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar cliente: " + e.getMessage());
            }
        }
    }
}

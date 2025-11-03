package red;

import java.io.*;
import java.net.*;

public class Receiver implements Runnable {
    private final int puerto;
    private boolean activo = true;
    private final Dispatcher dispatcher;

    public Receiver(int puerto) {
        this.puerto = puerto;
        this.dispatcher = DispatcherFactory.getSingletonInstance();
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("[Receiver] Escuchando en puerto " + puerto);
            while (activo) {
                Socket socket = serverSocket.accept();
                new Thread(() -> manejarCliente(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("[Receiver] Error: " + e.getMessage());
        }
    }

    private void manejarCliente(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String json;
            while ((json = in.readLine()) != null) {
                dispatcher.dispatch(json);
            }
        } catch (IOException e) {
            System.err.println("[Receiver] Error al manejar cliente: " + e.getMessage());
        }
    }

    public void detener() {
        activo = false;
    }
}


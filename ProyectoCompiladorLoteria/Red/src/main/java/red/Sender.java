package red;

import java.io.*;
import java.net.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender {
    private final String host;
    private final int puerto;
    private final BlockingQueue<String> colaEnvio = new LinkedBlockingQueue<>();
    private boolean activo = true;

    public Sender(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
        iniciarHiloEnvio();
    }

    private void iniciarHiloEnvio() {
        new Thread(() -> {
            while (activo) {
                try {
                    String json = colaEnvio.take();
                    enviarAhora(json);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "SenderThread").start();
    }

    private void enviarAhora(String json) {
        try (Socket socket = new Socket(host, puerto);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(json);
            System.out.println("[Sender] Enviado JSON: " + json);
        } catch (IOException e) {
            System.err.println("[Sender] Error al enviar: " + e.getMessage());
        }
    }

    public void enviar(String json) {
        colaEnvio.offer(json);
    }

    public void detener() {
        activo = false;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

// ----------------- Dispatcher -----------------

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

class Dispatcher implements Runnable {

    private final BlockingQueue<String> colaEnvio;
    private final OutputStream out;
    private final Sender sender;

    public Dispatcher(BlockingQueue<String> colaEnvio, OutputStream out) {
        this.colaEnvio = colaEnvio;
        this.out = out;
        this.sender = Sender.getInstance();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String mensaje = colaEnvio.take(); // espera hasta que haya mensaje
                sender.enviarMensaje(out, mensaje);
            }
        } catch (InterruptedException e) {
            System.out.println("Dispatcher interrumpido");
        } catch (IOException e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
        }
    }
}

package server;

import ConvertidorJSON.Evento;
import java.util.concurrent.BlockingQueue;

public class Dispatcher implements Runnable {
    private final BlockingQueue<Evento> colaEnvio;
    private final Sender sender;
    private boolean activo = true;

    public Dispatcher(BlockingQueue<Evento> colaEnvio, Sender sender) {
        this.colaEnvio = colaEnvio;
        this.sender = sender;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                Evento evento = colaEnvio.take(); // espera un evento
                sender.enviar(evento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void detener() { activo = false; }
}


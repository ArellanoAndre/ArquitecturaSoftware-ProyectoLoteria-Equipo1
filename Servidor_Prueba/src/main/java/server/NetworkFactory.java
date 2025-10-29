package server;

import ConvertidorJSON.Evento;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class NetworkFactory {
    private static NetworkFactory instancia;

    private NetworkFactory() { }

    public static synchronized NetworkFactory getInstancia() {
        if (instancia == null) {
            instancia = new NetworkFactory();
        }
        return instancia;
    }

    public Dispatcher crearDispatcher(BlockingQueue<Evento> colaEnvio, Sender sender) {
        return new Dispatcher(colaEnvio, sender);
    }

    public Sender crearSender(Socket socket) throws IOException {
        return new Sender(socket.getOutputStream());
    }

    public ReceiverHelper crearReceiverHelper(Socket socket, BlockingQueue<String> colaRecepcion) {
        return new ReceiverHelper(socket, colaRecepcion);
    }
}

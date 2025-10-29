package server;

import ConvertidorJSON.Evento;
import ConvertidorJSON.ToJSON;
import java.io.IOException;
import java.io.OutputStream;

public class Sender {
    private final OutputStream out;

    public Sender(OutputStream out) {
        this.out = out;
    }

    public synchronized void enviar(Evento evento) {
        try {
            String mensaje = ToJSON.convertirAJson(evento) + "\n";
            out.write(mensaje.getBytes());
            out.flush();
            System.out.println("\nEnviado: " + mensaje);
        } catch (IOException e) {
            System.err.println("Error al enviar: " + e.getMessage());
        }
    }
}

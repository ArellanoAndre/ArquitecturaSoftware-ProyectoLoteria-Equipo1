package itson.broker;

import Evento.Evento;
import java.net.*;

public class ServidorMain {

    public static void main(String[] args) {
        int puerto = 7000;
        try (ServerSocket servidor = new ServerSocket(puerto)) {

            
            System.out.println("[ServidorMain] Iniciando servidor en puerto " + puerto);
            Socket cliente = servidor.accept();
            EnsambladorServidor server = new EnsambladorServidor(cliente);

            System.out.println("[ServidorMain] Servidor listo.");
            System.out.println("Conéctate con:  nc 127.0.0.1 7000");
            System.out.println("Envía un JSON compatible con tu clase Evento.");

            // Opcional: mandar un evento de prueba hacia el cliente después de unos segundos
            Thread.sleep(5000);

            Evento eventoPrueba = new Evento(
                    "juego.out",
                    "MENSAJE_SERVIDOR",
                    "{\"msg\":\"hola desde el servidor\"}",
                    InetAddress.getLocalHost(),
                    "127.0.0.1"
            );

            System.out.println("[ServidorMain] Enviando evento de prueba hacia la red...");
            server.enviarEvento(eventoPrueba);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

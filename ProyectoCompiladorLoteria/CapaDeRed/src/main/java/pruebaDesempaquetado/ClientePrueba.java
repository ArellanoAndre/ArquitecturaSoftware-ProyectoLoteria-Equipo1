package pruebaDesempaquetado;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import dispatcher.Dispatcher;
import interfacesGlobales.IReceptorJSON;
import java.util.Scanner;

public class ClientePrueba {

    public static void main(String[] args) {

        // Receptor que imprime el JSON recibido.
        IReceptorJSON receptor = new IReceptorJSON() {
            @Override
            public void recibirJSON(String eventojson) {
                System.out.println("\n[ClientePrueba1 - 192.168.1.89:7001]");
                System.out.println("JSON recibido desde el otro cliente:");
                System.out.println(eventojson);
            }
        };

        try {
            System.out.println("[ClientePrueba1] Escuchando en 192.168.1.89 puerto 7001...");

            // Ensambla la red con el puerto de esta laptop
            EnsambladorRed ensamblador = new EnsambladorRed(7001);
            ensamblador.ensamblar(receptor);

            System.out.println("[ClientePrueba1] Esperando mensajes...");

            // Mantener viva la aplicación
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package pruebaDesempaquetado;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import dispatcher.Dispatcher;
import interfacesGlobales.IReceptorJSON;
import java.util.Scanner;
public class ClientePrueba2 {

    public static void main(String[] args) {

        IReceptorJSON receptor = new IReceptorJSON() {
            @Override
            public void recibirJSON(String eventojson) {
                System.out.println("\n[ClientePrueba2 - 192.168.1.88:7002]");
                System.out.println("JSON recibido en respuesta:");
                System.out.println(eventojson);
            }
        };

        try {
            System.out.println("[ClientePrueba2] Iniciando en 192.168.1.88 puerto 7002...");

            // Ensambla red en el puerto de esta laptop
            EnsambladorRed ensamblador = new EnsambladorRed(7002);
            ensamblador.ensamblar(receptor);

            Dispatcher dispatcher = ensamblador.getDispatcher();

            // JSON automático (sin pedir input)
            String jsonInterno = "{ \\\"mensaje\\\": \\\"Hola desde ClientePrueba2 (Laptop 2)\\\" }";

            String eventoJSON =
                "{"
                + "\"topico\":\"prueba.red\","
                + "\"evento\":\"MENSAJE_AUTOMATICO\","
                + "\"JSON\":\"" + jsonInterno + "\","
                + "\"ipLocal\":\"192.168.1.89\","
                + "\"ipDestino\":\"192.168.1.89\","
                + "\"puertoLocal\":7002,"
                + "\"puertoDestino\":7001"
                + "}";

            System.out.println("[ClientePrueba2] Enviando JSON automáticamente...");
            System.out.println(eventoJSON);

            // Crea EventoRed con el JSON y lo envía
            EventoRed evento = new EventoRed(eventoJSON, "192.168.1.89", 7001);
            dispatcher.dispatch(evento);

            // Mantener vivo para escuchar
            while (true) {
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

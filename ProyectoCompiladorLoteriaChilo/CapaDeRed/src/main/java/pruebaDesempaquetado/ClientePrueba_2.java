/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaDesempaquetado;

import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import dispatcher.Dispatcher;
import interfacesRed.IReceptorJSON;

/**
 *
 * @author rodri
 */
public class ClientePrueba_2 {

    /**
     * @param args the command line arguments
     */
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
            EventoRed evento = new EventoRed(eventoJSON, "192.168.8.99", 7001);
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

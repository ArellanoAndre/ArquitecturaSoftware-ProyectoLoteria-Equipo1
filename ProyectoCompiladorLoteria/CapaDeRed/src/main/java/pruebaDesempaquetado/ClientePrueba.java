/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebaDesempaquetado;

import Ensamblador.EnsambladorRed;
import dispatcher.Dispatcher;
import eventoRed.EventoRed;
import interfacesGlobales.IReceptorJSON;
import java.io.IOException;
import java.util.Scanner;

public class ClientePrueba {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);

        // Implementación sencilla del receptor
        IReceptorJSON receptor = new IReceptorJSON() {
            @Override
            public void recibirJSON(String eventojson) {
                System.out.println("[ReceptorCliente] Mensaje recibido del broker: " + eventojson);
            }

        };

        try {
            // Ensamblar la red del cliente
            EnsambladorRed ensamblador = new EnsambladorRed(5000);
            ensamblador.ensamblar(receptor);

            Dispatcher dispatcher = ensamblador.getDispatcher();

            // Enviar algunos mensajes hacia el broker
            System.out.println("[Cliente] Enviando mensajes al broker...");

            while (true) {
                String mensaje = scan.nextLine();

                EventoRed eventoRed = new EventoRed(mensaje, "127.0.0.1", 5001);

                dispatcher.dispatch(eventoRed);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

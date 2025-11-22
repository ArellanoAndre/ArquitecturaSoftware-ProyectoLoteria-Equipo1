/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import Evento.Evento;
import Sender.EventSender;
import builder.EventBuilder;
import colaGenerica.ColaDePrioridad;
import interfacesGlobales.IManejadorEvento;
import interfacesGlobales.IReceptorJSON;
import java.io.IOException;
import java.util.Scanner;
import listener.EventListener;

/**
 *
 * @author rodri
 */
public class Prueba_EventosYRed {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        int puertoLocal = 5000;
        int puertoDestino = 5001;

        EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocal);

        // Implementación sencilla del receptor
        IManejadorEvento manejadorEvento = new IManejadorEvento() {
            @Override
            public void manejar(String payloadJSON) {
                System.out.println("=====================================================================================");
                System.out.println("[ManejadorEvento] Evento recibido: " + payloadJSON);
                System.out.println("=====================================================================================");
            }
        };

        ColaDePrioridad colaSalida = new ColaDePrioridad();
        ColaDePrioridad colaEntrada = new ColaDePrioridad();

        Empaquetador empaquetador = new Empaquetador(colaSalida);

        EventSender eventSender = new EventSender(colaSalida);
        colaSalida.addObserverSalida(eventSender);

        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, manejadorEvento);
        colaEntrada.addObserverEntrada(desempaquetador);

        EventListener eventListener = new EventListener(colaEntrada);

        EventBuilder eventBuilder = new EventBuilder("127.0.0.1", puertoDestino, puertoLocal);

        IReceptorJSON receptorJSON = eventListener;
        ensambladorRed.ensamblar(receptorJSON);

        //------- Creación de un evento de seleccionar carta aleatorio----------
        while (true) {
            System.out.println("Ingresa el tipo de evento:");
            String tipoEvento = scan.nextLine();

            Evento eRandom = eventBuilder.crearEvento();

            eRandom.setTopico("Juego-in");
            eRandom.setEvento("Juego");
            eRandom.setJSON(
                    "{ \"TipoEvento\": " + tipoEvento + ", \"Jugador\": 2, \"Casilla\": " + 1 + " }"
            );
            empaquetador.empaquetar(eRandom);
        }

    }

}

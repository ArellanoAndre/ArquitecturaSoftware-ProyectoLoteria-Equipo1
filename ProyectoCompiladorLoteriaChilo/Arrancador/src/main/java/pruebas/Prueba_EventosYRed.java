/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import interfacesRed.IReceptorJSON;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import listener.EventListener;

/**
 *
 * @author rodri
 */
public class Prueba_EventosYRed {

    /**
     * @param args the command line arguments SCANNER PARA ENVIAR EL NÚMERO DE
     * CASILLA, SOLO ACEPTA INT
     */
    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        int puertoLocal = 5000;
        int puertoDestino = 7000;

        EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocal);

        // Implementación sencilla del receptor
        IReceptorEvento manejadorEvento = new IReceptorEvento() {

            @Override
            public void manejar(IEvento evento) {
                System.out.println("=====================================================================================");
                System.out.println("[ManejadorEvento] Evento recibido: " + evento);
                System.out.println("=====================================================================================");
            }
        };

        ColaDePrioridad colaSalida = new ColaDePrioridad();
        ColaDePrioridad colaEntrada = new ColaDePrioridad();

        Empaquetador empaquetador = new Empaquetador(colaSalida, "127.0.0.1", puertoDestino, puertoLocal);

        EventSender eventSender = new EventSender(colaSalida);
        colaSalida.addObserverSalida(eventSender);

        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, manejadorEvento);
        colaEntrada.addObserverEntrada(desempaquetador);

        EventListener eventListener = new EventListener(colaEntrada);

        IReceptorJSON receptorJSON = eventListener;
        ensambladorRed.ensamblar(receptorJSON);
        eventSender.setiDispatcher(ensambladorRed.getDispatcher());

        Random rand = new Random();
        

        //------- Creación de un evento de seleccionar carta aleatorio----------
        while (true) {
            System.out.println("Ingresa la casilla seleccionada 1 - 16:");
            int numero = scan.nextInt() - 1;

            IEvento eRandom = empaquetador.crearEvento();
            int Rnumero = rand.nextInt(16) + 1; // 1 al 16
            
            eRandom.setTopico("Juego-out");
            eRandom.setEvento("Juego");
            eRandom.setJSON(
                    "{ \"TipoEvento\": \"CasillaSeleccionadaValida\", "
                    + "\"Jugador\": " + numero + ", \"Casilla\": " + Rnumero + " }"
            );
            empaquetador.enviarEvento(eRandom);
        }

    }

}

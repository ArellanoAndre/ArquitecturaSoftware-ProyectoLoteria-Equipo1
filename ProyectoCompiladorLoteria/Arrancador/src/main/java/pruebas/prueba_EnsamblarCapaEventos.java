/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Evento.Evento;
import Helper.HelperJSON;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import interfacesGlobales.IManejadorEvento;
import java.util.Random;
import listener.EventListener;

/**
 *
 * @author rodri
 */
public class Prueba_EnsamblarCapaEventos {

    /**
     * Esta clase es para probar el ensamblaje solamente de la capa de eventos,
     * por la parte del jugador y lógica de juego. Empaquetador,
     * Desempaquetador, ColaSalida, ColaEntrada, EventSender y EventListener
     */
    public static void main(String[] args) {
//
//        // Implementación sencilla del receptor
//        IManejadorEvento manejadorEvento = new IManejadorEvento() {
//            @Override
//            public void manejar(String payloadJSON) {
//                System.out.println("[ManejadorEvento] Evento recibido: " + payloadJSON);
//            }
//        };
//        
//        IDispatcher dispatcher = new IDispatcher() {
//            @Override
//            public void dispatch(EventoRed eventoRed) {
//                System.out.println("[Dispatcher] Evento recibido: " + eventoRed);
//            }
//        };
//
//        ColaDePrioridad colaSalida = new ColaDePrioridad();
//        ColaDePrioridad colaEntrada = new ColaDePrioridad();
//
//        Empaquetador empaquetador = new Empaquetador(colaSalida);
//        
//        EventSender eventSender = new EventSender(colaSalida);
//        eventSender.setDispatcher(dispatcher);
//        colaSalida.addObserverSalida(eventSender);
//
//        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, manejadorEvento);
//        colaEntrada.addObserverEntrada(desempaquetador);
//        
//        EventListener eventListener = new EventListener(colaEntrada);
//
//        EventBuilder eventBuilder = new EventBuilder("127.0.0.1", 5000, 5001);
//        
//        
//        //------- Creación de un evento de seleccionar carta aleatorio----------
//        Random random = new Random();
//        int casillaRandom = random.nextInt(15) + 1;
//        Evento eRandom = eventBuilder.crearEvento();
//
//        eRandom.setTopico("Juego-in");
//        eRandom.setEvento("Juego");
//        eRandom.setJSON(
//                "{ \"TipoEvento\": \"CasillaSeleccionadaValida\", "
//                + "\"Jugador\": 2, \"Casilla\": " + casillaRandom + " }"
//        );
//        String jsonRandom = HelperJSON.toJSON(eRandom);
//        //----------------------------------------------------------------------
//        
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Prueba 1 Desempaquetador Completo ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        
//        eventListener.recibirJSON(jsonRandom);
//        
//        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Prueba 2 Empaquetador Completo ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//        
//        empaquetador.empaquetar(eRandom);
//        
    }

}

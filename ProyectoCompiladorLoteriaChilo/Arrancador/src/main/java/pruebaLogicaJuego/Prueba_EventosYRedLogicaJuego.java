/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaLogicaJuego;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import Evento.Evento;
import colaGenerica.ColaDePrioridad;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author isaac
 */
public class Prueba_EventosYRedLogicaJuego {

    public static void main(String[] args) throws IOException {
//
//        System.out.println("=== INICIANDO CLIENTE (JUGADOR) ===");
//
//        Scanner scan = new Scanner(System.in);
//
//        // 1. CONFIGURACIÓN DE PUERTOS (Invertidos respecto al Host)
//        // El Host escucha en 6000 y envía a 6001.
//        // Nosotros escuchamos en 6001 y enviamos a 6000.
//        int puertoLocal = 6001;
//        int puertoDestino = 6000;
//
//        // 2. LEVANTAR RED
//        EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocal);
//        ensambladorRed.ensamblar(json -> {
//        }); 
//
//        
//        ensambladorRed.getColaEntrada().removeObserverEntrada(ensambladorRed.getRecepcion());
//
//        // Usamos las colas REALES de la red
//        ColaDePrioridad colaSalida = ensambladorRed.getColaSalida();
//        ColaDePrioridad colaEntrada = ensambladorRed.getColaEntrada();
//
//        // 3. CONFIGURAR DESEMPAQUETADOR (Para escuchar al Host)
//        IManejadorEvento manejadorEvento = new IManejadorEvento() {
//            @Override
//            public void manejar(String payloadJSON) {
//                System.out.println("\n>>> [CLIENTE] Recibí del Host: " + payloadJSON);
//            }
//        };
//
//        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, manejadorEvento);
//        colaEntrada.addObserverEntrada(desempaquetador);
//
//        // 4. CONFIGURAR EMPAQUETADOR (Para hablarle al Host)
//        Empaquetador empaquetador = new Empaquetador(colaSalida);
//        EventBuilder eventBuilder = new EventBuilder("127.0.0.1", puertoDestino, puertoLocal);
//
//        System.out.println("--> Cliente listo en puerto " + puertoLocal);
//        System.out.println("--> Escribe un número de casilla (1-16) para intentar marcar:");
//
//        // 5. BUCLE DE ENVÍO MANUAL
//        while (true) {
//            try {
//                String input = scan.next();
//                int numeroCasilla = Integer.parseInt(input);
//
//                Evento evento = eventBuilder.crearEvento();
//                evento.setTopico("Juego-in");
//                evento.setEvento("Juego"); // Nombre genérico
//
//                // JSON COMPATIBLE CON TU LOGICA DEL HOST            
//                String jsonPayload = "{ \"TipoEvento\": \"INTENTO_MARCAR\", "
//                        + "\"Jugador\": 2, " // Fingimos ser el Jugador 2
//                        + "\"Casilla\": " + numeroCasilla + " }";
//
//                evento.setJSON(jsonPayload);
//
//                empaquetador.empaquetar(evento);
//                System.out.println("[CLIENTE] Enviado intento casilla " + numeroCasilla);
//
//            } catch (NumberFormatException e) {
//                System.out.println("Por favor ingresa solo números.");
//            } catch (Exception e) {
//                System.out.println("Error: " + e.getMessage());
//            }
//        }
    }
}

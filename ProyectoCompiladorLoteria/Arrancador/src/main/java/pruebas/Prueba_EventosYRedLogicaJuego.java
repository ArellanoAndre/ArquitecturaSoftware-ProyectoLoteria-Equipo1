/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import InterfacesEventClient.IEvento;
import colaGenerica.ColaDePrioridad;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author isaac
 */
public class Prueba_EventosYRedLogicaJuego {

    public static void main(String[] args) throws IOException {

        System.out.println("=== INICIANDO CLIENTE (JUGADOR PUERTO 6001) ===");

        Scanner scan = new Scanner(System.in);

        // Configuración inversa al Host
        int puertoLocal = 6001;
        int puertoDestino = 6000;
        String ipDestino = "127.0.0.1";

        // 1. RED
        EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocal);
        ensambladorRed.ensamblar(json -> {
        });
        ensambladorRed.getColaEntrada().removeObserverEntrada(ensambladorRed.getRecepcion());

        ColaDePrioridad colaSalida = ensambladorRed.getColaSalida();
        ColaDePrioridad colaEntrada = ensambladorRed.getColaEntrada();

        
        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, new InterfacesEventClient.IReceptorEvento() {
            @Override
            public void manejar(IEvento evento) {
                System.out.println("\n>>> [CLIENTE] Recibí: " + evento.getJSON());
            }
        });
        colaEntrada.addObserverEntrada(desempaquetador);

        // 3. ENVÍO (Mandar Jugadas al Host)
        // [CAMBIO CLAVE] Configuramos el empaquetador con el destino
        Empaquetador empaquetador = new Empaquetador(colaSalida, ipDestino, puertoDestino, puertoLocal);

        System.out.println("--> Escribe un número de casilla (1-16) para marcar:");

        while (true) {
            try {
                String input = scan.next();
                int numeroCasilla = Integer.parseInt(input);

                IEvento evento = empaquetador.crearEvento();

                evento.setTopico("Juego-in");
                evento.setEvento("Juego");

                String jsonPayload = "{ \"TipoEvento\": \"INTENTO_MARCAR\", "
                        + "\"Jugador\": 2, "
                        + "\"Casilla\": " + numeroCasilla + " }";

                evento.setJSON(jsonPayload);

                // Enviamos usando la interfaz
                empaquetador.enviarEvento(evento);

                System.out.println("[CLIENTE] Enviado intento casilla " + numeroCasilla);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}

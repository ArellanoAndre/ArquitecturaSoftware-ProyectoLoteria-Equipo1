/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaLogicaJuego;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import Helper.HelperJSON;
import builder.EventBuilder;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import eventoRed.EventoRed;
import interfacesGlobales.IEvento;
import java.io.IOException;
import logicaJuego.LogicaDeJuego;
import modeloJuego.ModeloLogica;

/**
 *
 * @author isaac
 */
/**
 * Prueba unificada de 1 jugador (Host). Simula el ciclo completo: 1. La lógica
 * genera una carta. 2. Simulamos que el jugador ve la carta e intenta marcarla
 * (Inyección de evento de red). 3. La lógica valida y confirma.
 */
public class PruebaFlujoLogicaJuego {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== SERVIDOR HOST INICIADO (PUERTO 6000) ===");

        
        int puertoHost = 6000;
        int puertoDestino = 6001;
        
        
        EnsambladorRed ensamblador = new EnsambladorRed(puertoHost);
        ensamblador.ensamblar(json -> {
        });

        
        ensamblador.getColaEntrada().removeObserverEntrada(ensamblador.getRecepcion());

        ColaDePrioridad<EventoRed> colaSalida = ensamblador.getColaSalida();
        ColaDePrioridad<String> colaEntrada = ensamblador.getColaEntrada();

        
        LogicaDeJuego logica = new LogicaDeJuego();
        ModeloLogica modelo = new ModeloLogica();
        Empaquetador empaquetador = new Empaquetador(colaSalida);

        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modelo);
        colaEntrada.addObserverEntrada(desempaquetador);

        // Conexiones
        logica.setModelo(modelo);
        modelo.setLogicaDeJuego(logica);
        modelo.setEmpaquetador(empaquetador);
        // El Host responderá al puerto 6001 (donde vive el cliente)
        modelo.setEventBuilder(new EventBuilder("127.0.0.1", puertoDestino, puertoHost));

        // -----------------------------------------------------------
        // 3. JUGADORES (ESTO ES IMPORTANTE)
        // -----------------------------------------------------------

        // Creamos al Jugador 1 (Cliente Remoto)
        // Necesitamos agregarlo para que cuando llegue el ID=2, la lógica sepa quién es.
        logica.agregarJugador("Rodri");

        var jugador2 = logica.getJugadores().get(0); // Índice 0 es el jugador 1
        System.out.println("[INFO] Esperando jugadas del ID 2. Su tarjeta es: "
                + java.util.Arrays.toString(jugador2.getTarjeta().getCasillas()));

        // -----------------------------------------------------------
        // 4. ARRANQUE
        // -----------------------------------------------------------
        System.out.println("[ACCION] Iniciando la Lotería...");
        logica.iniciarJuego();

        // -----------------------------------------------------------
        // 5. MODO ESPERA (SERVIDOR)
        // -----------------------------------------------------------
        System.out.println("=== El Host está esperando eventos de red... ===");
        while (true) {
            // Mantenemos el main vivo. 
            // La lógica funciona con un Timer en otro hilo, así que esto no bloquea el juego.
            Thread.sleep(1000);
        }
    }
}

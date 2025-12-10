/*
 * Clase de prueba del Servidor:
 * Inicia la lógica del juego pero MANIPULA al jugador 1 (Rodri)
 * para que el servidor crea que ya tiene todas las casillas marcadas.
 */
package pruebaLogicaJuego;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import colaGenerica.ColaDePrioridad;
import java.io.IOException;
import java.util.Arrays; // Import necesario para llenar el arreglo
import logicaJuego.LogicaDeJuego;
import modeloJuego.ModeloLogica;

public class PruebaFlujoLogicaJuego3 {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== SERVIDOR HOST (TEST WIN) INICIADO (PUERTO 6000) ===");

        // 1. RED
        int puertoLocal = 5000;
        int puertoDestino = 7000; // A donde vamos a responder (Broker)
        String ipDestino = "127.0.0.1";

        EnsambladorRed ensamblador = new EnsambladorRed(puertoLocal);
        ensamblador.ensamblar(json -> {
        });

        // Desactivamos el receptor default para que no compita con el Desempaquetador
        ensamblador.getColaEntrada().removeObserverEntrada(ensamblador.getRecepcion());

        ColaDePrioridad<EventoRed> colaSalida = ensamblador.getColaSalida();
        ColaDePrioridad<String> colaEntrada = ensamblador.getColaEntrada();

        // 2. NEGOCIO
        LogicaDeJuego logica = new LogicaDeJuego();
        ModeloLogica modelo = new ModeloLogica();

        Empaquetador empaquetador = new Empaquetador(colaSalida, ipDestino, puertoDestino, puertoLocal);

        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modelo);
        colaEntrada.addObserverEntrada(desempaquetador);

        // 3. CONEXIONES
        logica.setModelo(modelo);
        modelo.setLogicaDeJuego(logica);
        modelo.setEmpaquetador(empaquetador);

        // 4. JUGADORES (Agrega a Rodri y a Isaac normalmente)
        logica.agregarJugadores();

        // =====================================================================
        // >>>> HACK SERVER: VALIDAR VICTORIA INMEDIATA <<<<
        // =====================================================================
        // Obtenemos al jugador 1 (Rodri) de la memoria del servidor
        // y le marcamos todas las casillas como true.
        // Así, cuando llegue el evento "INTENTO_LOTERIA", la validación pasará.
        
        var jugadorRodri = logica.getJugadores().get(0); // Índice 0 es Rodri (Jugador 1)
        
        boolean[] tarjetaLlena = new boolean[16];
        Arrays.fill(tarjetaLlena, true); // Llenar de true
        
        jugadorRodri.getTarjeta().setMarcadas(tarjetaLlena);
        
        System.out.println("[TEST-HACK] Se ha manipulado la memoria del servidor.");
        System.out.println("[TEST-HACK] El jugador " + jugadorRodri.getNombre() + 
                           " ahora tiene la tarjeta COMPLETA en la lógica.");
        // =====================================================================

        // MODO ESPERA
        while (true) {
            Thread.sleep(1000);
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaEnsamblador;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import colaGenerica.ColaDePrioridad;
import java.io.IOException;
import logicaJuego.LogicaDeJuego;
import modeloJuego.ModeloLogica;

/**
 *
 * @author rodri
 */
public class EnsambladorLogica {

    public void ensamblar(int puertoLocal, int puertoDestino, String ipDestino) throws IOException, InterruptedException {
        System.out.println("=== SERVIDOR HOST INICIADO (PUERTO 6000) ===");

        // 1. RED
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

        // 3. CONEXIONES (Mucho más limpias ahora)
        logica.setModelo(modelo);
        modelo.setLogicaDeJuego(logica);

        modelo.setEmpaquetador(empaquetador);

        // 4. JUGADORES
        logica.agregarJugadores();

        var jugador2 = logica.getJugadores().get(1);
        System.out.println("[INFO] Esperando jugadas del ID 2. Tarjeta: "
                + java.util.Arrays.toString(jugador2.getTarjeta().getCasillas()));

        // MODO ESPERA
        while (true) {
            Thread.sleep(1000);
        }
    }
}

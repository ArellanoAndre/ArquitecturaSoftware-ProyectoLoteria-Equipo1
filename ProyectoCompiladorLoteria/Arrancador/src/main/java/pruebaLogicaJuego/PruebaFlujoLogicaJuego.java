/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebaLogicaJuego;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import Helper.HelperJSON;
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import dispatcher.Dispatcher;
import eventBuilder.EventBuilder;
import interfacesRed.IReceptorJSON;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import listener.EventListener;
import logicaJuego.LogicaDeJuego;
import modeloJuego.ModeloLogica;
import pruebaMVC.Prueba_MVC;

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

        int puertoHost = 5000;
        String ipDestino = "127.0.0.1";
        int puertoDestino = 7000;

        EnsambladorRed ensambladorRed = new EnsambladorRed(puertoHost);

        ColaDePrioridad colaSalida = new ColaDePrioridad();
        ColaDePrioridad colaEntrada = new ColaDePrioridad();

        Empaquetador empaquetador = new Empaquetador(colaSalida, ipDestino, puertoDestino, puertoHost);

        LogicaDeJuego logica = new LogicaDeJuego();
        ModeloLogica modelo = new ModeloLogica();

        EventSender eventSender = new EventSender(colaSalida);
        colaSalida.addObserverSalida(eventSender);

        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modelo);
        colaEntrada.addObserverEntrada(desempaquetador);

        EventListener eventListener = new EventListener(colaEntrada);

        modelo.setEmpaquetador(empaquetador);

        IReceptorJSON receptorJSON = eventListener;
        try {
            ensambladorRed.ensamblar(receptorJSON);
            Dispatcher dispatcher = ensambladorRed.getDispatcher();
            eventSender.setiDispatcher(dispatcher);
        } catch (IOException ex) {
            Logger.getLogger(Prueba_MVC.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Conexiones
        logica.setModelo(modelo);
        modelo.setLogicaDeJuego(logica);
        modelo.setEmpaquetador(empaquetador);

        logica.agregarJugadores();

        var jugador2 = logica.getJugadores().get(1); // Índice 1 es el jugador 2
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

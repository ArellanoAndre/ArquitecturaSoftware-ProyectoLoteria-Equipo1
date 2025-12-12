/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas_CU_Unirse;
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import logicaJuego.LogicaDeJuego;
import modeloJuego.ModeloLogica;
/**
 *
 * @author Arell
 */
public class PruebaPartidaNoConfigurada {

    public static void main(String[] args) throws Exception {

        System.out.println("=== HOST SIN PARTIDA CONFIGURADA ===");

        int puertoLocal = 5000;
        int puertoBroker = 7777;
        String ipBroker = "127.0.0.1";

        // 1) ENSAMBLAR RED IGUAL QUE EN HOST CONFIGURADO
        EnsambladorRed ensamblador = new EnsambladorRed(puertoLocal);

        // Listener dummy para activar la red, NO procesará nada
        ensamblador.ensamblar(json -> {});

        // QUITAR RECEPTOR AUTOMÁTICO (MUY IMPORTANTE)
        ensamblador.getColaEntrada().removeObserverEntrada(ensamblador.getRecepcion());

        // 2) COLAS
        ColaDePrioridad<EventoRed> colaSalida = ensamblador.getColaSalida();
        ColaDePrioridad<String> colaEntrada = ensamblador.getColaEntrada();

        // 3) MODELO LÓGICO (NO CONFIGURADO)
        LogicaDeJuego logica = new LogicaDeJuego();
        ModeloLogica modelo = new ModeloLogica();

// conectar modelo ↔ lógica
        logica.setModelo(modelo);
        modelo.setLogicaDeJuego(logica);

        // Empaquetador HOST → BROKER
        Empaquetador empaquetador = new Empaquetador(
                colaSalida,
                ipBroker,
                puertoBroker,
                puertoLocal
        );

        // Desempaquetador BROKER → HOST
        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modelo);
        colaEntrada.addObserverEntrada(desempaquetador);

        // 4) SENDER
        EventSender sender = new EventSender(colaSalida);
        sender.setiDispatcher(ensamblador.getDispatcher());
        colaSalida.addObserverSalida(sender);

        // 5) CONEXIÓN MODELO ↔ EMPAQUETADOR
        modelo.setEmpaquetador(empaquetador);

        System.out.println("[HOST] No configurado y esperando eventos...");

        while (true) Thread.sleep(1000);
    }
}
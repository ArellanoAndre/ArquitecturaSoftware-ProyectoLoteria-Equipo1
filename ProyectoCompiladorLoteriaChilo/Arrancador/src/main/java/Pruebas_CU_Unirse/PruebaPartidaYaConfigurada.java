/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas_CU_Unirse;

/**
 *
 * @author Arell
 */
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import colaGenerica.ColaDePrioridad;
import java.io.IOException;
import logicaJuego.LogicaDeJuego;
import modeloJuego.ModeloLogica;

public class PruebaPartidaYaConfigurada {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("=== HOST LÓGICA DE JUEGO – PARTIDA YA CONFIGURADA ===");

        // 1) DATOS DE RED
        int puertoLocal = 5000;      // HOST
        int puertoBroker = 7000;     // BROKER
        String ipBroker = "127.0.0.1";

        // 2) ENSAMBLADOR RED (HOST)
        EnsambladorRed ensamblador = new EnsambladorRed(puertoLocal);

        // El receptor JSON aquí NO nos interesa, el Desempaquetador tomará la cola
        ensamblador.ensamblar(json -> {});

        // Quitamos el observer default para usar el Desempaquetador propio
        ensamblador.getColaEntrada().removeObserverEntrada(ensamblador.getRecepcion());

        ColaDePrioridad<EventoRed> colaSalida = ensamblador.getColaSalida();
        ColaDePrioridad<String> colaEntrada = ensamblador.getColaEntrada();

        // 3) LÓGICA DE JUEGO + MODELO LÓGICA
        LogicaDeJuego logica = new LogicaDeJuego();
        ModeloLogica modelo = new ModeloLogica();

        // Empaquetador: HOST → BROKER
        Empaquetador empaquetador = new Empaquetador(
                colaSalida,
                ipBroker,
                puertoBroker,
                puertoLocal
        );

        // Desempaquetador: BROKER → HOST (entradas)
        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modelo);
        colaEntrada.addObserverEntrada(desempaquetador);

        // 4) CONEXIONES ENTRE LÓGICA Y MODELO
        logica.setModelo(modelo);           // LogicaDeJuego conoce a ModeloLogica
        logica.AgregarJugadorTest();
        modelo.setLogicaDeJuego(logica);    // ModeloLogica conoce a LogicaDeJuego
        modelo.setEmpaquetador(empaquetador); // ModeloLogica puede mandar eventos al broker
        


        // 5) CONFIGURAR PARTIDA "YA LISTA"
        //    Aquí simulas como si ya se hubiera ejecutado ConfigurarPartida
        logica.setDificultad("BASICO");
        logica.setPunMax(1000); // puntuación máxima (puedes ajustar)
        logica.setNumJugadores(4);// jugadores esperados (puedes ajustar)
        logica.AgregarJugadorTest();
        // ===============================
           
        

        System.out.println("[HOST] Partida ya está configurada.");
        System.out.println("[HOST] Esperando eventos UnirsePartida desde Juego-in vía broker...");

        // 6) LOOP DE ESPERA
        while (true) {
            Thread.sleep(1000);
        }
    }
}


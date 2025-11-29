/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import ConstructorEventos.EventBuilder;
import Empaquetador.Empaquetador;
import RedEventos.EventoRed;
import colaGenerica.ColaDePrioridad;
import interfacesGlobales.IEvento;

/**
 *
 * @author Arell
 */

public class TestEnvioEmpaquetador {

    public static void main(String[] args) {

        System.out.println("===== TEST 2: ENVÍO (EventBuilder + Empaquetador) =====\n");

        // 1. Cola de salida
        ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();

        // 2. Crear Empaquetador
        Empaquetador empaquetador = new Empaquetador(colaSalida);

        // 3. Crear evento con EventBuilder REAL
        EventBuilder builder = new EventBuilder("127.0.0.1", 9000, 9001);

        IEvento evento = builder.crearEvento();
        evento.setTopico("test.envio");
        evento.setEvento("EVENTO_ENVIADO");
        evento.setJSON("{\"TipoEvento\":\"EnvioPrueba\", \"Valor\": 123}");

        System.out.println("[Test] Evento original:");
        System.out.println(evento + "\n");

        // 4. Empaquetar evento → produce EventoRed en colaSalida
        System.out.println("[Test] Empaquetando IEvento...\n");
        empaquetador.empaquetar(evento);

        // 5. Sacamos el EventoRed creado
        Object eventoRed = colaSalida.poll();

        System.out.println("[Test] Objeto resultante en colaSalida (EventoRed):");
        System.out.println(eventoRed);

        System.out.println("\n===== FIN TEST 2 =====");
    }
}


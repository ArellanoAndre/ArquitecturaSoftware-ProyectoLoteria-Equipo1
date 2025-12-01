/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Pruebas;

import eventBuilder.EventBuilder;
import Empaquetador.Empaquetador;
import InterfacesEventClient.IEvento;
import RedEventos.EventoRed;
import colaGenerica.ColaDePrioridad;

/**
 *
 * @author Arell
 */

public class TestEnvioEmpaquetador {

    public static void main(String[] args) {

        System.out.println("===== TEST 2: ENVÍO (EventBuilder + Empaquetador) =====\n");

        // 1. Parámetros para el empaquetador
        ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();
        String ipDestino = "127.0.0.1";
        int puertoDestino = 5000;
        int puertoLocal = 5001;

        // 2. Crear Empaquetador
        Empaquetador empaquetador = new Empaquetador(colaSalida,ipDestino, puertoDestino, puertoLocal);

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("test.envio");
        evento.setEvento("EVENTO_ENVIADO");
        evento.setJSON("{\"TipoEvento\":\"EnvioPrueba\", \"Valor\": 123}");

        System.out.println("[Test] Evento original:");
        System.out.println(evento + "\n");

        // 4. Empaquetar evento → produce EventoRed en colaSalida
        System.out.println("[Test] Empaquetando IEvento...\n");
        empaquetador.enviarEvento(evento);

        // 5. Sacamos el EventoRed creado
        Object eventoRed = colaSalida.poll();

        System.out.println("[Test] Objeto resultante en colaSalida (EventoRed):");
        System.out.println(eventoRed);

        System.out.println("\n===== FIN TEST 2 =====");
    }
}


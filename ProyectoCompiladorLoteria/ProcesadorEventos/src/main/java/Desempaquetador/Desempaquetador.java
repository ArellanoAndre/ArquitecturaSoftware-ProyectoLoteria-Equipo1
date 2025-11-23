/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desempaquetador;

import Helper.HelperJSON;
import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverEntrada;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;

/**
 *
 * @author isaac
 */
public class Desempaquetador implements ObserverEntrada {

    private ColaDePrioridad<String> colaEntrada;
    private IManejadorEvento componenteSuperior; // broker o logica de juego

    public Desempaquetador(ColaDePrioridad<String> colaEntrada, IManejadorEvento componenteSuperior) {
        this.colaEntrada = colaEntrada;
        this.componenteSuperior = componenteSuperior;
    }

    public Desempaquetador() {
    }

    public void setColaEntrada(ColaDePrioridad<String> colaEntrada) {
        this.colaEntrada = colaEntrada;
    }

    @Override
    public void updateEntrada() {

        try {
            String eventojson = colaEntrada.poll();
            System.out.println("\n DESEMPAQUETADOR, YA SAQUE EL EVENTO DE LA COLA ENTRADA!");

            if (eventojson == null) {
                return;
            }

            // Convertir al objeto Evento
            IEvento evento = HelperJSON.toEvento(eventojson);
            if (evento == null) {
                System.err.println("ERROR: No pude convertir el JSON a Evento");
                return;
            }

            // Obtener solo el payload interno (el JSON)
            String payload = evento.getJSON();

            System.out.println("Entregando payload al modelo");

            if (payload != null) {
                componenteSuperior.manejar(payload);
                
            } else {
                System.err.println("ERROR: payload es null en Evento");
            }

        } catch (Exception e) {
            System.err.println("ERROR extrayendo payload: " + e.getMessage());
        }
    }

}

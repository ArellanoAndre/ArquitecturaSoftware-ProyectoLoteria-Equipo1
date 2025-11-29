/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desempaquetador;

import Helper.HelperJSON;
import InterfacesEventClient.IEvento;
import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverEntrada;
import InterfacesEventClient.IReceptorEvento;

/**
 *
 * @author isaac
 */
public class Desempaquetador implements ObserverEntrada {

    private ColaDePrioridad<String> colaEntrada;
    private IReceptorEvento componenteSuperior; // broker o logica de juego

    public Desempaquetador(ColaDePrioridad<String> colaEntrada, IReceptorEvento componenteSuperior) {
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
        System.out.println("\n DESEMPAQUETADOR: YA SAQUÉ EL EVENTO DE LA COLA ENTRADA!");

        if (eventojson == null) {
            return;
        }

        // Convertir al objeto Evento
        IEvento iEvento = HelperJSON.toEvento(eventojson);
        if (iEvento == null) {
            System.err.println("ERROR: No pude convertir el JSON a Evento");
            return;
        }

        System.out.println("Entregando EVENTO COMPLETO al componente superior...");

        // AHORA ENTREGAS EL EVENTO COMPLETO
        componenteSuperior.manejar(iEvento);

    } catch (Exception e) {
        System.err.println("ERROR extrayendo evento: " + e.getMessage());
    }
}

}

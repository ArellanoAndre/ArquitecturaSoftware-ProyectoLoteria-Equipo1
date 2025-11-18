/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desempaquetador;

import Evento.Evento;
import Helper.HelperJSON;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IDesempaquetador;
import interfacesGlobales.IEvento;
import interfacesGlobales.IReceptorEventos;

/**
 *
 * @author isaac
 */
public class Desempaquetador implements IDesempaquetador {

    private ColaDePrioridad<Evento> colaEntrada = null;
    private String eventojson;

    public Desempaquetador() {

    }

    public void setColaEntrada(ColaDePrioridad<Evento> colaEntrada) {
        this.colaEntrada = colaEntrada;
    }

    @Override
    public void Desempaquetar(String eventojson) {

        try {
            if (colaEntrada == null) {
                throw new IllegalStateException("[Empaquetador] La cola de salida no ha sido inicializada.");
            }

            if (eventojson == null || eventojson.isEmpty()) {
                System.out.println("evento vacio");
                return;
            }

            Evento evento = HelperJSON.toEvento(eventojson); // tiene que llegar como evento o ievento xd

            if (evento == null) {
                System.out.println("el evento es nulo, no se pudo encolar");
                return;
            }

            this.colaEntrada.add(evento, TipoAdd.Entrada);
            System.out.println("Evento encolado para arriba");

            System.out.println("[Empaquetador] Mensaje enviado a la cola de entrada.");
        } catch (Exception e) {
            System.err.println("[Empaquetador] Error al desempaquetar evento: " + e.getMessage());
        }

    }

}

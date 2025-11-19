/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Desempaquetador;

import Evento.Evento;
import Helper.HelperJSON;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverEntrada;
import colaGenerica.TipoAdd;
import interfaces.IReceptor;
import interfacesGlobales.IDesempaquetador;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;
import interfacesGlobales.IReceptorEventos;

/**
 *
 * @author isaac
 */
public class Desempaquetador implements IReceptor {

    private IManejadorEvento componenteSuperior; // broker o logica de juego

    public Desempaquetador(IManejadorEvento componenteSuperior) {

        this.componenteSuperior = componenteSuperior;
    }

    public Desempaquetador() {
    }

    public void setComponenteSuperior(IManejadorEvento componenteSuperior) {
        this.componenteSuperior = componenteSuperior;
    }

    @Override
    public void mandarMensaje(String json) {

        if (json == null) {
            return;
        }

        System.out.println("[Desempaquetador] Recibido desde CapaRed: " + json);

        try {
             
            IEvento evento = HelperJSON.toEvento(json);

             
            if (evento != null && componenteSuperior != null) {
                componenteSuperior.manejar(evento);
            } else {
                System.out.println("[Desempaquetador] Evento nulo o sin componente superior.");
            }

        } catch (Exception e) {
            System.err.println("Error al procesar JSON: " + e.getMessage());
        }

    }

}

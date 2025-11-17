/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listener;

import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IEvento;
import interfacesGlobales.IReceptor;

/**
 *
 * @author isaac
 */
public class EventListener implements IReceptor {

    private String eventoJSON;
    private ColaDePrioridad<String> colaEntrada;
    private IReceptor suscriptor;

    public EventListener(ColaDePrioridad<String> colaEntrada) {
        this.colaEntrada = colaEntrada;
    }

//    public EventListener(String eventoJSON, ColaDePrioridad<String> colaEntrada, IReceptor suscriptor) {
//        this.eventoJSON = eventoJSON;
//        this.colaEntrada = colaEntrada;
//        this.suscriptor = suscriptor;
//    }

    @Override
    public void recibir(String eventojson) {

        while (true) {
            try {
                this.colaEntrada.add(eventojson, TipoAdd.Entrada);
                System.out.println("EventListener recibio el JSON y lo metio a la cola");

            } catch (InterruptedException e) {
                System.out.println("EventListener interrumpido");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}

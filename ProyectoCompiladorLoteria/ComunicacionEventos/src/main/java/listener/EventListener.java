/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listener;

import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IReceptor;

/**
 *
 * @author isaac
 */
public class EventListener implements IReceptor {

    private ColaDePrioridad<String> colaEntrada;
    
    
    public EventListener(ColaDePrioridad<String> colaEntrada) {
        this.colaEntrada = colaEntrada;
    }


    @Override
    public void recibir(String eventojson) {
            try {
                this.colaEntrada.add(eventojson, TipoAdd.Entrada);
                System.out.println("EventListener recibio el JSON y lo metio a la cola");

            } catch (InterruptedException e) {
                System.out.println("EventListener interrumpido");
                Thread.currentThread().interrupt();
            }
    }

}

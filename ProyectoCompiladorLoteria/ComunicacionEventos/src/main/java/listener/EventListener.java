/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listener;

import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IReceptorJSON;

/**
 *
 * @author isaac
 */
public class EventListener implements IReceptorJSON {

    private ColaDePrioridad<String> colaEntrada;
    
    
    public EventListener(ColaDePrioridad<String> colaEntrada) {
        this.colaEntrada = colaEntrada;
    }


    @Override
    public void recibirJSON(String eventojson) {
            try {
                System.out.println("\n LISTENER, RECIBI UN EVENTO CARNAL LO METERE A LA COLA ENTRADA!");
                this.colaEntrada.add(eventojson, TipoAdd.Entrada);

            } catch (InterruptedException e) {
                System.out.println("EventListener interrumpido");
                Thread.currentThread().interrupt();
            }
    }

}

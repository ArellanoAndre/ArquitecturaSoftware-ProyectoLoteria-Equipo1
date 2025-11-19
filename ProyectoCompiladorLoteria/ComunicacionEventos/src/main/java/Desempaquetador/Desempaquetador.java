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
    public void updateEntrada( ) {
        
        try {
            
            String eventojson =  colaEntrada.poll();
            
             if (eventojson == null) {
                 return;
             }
             
             IEvento evento = HelperJSON.toEvento(eventojson); 
            
             

            if (eventojson != null  ) {
                componenteSuperior.manejar(evento); // falta implementar como lo agarra y asi
                System.out.println("Evento entregado a " + componenteSuperior.getClass().getSimpleName());

            }
        } catch (Exception e) {
            System.err.println("[Empaquetador] Error al desempaquetar evento: " + e.getMessage());
        }

    }

     

}

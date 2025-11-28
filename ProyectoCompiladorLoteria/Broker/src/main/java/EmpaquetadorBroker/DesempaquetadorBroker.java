/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmpaquetadorBroker;

import Broker.Broker;
import Helper.HelperJSON;
import Interfaces.IEvento;
import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverEntrada;
import interfacesGlobales.IManejadorEvento;

/**
 *
 * @author abrilislas
 */
public class DesempaquetadorBroker implements ObserverEntrada{

    private ColaDePrioridad<String> colaEntrada;
    private IManejadorEvento componenteSuperior; // broker o logica de juego
    private IEvento evento;

    public DesempaquetadorBroker(ColaDePrioridad<String> colaEntrada, IManejadorEvento componenteSuperior) {
        this.colaEntrada = colaEntrada;
        this.componenteSuperior = componenteSuperior;
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
            if (evento == null) {
                eventojson = colaEntrada.take();
                evento = traducirJSON(eventojson);
            }    
            System.out.println("Entregando payload al modelo");

            if (evento != null) {
                componenteSuperior.manejar(eventojson);
                
            } else {
                System.err.println("ERROR: payload es null en Evento");
            }

        } catch (Exception e) {
            System.err.println("ERROR extrayendo payload: " + e.getMessage());
        }
    }
    
    public void setBroker(Broker broker){
        this.componenteSuperior=broker;
    }

    public IEvento traducirJSON(String eventojson) {
       return HelperJSON.toEvento(eventojson);
    }
}

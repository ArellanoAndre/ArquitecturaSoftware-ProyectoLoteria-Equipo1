/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EmpaquetadorBroker;

import eventoRed.EventoRed;
import Broker.Suscripcion;
import ProcesadorEventosBroker.Interfaces.IEmpaquetadorBroker;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IEvento;

/**
 *
 * @author abrilislas
 */
public  class EmpaquetadorBroker implements IEmpaquetadorBroker{
    
    private ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();

    
    public EmpaquetadorBroker(ColaDePrioridad<EventoRed> colaSalida){
        this.colaSalida=colaSalida;

    }
    /**
     * Este metodo se encarga de convertir los eventos 
     * a eventos de red y llama al metodo privado que los agrega a la cola de salida 
     * @param evento
     * @param suscriptor
     * @return 
     */
    @Override
    public void empaquetarEvento(IEvento evento, Suscripcion suscriptor) throws InterruptedException{
        
            EventoRed eventoRed = new EventoRed();
            
        try{

            eventoRed.setEventoJson(evento.getJSON());
            eventoRed.setIpDestino(evento.getIpDestino());
            eventoRed.setPuertoDestino(suscriptor.getPuerto());

            System.out.println("\n [Empaquetador] EventoRed creado: " + eventoRed);
            agregarEventoRedACola(eventoRed);
            
        }catch(Exception e){
            Thread.currentThread().interrupt();
            System.err.println("[Empaquetador] Hilo interrumpido al encolar evento.");
        }
    }
    
    private void agregarEventoRedACola(EventoRed eventoRed) throws InterruptedException{
        try{
            colaSalida.add(eventoRed, TipoAdd.Salida);
            System.out.println("[Empaquetador] El evento ha sido agregado a la cola de salida.");
        
        }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Empaquetador] Hilo interrumpido al encolar evento.");
        }catch (Exception e) {
            System.err.println("[Empaquetador] Error al empaquetar evento: " + e.getMessage());
        }
    }
}

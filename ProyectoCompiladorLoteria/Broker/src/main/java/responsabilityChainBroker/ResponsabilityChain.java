/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import interfacesGlobales.IEvento;
import interfacesGlobales.IReceptorEventos;
import pruebas.Broker;
import pruebas.Suscripcion;

/**
 *
 * @author abrilislas
 */
public class ResponsabilityChain implements IReceptorEventos{
    
    private Evento evento;
    private Broker broker;

    public ResponsabilityChain(Broker broker) {
        this.broker = broker;
    }
    
    public void startChain(Evento evento){
        registrarSuscriptor(evento);
    }
    
    //1. Proceso 01 - Registrar Suscriptor
        private void registrarSuscriptor(Evento evento){
        if("suscribir".equals(evento.getEvento())){
            
            Suscripcion suscriptor = new Suscripcion(evento.getIpLocal(), evento.getPuerto());
            
            broker.registrarSuscripcion(evento.getTopico(),suscriptor);
            
        }else{
            desuscribirTopico(evento);
        }
    }
    //2. Proceso 02 - Desuscribir
        private void desuscribirTopico(Evento evento){
        if("desuscribir".equals(evento.getEvento())){
            
            
        }else{
            notificarEvento(evento);
        }
    }
    //3.  Proceso 03 -Notificar Evento a suscriptores
        private void notificarEvento(Evento evento){
    }

    @Override
    public void recibirEvento(IEvento ievento) {
        evento = (Evento) ievento;
        startChain(evento);
    }
}
        

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import eventoRed.EventoRed;
import interfaces.IBroker;
import pruebas.Suscripcion;

/**
 *
 * @author abrilislas
 */
public class FiltroGenericoEvento implements IFiltro {
    
    protected IFiltro succesor = new FiltroGenericoEvento();
    IBroker broker;
    
    @Override
    public void setNext(IFiltro succesor) {
        if(succesor==null){
            setNext(succesor);
        }
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        for (Suscripcion suscriptores : broker.obtenerSuscriptores(topico)) {
            EventoRed eventoRed = new EventoRed(); 
            eventoRed.setEventoJson(evento.getJSON());
            eventoRed.setIpDestino(evento.getIpDestino());
            eventoRed.setPuertoDestino(suscriptores.getPuerto());
            broker.publicarEvento(eventoRed, topico);
        }
    
    }
    @Override
    public void setBroker(IBroker broker) {
        this.broker=broker;
    }
    
}

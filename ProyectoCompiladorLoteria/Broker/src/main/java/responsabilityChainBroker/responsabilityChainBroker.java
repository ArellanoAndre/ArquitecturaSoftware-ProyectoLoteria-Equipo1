/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import filtros.FiltroDesuscripcion;
import filtros.FiltroGenericoEvento;
import filtros.FiltroSuscripcion;
import interfacesGlobales.IEvento;
import interfacesGlobales.IReceptorEventos;

/**
 *
 * @author abrilislas
 */
public class responsabilityChainBroker implements IReceptorEventos{
    
    FiltroSuscripcion filtroSuscripcion;
    FiltroDesuscripcion filtroDesuscripcion;
    FiltroGenericoEvento filtroEventoGenerico;
    
    public responsabilityChainBroker(){
        filtroSuscripcion.setNext(filtroDesuscripcion);
        filtroDesuscripcion.setNext(filtroEventoGenerico);
        filtroEventoGenerico.setNext(null);
    }
    
    @Override
    public void recibirEvento(IEvento ievento) {
        Evento evento = (Evento) ievento;
        filtroSuscripcion.procesarEvento(evento);
    }
}

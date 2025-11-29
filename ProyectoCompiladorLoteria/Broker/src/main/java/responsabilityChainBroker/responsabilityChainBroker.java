/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import filtros.FiltroDesuscripcion;
import filtros.FiltroGenericoEvento;
import filtros.FiltroSuscripcion;

/**
 *
 * @author abrilislas
 */
public class responsabilityChainBroker implements IReceptorEvento {

    public FiltroSuscripcion filtroSuscripcion;
    public FiltroDesuscripcion filtroDesuscripcion;
    public FiltroGenericoEvento filtroEventoGenerico;

    public responsabilityChainBroker() {
        filtroSuscripcion = new FiltroSuscripcion();
        filtroDesuscripcion = new FiltroDesuscripcion();
        filtroEventoGenerico = new FiltroGenericoEvento();
        filtroSuscripcion.setNext(filtroDesuscripcion);
        filtroDesuscripcion.setNext(filtroEventoGenerico);
        filtroEventoGenerico.setNext(null);
    }

    @Override
    public void manejar(IEvento ievento) {
        Evento evento = (Evento) ievento;
        filtroSuscripcion.procesarEvento(evento);
    }
}

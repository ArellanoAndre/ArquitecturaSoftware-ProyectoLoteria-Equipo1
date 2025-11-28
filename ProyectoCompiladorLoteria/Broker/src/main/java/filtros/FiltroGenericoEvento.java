/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtros;

import Evento.Evento;
import IEventosBroker.IEnvioEvento;
import interfaces.IFiltro;
import interfacesGlobales.IEvento;
import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

/**
 *
 * @author abrilislas
 */
public abstract class FiltroGenericoEvento implements IFiltro, IEnvioEvento {
    
    protected IFiltro succesor; 
    gestorDeSuscripciones gestorSuscripciones;
    
    @Override
    public void setNext(IFiltro succesor) {
        this.succesor=succesor; 
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        for (Suscripcion suscriptores : gestorSuscripciones.obtenerSuscriptores(topico)) {
            empaquetar(evento);
        }
    }

    @Override
    public void empaquetar(IEvento evento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

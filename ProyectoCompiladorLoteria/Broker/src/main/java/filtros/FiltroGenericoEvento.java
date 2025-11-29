/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtros;

import Evento.Evento;
import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import interfaces.IFiltro;

import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

/**
 *
 * @author abrilislas
 */
public abstract class FiltroGenericoEvento implements IFiltro, IEnvioEvento{
    
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
        }
    }

    @Override
    public IEvento crearEvento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enviarEvento(IEvento evento) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

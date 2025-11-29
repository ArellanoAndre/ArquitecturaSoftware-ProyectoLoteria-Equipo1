/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtros;

import Evento.Evento;
import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import interfaces.IFiltro;
import java.util.List;
import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

/**
 *
 * @author abrilislas
 */
public class FiltroGenericoEvento implements IFiltro, IEnvioEvento{
    
    protected IFiltro succesor; 
    public gestorDeSuscripciones gestorSuscripciones;
    
    @Override
    public void setNext(IFiltro succesor) {
        this.succesor=succesor; 
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        List<Suscripcion> lista = gestorSuscripciones.obtenerSuscriptores(topico);

        if (lista.isEmpty()) {
            System.out.println("[FiltroGenericoEvento] No hay suscriptores para: " + topico);
        } else {
            System.out.println("[FiltroGenericoEvento] Notificando a suscriptores del topico: " + topico);

            for (Suscripcion s : lista) {
                System.out.println("  Enviando a " + s);
                enviarEvento(evento); 
            }
        }
    }

    @Override
    public IEvento crearEvento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enviarEvento(IEvento evento) {
        System.out.println("[EnvioEvento] Enviando evento a suscriptores: " + evento.getTopico());
    }
    
    
}

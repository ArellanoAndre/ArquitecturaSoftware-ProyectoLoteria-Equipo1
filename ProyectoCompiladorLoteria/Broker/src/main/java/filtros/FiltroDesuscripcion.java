/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtros;

import Evento.Evento;
import interfaces.IFiltro;
import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

 

/**
 *
 * @author abrilislas
 */

public class FiltroDesuscripcion implements IFiltro {

    protected IFiltro succesor;
    public gestorDeSuscripciones gestorSuscripciones;

    @Override
    public void setNext(IFiltro succesor) {
        this.succesor = succesor;
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        
        if (topico.equals("desuscripcion")) {
            String ip = evento.getIpLocal();
            int puerto = evento.getPuertoLocal();
            Suscripcion suscriptor = new Suscripcion(ip, puerto);

            gestorSuscripciones.eliminarElemento(topico, suscriptor);
            System.out.println("[FiltroDesuscripcion] Eliminando suscripción...");
        } else if (succesor != null) {
            succesor.procesarEvento(evento);
        }
    }
}


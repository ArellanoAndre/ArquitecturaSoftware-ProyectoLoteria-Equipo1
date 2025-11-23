/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Broker.Suscripcion;
import Evento.Evento;
import interfaces.IBroker;

/**
 *
 * @author abrilislas
 */

public class FiltroDesuscripcion implements IFiltro {

    public IFiltro succesor;
    private IBroker broker;

    @Override
    public void setBroker(IBroker broker) {
        this.broker = broker;
    }

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

            broker.eliminarSuscripcion(topico, suscriptor);
            System.out.println("[FiltroDesuscripcion] Eliminando suscripción...");
        } else if (succesor != null) {
            succesor.procesarEvento(evento);
        }
    }
}


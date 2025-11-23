/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import interfaces.IBroker;
import Broker.Suscripcion;
import EmpaquetadorBroker.DesempaquetadorBroker;

/**
 *
 * @author abrilislas
 */
public class FiltroGenericoEvento implements IFiltro {
    
    public IFiltro succesor; 
    IBroker broker;
    
    @Override
    public void setBroker(IBroker broker) {
        this.broker = broker;
    }
    
    @Override
    public void setNext(IFiltro succesor) {
        this.succesor=succesor; 
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        //hacerlo por medio de un hilo 
        for (Suscripcion suscriptores : broker.obtenerSuscriptores(topico)) {
            
        }
    }
}

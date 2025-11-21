/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import interfaces.IBroker;

/**
 *
 * @author abrilislas
 */
public abstract interface IFiltro {
    
    void setNext(IFiltro succesor);
    void procesarEvento(Evento evento);
    void setBroker(IBroker broker);
}

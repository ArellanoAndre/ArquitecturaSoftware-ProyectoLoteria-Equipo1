/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ProcesadorEventosBroker.Interfaces;

import Broker.Suscripcion;
import interfacesGlobales.IEvento;

/**
 *
 * @author abrilislas
 */
public interface IEmpaquetadorBroker {
    
    void empaquetarEvento(IEvento evento, Suscripcion suscriptor) throws InterruptedException;
    
}

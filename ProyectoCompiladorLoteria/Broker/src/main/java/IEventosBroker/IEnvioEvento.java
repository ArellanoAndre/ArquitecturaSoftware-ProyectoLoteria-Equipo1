/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package IEventosBroker;

import Evento.Evento;
import interfacesGlobales.IEmpaquetador;

/**
 *
 * @author abrilislas
 */
public interface IEnvioEvento extends IEmpaquetador {
    
    public void empaquetar(Evento evento);
    
}

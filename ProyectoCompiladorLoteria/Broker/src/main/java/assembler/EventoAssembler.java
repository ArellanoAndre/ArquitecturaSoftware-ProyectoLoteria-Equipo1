/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Helper.HelperJSON;
import colaGenerica.ColaDePrioridad;
import interfaces.IBroker;
import pruebas.Broker;
/**
 *
 * @author abrilislas
 */
public class EventoAssembler {
    
    
    ColaDePrioridad cola = new ColaDePrioridad();
    HelperJSON helper = new HelperJSON();
    IBroker broker = new Broker(); 
    
    
}

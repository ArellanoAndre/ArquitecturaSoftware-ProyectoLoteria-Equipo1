/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import eventoRed.EventoRed;
import interfaces.IBroker;
import interfacesGlobales.IReceptorJSON;
import mecanismoRecepcion.MecanismoRecepcion;
import pruebas.LaSecuelaDelBroker;
import listener.EventListener;

/**
 *
 * @author abrilislas
 */
public class AssemblerBroker {
    
    private int PUERTO_BROKER;
    private ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();
    private ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();
    private IBroker broker = new LaSecuelaDelBroker(colaSalida);
    EventListener eventListener = new EventListener(colaEntrada);
    MecanismoRecepcion mecanismo = new MecanismoRecepcion(colaEntrada, eventListener);
    EventSender eventSender= new EventSender(colaSalida);
    
    public AssemblerBroker(IReceptorJSON receptor) {
    }
}

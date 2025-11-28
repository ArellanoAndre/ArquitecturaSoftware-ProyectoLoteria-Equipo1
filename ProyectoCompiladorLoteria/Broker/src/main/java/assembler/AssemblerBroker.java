/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Broker.Broker;
import EmpaquetadorBroker.DesempaquetadorBroker;
import EmpaquetadorBroker.EmpaquetadorBroker;
import Evento.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import mecanismoRecepcion.MecanismoRecepcion;
import ProcesadorEventosBroker.Interfaces.IEmpaquetadorBroker;
import listener.EventListener;

/**
 *
 * @author abrilislas
 */
public class AssemblerBroker {
    
    private int PUERTO_BROKER;
    private ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();
    private ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();
    private Broker broker;
    EventListener eventListener = new EventListener(colaEntrada);
    MecanismoRecepcion mecanismo = new MecanismoRecepcion(colaEntrada, eventListener);
    EventSender eventSender= new EventSender(colaSalida);
    
    IEmpaquetadorBroker empaquetador = new EmpaquetadorBroker(colaSalida);
    DesempaquetadorBroker desempaquetador;
    
    public AssemblerBroker() {
        desempaquetador = new DesempaquetadorBroker(colaEntrada, null);
        this.broker = new Broker(desempaquetador,empaquetador);
        desempaquetador.setBroker(broker);
        
    }
    public Broker getBroker() {
        return broker;
    }

    public void iniciar() {
        new Thread(() -> {
            while (true) {
                mecanismo.updateEntrada();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                eventSender.updateSalida();
            }
        }).start();

        System.out.println("[AssemblerBroker] Broker iniciado");
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

import EmpaquetadorBroker.DesempaquetadorBroker;
import Evento.Evento;
import Interfaces.IEvento;
import ProcesadorEventosBroker.Interfaces.IEmpaquetadorBroker;
import interfaces.IBroker;
import interfacesGlobales.IManejadorEvento;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import responsabilityChainBroker.FiltroDesuscripcion;
import responsabilityChainBroker.FiltroGenericoEvento;
import responsabilityChainBroker.FiltroSuscripcion;
import responsabilityChainBroker.IFiltro;

/**
 *
 * @author abrilislas
 */
public class Broker implements IBroker, IManejadorEvento{
    
    private final Map<String, CopyOnWriteArrayList<Suscripcion>> suscripciones = new ConcurrentHashMap<>(); 
    private final IFiltro filtroInicio = new FiltroSuscripcion(); 
    private final IFiltro filtroGenericoEvento = new FiltroGenericoEvento();
    private final IFiltro filtroDesuscripcion = new FiltroDesuscripcion();
    private DesempaquetadorBroker desempacador;
    private IEmpaquetadorBroker empaquetador;
    
    public Broker(DesempaquetadorBroker desempacador, IEmpaquetadorBroker empaquetador ){
        
        //00 - inicializamos 
        this.empaquetador=empaquetador;
        this.desempacador=desempacador;
        
        //01 - Asigamos los filtros al broker
        filtroInicio.setBroker(this);
        filtroGenericoEvento.setBroker(this);
        filtroDesuscripcion.setBroker(this);
        
        //02 - "Armamos" la cadena de filtros
        filtroInicio.setNext(filtroDesuscripcion);
        filtroDesuscripcion.setNext(filtroGenericoEvento);
        filtroGenericoEvento.setNext(null);
  
    }
    @Override
    public void registrarSuscripcion(String topico, Suscripcion suscriptor) {
    suscripciones.computeIfAbsent(topico, t -> new CopyOnWriteArrayList<>()).addIfAbsent(suscriptor);
    System.out.println("[Broker] " + suscriptor.getHost() + " suscrito a: " + topico);    }

    @Override
    public void eliminarSuscripcion(String topico, Suscripcion suscriptor) {
        
        List<Suscripcion> lista = obtenerSuscriptores(topico);

        if (lista == null) {
            System.out.println("[Broker] El tópico '" + topico + "' no existe.");
            return;
        }

        boolean eliminado = lista.remove(suscriptor);

        if (!eliminado) {
            System.out.println("[Broker] No existe el suscriptor '" + suscriptor + "' en el tópico '" + topico + "'.");
            return;
        }
        if (lista.isEmpty()) {
            suscripciones.remove(topico);
            System.out.println("[Broker] Tópico '" + topico + "' eliminado por quedar sin suscriptores.");
        }

        System.out.println("[Broker] " + suscriptor + " eliminado del tópico '" + topico + "'.");   
    }

    @Override
    public List<Suscripcion> obtenerSuscriptores(String topico) {
        return suscripciones.getOrDefault(topico, new CopyOnWriteArrayList<>());

    }
       

    @Override
    public void procesarEvento(Evento evento) {
        //02 - Intentamos usar el filtro de mayor prioridad (Suscripcion ig)
        filtroInicio.procesarEvento(evento);
        
    }

    public void publicarEvento(IEvento eventoNuevo, String topico) {
        try {
            for (Suscripcion suscriptor : obtenerSuscriptores(topico)) {
                empaquetador.empaquetarEvento(eventoNuevo,suscriptor); 
            }

        } catch (InterruptedException ex) {
            System.getLogger(Broker.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }    }

    @Override
    public void manejar(String payloadJSON) {
       IEvento evento = desempacador.traducirJSON(payloadJSON);
       procesarEvento((Evento) evento);
    }


    
}

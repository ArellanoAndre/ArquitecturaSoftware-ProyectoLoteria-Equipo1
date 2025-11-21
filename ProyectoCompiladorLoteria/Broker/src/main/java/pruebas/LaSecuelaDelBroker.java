/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import Evento.Evento;
import interfaces.IBroker;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import responsabilityChainBroker.FiltroSuscripcion;
import responsabilityChainBroker.IFiltro;

/**
 *
 * @author abrilislas
 */
public class LaSecuelaDelBroker implements IBroker{
    
    private final Map<String, CopyOnWriteArrayList<Suscripcion>> suscripciones = new ConcurrentHashMap<>();
    private final IFiltro filtroInicio = new FiltroSuscripcion(); 
    
    @Override
    public void registrarSuscripcion(String topico, Suscripcion suscriptor) {
    suscripciones.computeIfAbsent(topico, t -> new CopyOnWriteArrayList<>())
            .addIfAbsent(suscriptor);
    System.out.println("[Broker] " + suscriptor.getHost() + " suscrito a: " + topico);    }

    @Override
    public void eliminarSusripcion(String topico, Suscripcion suscriptor) {
        
        List<Suscripcion> lista = obtenerSuscriptores(topico);
        
        if(!suscripciones.containsValue(suscriptor)){
            System.out.println("[Broker] tópico '" + topico + "' No existe el suscriptor");
            return;
        }
        else if(lista.isEmpty()) {
            suscripciones.remove(topico);
            System.out.println("[Broker] tópico '" + topico + "' eliminado por quedar vacío.");
            }
        System.out.println("[Broker] " + suscriptor + " desuscrito de: " + topico);


    }

    @Override
    public List<Suscripcion> obtenerSuscriptores(String topico) {
        return suscripciones.getOrDefault(topico, new CopyOnWriteArrayList<>());

    }

    @Override
    public void publicarEvento(Evento eventoNuevo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void procesarEvento(Evento evento) {
        
        //01 - "Armamos" la cadena de filtros
        //Esto actualmente hace un looop pq no hay otro filtro, solo es pa que no marque error.
        filtroInicio.setNext(filtroInicio);
        
        //02 - Intentamos usar el filtro de mayor prioridad (Suscripcion ig)
        filtroInicio.procesarEvento(evento);
        
    }
}

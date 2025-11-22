/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import Evento.Evento;
import Helper.HelperJSON;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfaces.IBroker;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import responsabilityChainBroker.FiltroGenericoEvento;
import responsabilityChainBroker.FiltroSuscripcion;
import responsabilityChainBroker.IFiltro;

/**
 *
 * @author abrilislas
 */
public class LaSecuelaDelBroker implements IBroker{
    
    private final Map<String, CopyOnWriteArrayList<Suscripcion>> suscripciones = new ConcurrentHashMap<>();
    private final ColaDePrioridad<Evento> colaSalida = new ColaDePrioridad<>(); 
    private final IFiltro filtroInicio = new FiltroSuscripcion(); 
    private final IFiltro filtroGenericoEvento = new FiltroGenericoEvento();
    
    public LaSecuelaDelBroker(){
        filtroInicio.setBroker(this);
        filtroGenericoEvento.setBroker(this);
        //01 - "Armamos" la cadena de filtros
        filtroInicio.setNext(filtroGenericoEvento);
  
    }
    @Override
    public void registrarSuscripcion(String topico, Suscripcion suscriptor) {
    suscripciones.computeIfAbsent(topico, t -> new CopyOnWriteArrayList<>()).addIfAbsent(suscriptor);
    System.out.println("[Broker] " + suscriptor.getHost() + " suscrito a: " + topico);    }

    @Override
    public void eliminarSusripcion(String topico, Suscripcion suscriptor) {
        
        List<Suscripcion> lista = obtenerSuscriptores(topico);
        
        if (lista == null) {
            System.out.println("[Broker] El tópico no existe");
            return;
        }

        if(!lista.remove(suscriptor)){
            System.out.println("[Broker] tópico " + topico + " No existe el suscriptor");
            return;
        }
        else if(lista.isEmpty()) {
            suscripciones.remove(topico);
            System.out.println("[Broker] tópico " + topico + " eliminado por quedar vacío.");
            }
        
        System.out.println("[Broker] " + suscriptor + " desuscrito de: " + topico);
        

    }

    @Override
    public List<Suscripcion> obtenerSuscriptores(String topico) {
        return suscripciones.getOrDefault(topico, new CopyOnWriteArrayList<>());

    }

    @Override
    public void publicarEvento(Evento eventoNuevo) {
        try {
            colaSalida.add(eventoNuevo, TipoAdd.Salida);
        } catch (InterruptedException ex) {
            System.getLogger(LaSecuelaDelBroker.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
       

    @Override
    public void procesarEvento(Evento evento) {
        //02 - Intentamos usar el filtro de mayor prioridad (Suscripcion ig)
        filtroInicio.procesarEvento(evento);
        
    }
    
    @Override
    public void manejar(String payloadJSON) {
        procesarEvento(HelperJSON.toEvento(payloadJSON));
    }
}

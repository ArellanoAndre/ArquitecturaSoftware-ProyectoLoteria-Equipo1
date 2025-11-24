/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;
import interfaces.IBroker;
import interfacesGlobales.IManejadorEvento;

/**
 *
 * @author isaac
 */
public class FiltroEvento implements IFiltro {

    private IFiltro sucesor;
    private IBroker broker;
    private IManejadorEvento modeloLogica;

    public FiltroEvento() {
    }
    
    
    public void setModeloLogica(IManejadorEvento modelo) {
        this.modeloLogica = modelo;
    }

    @Override
    public void setNext(IFiltro succesor) {
        this.sucesor = sucesor;
    }

    @Override
    public void procesarEvento(Evento evento) {
        if (evento.getTopico().equalsIgnoreCase("partida") || evento.getTopico().equalsIgnoreCase("juego-in")) {

            System.out.println("[FiltroJuego] Evento de juego detectado. Enviando a ModeloLogica.");
            
            if (modeloLogica != null) {
                modeloLogica.manejar(evento.getJSON());
            } else {
                System.err.println("[FiltroEvento] ERROR: ModeloLogica es null");
            }
            
            String payload = evento.getJSON();
            modeloLogica.manejar(payload);

        } else {
             
            if (sucesor != null) {
                sucesor.procesarEvento(evento);
            }
        }

    }

    @Override
    public void setBroker(IBroker broker) {
        this.broker = broker;
    }
}

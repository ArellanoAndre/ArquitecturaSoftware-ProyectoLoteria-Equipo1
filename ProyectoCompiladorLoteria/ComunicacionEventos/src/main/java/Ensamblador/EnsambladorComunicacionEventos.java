package Ensamblador;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Evento.Evento;
import colaGenerica.ColaDePrioridad;
import interfacesGlobales.IEvento;


public class EnsambladorComunicacionEventos {
    private ColaDePrioridad<String> colaSalida;
    private ColaDePrioridad<IEvento> colaEntrada;
    
    public EnsambladorComunicacionEventos() {
        ensamblar();
    }
    
    public void ensamblar(){
    //....
        colaSalida = new ColaDePrioridad<>();
        Empaquetador empaquetador = new Empaquetador();
        empaquetador.setColaSalida(colaSalida);
        
        colaEntrada = new ColaDePrioridad<>();
        Desempaquetador desempaquetador = new Desempaquetador();
        colaEntrada.addObserverEntrada(desempaquetador);
    }
    
    
}

package Ensamblador;

import Empaquetador.Empaquetador;
import colaGenerica.ColaDePrioridad;

public class EnsambladorComunicacionEventos {
    private ColaDePrioridad<String> colaSalida;
    private ColaDePrioridad<String> colaEntrada;
    
    public EnsambladorComunicacionEventos() {
        ensamblar();
    }
    
    public void ensamblar(){
    //....
        colaSalida = new ColaDePrioridad<>();
        Empaquetador empaquetador = new Empaquetador();
        empaquetador.setColaSalida(colaSalida);
    }
    
    
}

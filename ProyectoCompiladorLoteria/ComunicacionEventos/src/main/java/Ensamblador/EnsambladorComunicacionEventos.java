package Ensamblador;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Evento.Evento;
import colaGenerica.ColaDePrioridad;
import interfacesGlobales.IEvento;
import java.io.IOException;

public class EnsambladorComunicacionEventos {

    private ColaDePrioridad<String> colaSalida;
    private ColaDePrioridad<IEvento> colaEntrada;
    private EnsambladorRed red;

    public EnsambladorComunicacionEventos() throws IOException {
        ensamblar();
    }

    public void ensamblar() throws IOException {
        //....

        int puerto = 5000;
        red = new EnsambladorRed(puerto);
        colaSalida = new ColaDePrioridad<>();
        Empaquetador empaquetador = new Empaquetador();
        empaquetador.setColaSalida(colaSalida);

        Desempaquetador desempaquetador = new Desempaquetador();
        red.ensamblar(desempaquetador);
         
    }

}

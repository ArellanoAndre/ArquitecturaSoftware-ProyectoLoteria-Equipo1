/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import Send.ColaGenerica;
import interfaces.IReceptor;
import java.util.List;
import dispatcher.Dispatcher;

/**
 *
 * @author abrilislas
 */

/**
 * Factory responsable de crear instancias configurables del Dispatcher por medio de inyección
 */
public class DispatcherFactory{
    /**
     *
     */
    private boolean autoStart = true;
    List<IReceptor> receptores;
    private ColaGenerica<String> colaEntrada;


    /**
     * Crea un Dispatcher con las colas default
     * Este metodo sera usado por el singleton y el ensamblador.
     */
    public Dispatcher createDispatcherDefault() {
        ColaGenerica<String> colaEntrada = new ColaGenerica<>();
        ColaGenerica<String> colaSalida = new ColaGenerica<>();
        return new Dispatcher(null, colaEntrada, colaSalida);
    }

    /**
     * Crea un Dispatcher con colas personalizadas (inyección de dependencias).
     * @param colaEntrada cola de entrada observada
     * @param colaSalida cola de salida para despachar mensajes
     * @return Dispatcher listo
     */
    public Dispatcher createDispatcherCustom(List<IReceptor> receptores, ColaGenerica<String> colaEntrada, ColaGenerica<String> colaSalida) {
        return new Dispatcher(receptores, colaEntrada, colaSalida);
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ensamblador;

import RedEventos.EventoRed;
import interfacesRed.IReceptorJSON;
import Send.Sender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import dispatcher.DispatcherFactory;

import mecanismoRecepcion.MecanismoRecepcion;
import networkListener.NetworkListener;

import java.io.IOException;

/**
 * EnsambladorRed
 *
 * Se encarga de:
 * - Crear colas de entrada y salida.
 * - Crear el Sender y suscribirlo a la cola de salida.
 * - Crear el Dispatcher y conectarlo con la cola de salida.
 * - Crear el mecanismo de recepción (MecanismoRecepcion) y conectarlo con la cola de entrada.
 * - Crear y arrancar el NetworkListener (servidor) en un hilo separado.
 */
public class EnsambladorRed {

    private final int puerto;

    private Dispatcher dispatcher;
    private Sender sender;
    private NetworkListener listener;
    private MecanismoRecepcion recepcion;
    

    // Cola de salida: aquí se encolarán los eventos de red a enviar
    private ColaDePrioridad<EventoRed> colaSalida;

    // Cola de entrada: aquí se encolarán los JSON (String) recibidos por red
    private ColaDePrioridad<String> colaEntrada;

    public ColaDePrioridad<String> getColaEntrada() {
        return colaEntrada;
    }

    /**
     * Constructor para el ensamblador de red.
     *
     * @param puerto Puerto en el que escuchará el servidor (NetworkListener)
     */
    public EnsambladorRed(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Ensambla todos los componentes de la capa de red.
     *
     * @param receptor Implementación de IReceptor, que procesará los mensajes recibidos.
     */
    public void ensamblar(IReceptorJSON receptorJSON) throws IOException {
        
        // Crear colas de prioridad
        colaSalida = new ColaDePrioridad<>();
        colaEntrada = new ColaDePrioridad<>();

        // Crear Sender y asociarlo con la cola de salida
        sender = new Sender(colaSalida);
        colaSalida.addObserverSalida(sender);

        // Crear Dispatcher (por medio de fábrica / singleton)
        dispatcher = DispatcherFactorySingleton();

        // Inyectar la cola de salida al dispatcher
        // (El dispatcher encolará IEventoRed que luego el Sender enviará)
        dispatcher.setColaSalida(colaSalida);

        // Crear mecanismo de recepción: leerá de colaEntrada y llamará a receptor.recibir(...)
        recepcion = new MecanismoRecepcion(colaEntrada, receptorJSON);

        // Si MecanismoRecepcion corre en su propio hilo, podrías hacer algo así:
        // new Thread(recepcion, "MecanismoRecepcion-Thread").start();
        // o recepcion.start(); si tú lo defines así.

        // Crear listener (servidor) y arrancarlo en un hilo separado
        listener = new NetworkListener(puerto, colaEntrada);

        // Si tu NetworkListener tiene método start() (como en la versión que te propuse):
        listener.start();

        // Si en cambio solo implementa Runnable, podrías hacer:
        // new Thread(listener, "NetworkListener-Thread").start();

        System.out.println("[EnsambladorRed] Componentes ensamblados correctamente.");
    }

    private Dispatcher DispatcherFactorySingleton() {
        Dispatcher dispatcher = DispatcherFactory.createDispatcherDefault();
        if (dispatcher == null) {
            System.out.println("No hay dispatcher");
        }
        else {
            System.out.println("Si hay dispatcher");
        }
        return dispatcher;
    }

    // Getters para que otras capas puedan interactuar con la red:

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ColaDePrioridad<EventoRed> getColaSalida() {
        return colaSalida;
    }

    public Sender getSender() {
        return sender;
    }

    public NetworkListener getListener() {
        return listener;
    }

    public MecanismoRecepcion getRecepcion() {
        return recepcion;
    }
}

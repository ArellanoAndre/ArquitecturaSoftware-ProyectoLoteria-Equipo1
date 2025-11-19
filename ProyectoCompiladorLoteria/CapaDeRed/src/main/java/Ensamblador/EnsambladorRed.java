/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ensamblador;

import Send.Sender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import dispatcher.DispatcherFactory;
import interfaces.IReceptor;
import java.io.IOException;
import java.net.Socket;
import mecanismoRecepcion.MecanismoRecepcion;
import networkListener.NetworkListener;

/**
 *
 * @author isaac
 */
public class EnsambladorRed {

    private Socket socket;
    private Dispatcher dispatcher;
    private Sender sender;
    private NetworkListener listener;
    private MecanismoRecepcion recepcion;
    private ColaDePrioridad<String> colaSalida;
    private ColaDePrioridad<String> colaEntrada;

    //Constructores Para iniciar como cliente o servidor
    
    public EnsambladorRed(Socket socket) throws IOException {
        this.socket = socket;
    }


    public void ensamblar(IReceptor receptor) throws IOException {
        System.out.println("[EnsambladorRed] Iniciando ensamblaje...");

        // Crear socket y colas
        colaSalida = new ColaDePrioridad<>();
        colaEntrada = new ColaDePrioridad<>();

        // Crear Sender y asociarlo con la cola de salida
        sender = new Sender(socket, colaSalida);
        colaSalida.addObserverSalida(sender);

        // Crear Dispatcher singleton
        dispatcher = DispatcherFactorySingleton();

        // Inyectar la cola de salida al dispatcher
        dispatcher.setColaSalida(colaSalida);

        // Crear listener y mecanismo de recepción
        listener = new NetworkListener(socket, colaEntrada);
        new Thread(listener, "NetworkListener-Thread").start();

        recepcion = new MecanismoRecepcion(colaEntrada, receptor);

        System.out.println("[EnsambladorRed] Componentes ensamblados correctamente.");
    }

    private Dispatcher DispatcherFactorySingleton() {
        return DispatcherFactory.createDispatcherDefault();
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ColaDePrioridad<String> getColaSalida() {
        return colaSalida;
    }

    public Sender getSender() {
        return sender;
    }
}

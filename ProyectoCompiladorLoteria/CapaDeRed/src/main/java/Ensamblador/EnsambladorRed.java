/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ensamblador;

import ColaGenerica.ColaGenerica;
import Send.Sender;
import dispatcher.Dispatcher;
import dispatcher.DispatcherFactory;
import interfaces.IReceptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import mecanismoRecepcion.MecanismoRecepcion;
import networkListener.NetworkListener;

/**
 *
 * @author isaac
 */
public class EnsambladorRed {

    private Socket socket;
    private final String host;
    private final int puerto;
    private Dispatcher dispatcher;
    private Sender sender;
    private NetworkListener listener;
    private ColaGenerica<String> colaSalida;
    private ColaGenerica<String> colaEntrada;
    private IReceptor receptor;

    public EnsambladorRed(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    private Socket crearSocket(String host, int puerto) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, puerto), 5000);
        return socket;
    }

    public void ensamblar(IReceptor receptor) throws IOException {
        System.out.println("[EnsambladorRed] Iniciando ensamblaje...");

        this.receptor = receptor;

        // Crear socket y colas
        socket = crearSocket(host, puerto);
        colaSalida = new ColaGenerica<>();
        colaEntrada = new ColaGenerica<>();

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

        new MecanismoRecepcion(colaEntrada, receptor);

        System.out.println("[EnsambladorRed] Componentes ensamblados correctamente.");
    }

    private Dispatcher DispatcherFactorySingleton() {
        DispatcherFactory factory = new DispatcherFactory();
        return factory.createDispatcherDefault();
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ColaGenerica<String> getColaSalida() {
        return colaSalida;
    }

    public Sender getSender() {
        return sender;
    }
}

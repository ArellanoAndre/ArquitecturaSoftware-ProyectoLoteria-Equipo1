/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ensamblador;

import ColaGenerica.ColaGenerica;
import Send.Sender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import dispatcher.DispatcherFactory;
import interfaces.IReceptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import mecanismoRecepcion.MecanismoRecepcion;
import networkListener.NetworkListener;

/**
 *
 * @author isaac
 */
public class EnsambladorRed {

    private Socket socket;
    private String host = "";
    private final int puerto;
    private Dispatcher dispatcher;
    private Sender sender;
    private NetworkListener listener;
    private ColaDePrioridad<String> colaSalida;
    private ColaDePrioridad<String> colaEntrada;
    private IReceptor receptor;

    //Constructores Para iniciar como cliente o servidor
    
    //Cliente
    public EnsambladorRed(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    //Servidor
    public EnsambladorRed(int puerto) throws IOException {
        this.puerto = puerto;
        
        ServerSocket server = new ServerSocket(puerto);
        socket = server.accept();
        
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

    public ColaDePrioridad<String> getColaSalida() {
        return colaSalida;
    }

    public Sender getSender() {
        return sender;
    }
}

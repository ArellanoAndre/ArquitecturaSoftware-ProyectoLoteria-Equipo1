/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ensamblador;

import Send.ColaGenerica;
import Send.Sender;
import dispatcher.Dispatcher;
import interfaces.IReceptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import networkListener.NetworkListener;

/**
 *
 * @author isaac
 */
public class EnsambladorRed2 {

    private Socket socket;
    private final String host;
    private final int puerto;
    private Dispatcher dispatcher;
    private Sender sender;
    private NetworkListener listener;
    private ColaGenerica<String> colaSalida;
    private ColaGenerica<String> colaEntrada;
    private IReceptor receptor;

    /**
     * Constructor del ensamblador.
     *
     * @param host Dirección del broker.
     * @param puerto Puerto TCP de conexión.
     */
    public EnsambladorRed2(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Ensambla los componentes del flujo de salida.
     */
    public Socket crearSocket(String host, int puerto) throws IOException {

        socket = new Socket();
        socket.connect(new InetSocketAddress(host, puerto), 5000);
        return socket;

    }

    public void ensamblar(String host, int puerto) throws IOException {
        System.out.println("[EnsambladorRed] Iniciando ensamblaje...");

        // crear socket 
        socket = crearSocket(host, puerto);
        // Crear colas de salida y entrasa
        colaSalida = new ColaGenerica<>();
        colaEntrada = new ColaGenerica<>();

        // Crear sender configurado
        sender = new Sender(socket, colaSalida);
        // Registrar sender como observador de la cola
        colaSalida.addObserverSalida(sender);

      //  Dispatcher dispatcher = new Dispatcher(colaSalida, colaEntrada); // dispatcher falta moverle
        //crear listener
        listener = new NetworkListener(socket, colaEntrada);
        
        
        System.out.println("[EnsambladorRed] Cola y Sender conectados correctamente.");
    }

    /**
     * Devuelve la cola de salida, por ejemplo para que otras capas agreguen
     * mensajes.
     */
    public ColaGenerica<String> getColaSalida() {
        return colaSalida;
    }

    /**
     * Devuelve el sender, por si se necesita control directo.
     */
    public Sender getSender() {
        return sender;
    }

}

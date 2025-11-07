/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ensamblador;

import Send.ColaGenerica;
import Send.Sender;
import dispatcher.Dispatcher;
import dispatcher.DispatcherFactory;

/**
 * @author Arell
 * EnsambladorRed
 * 
 * Clase responsable de ensamblar los componentes de red:
 * - Crea la ColaGenerica<String> (cola de salida)
 * - Crea el Sender
 * - Establece la relación Observer entre ambos
 * 
 * No ejecuta lógica de red; solo configura y conecta los componentes.
 */
public class EnsambladorRed {

    private final String host;
    private final int puerto;
    private ColaGenerica<String> colaSalida;
    private ColaGenerica<String> colaEntrada;
    private Sender sender;
    private Dispatcher dispatcher;

    /**
     * Constructor del ensamblador.
     * @param host Dirección del broker.
     * @param puerto Puerto TCP de conexión.
     */
    public EnsambladorRed(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    /**
     * Ensambla los componentes del flujo de salida.
     */
    public void ensamblar() {
        System.out.println("[EnsambladorRed] Iniciando ensamblaje...");

        // Crear cola de salida
        colaSalida = new ColaGenerica<>();
        
        //cola entrada
        colaEntrada = new ColaGenerica<>();

        // Crea el Dispatcher por medio de la fabrica, se inyectan las colas 
        DispatcherFactory factory = new DispatcherFactory();
        dispatcher = factory.createDispatcherCustom(null, colaEntrada, colaSalida);
        
        // Crear sender configurado
        sender = new Sender(host, puerto, colaSalida);

        // Registrar sender como observador de la cola
        colaSalida.addObserverSalida(sender);

        System.out.println("[EnsambladorRed] Cola y Sender conectados correctamente.");
    }

    /**
     * Devuelve la cola de salida, por ejemplo para que otras capas agreguen mensajes.
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
    
    /**
     * Devuelve el dispatcher ensamblado.
     */
    public Dispatcher getDispatcher() {
        return dispatcher;
    }
}

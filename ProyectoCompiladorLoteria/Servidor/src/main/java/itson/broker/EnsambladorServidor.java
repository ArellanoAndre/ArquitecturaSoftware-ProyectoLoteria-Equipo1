/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.broker;

/**
 *
 * @author Arell
 */

import Ensamblador.EnsambladorComunicacionEventos;
import Ensamblador.EnsambladorRed;
import dispatcher.Dispatcher;
import interfaces.IReceptor;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;
import java.io.IOException;
import java.net.Socket;

public class EnsambladorServidor {

    private final EnsambladorRed ensambladorRed;
    private final EnsambladorComunicacionEventos ensambladorEventos;

    public EnsambladorServidor(Socket socket) throws IOException {
        // 1) Lógica superior (IManejadorEvento)
        IManejadorEvento manejador = new ManejadorEventosServidor();

        // 2) Capa de eventos
        ensambladorEventos = new EnsambladorComunicacionEventos(manejador);

        // 3) Capa de red (modo servidor)
        ensambladorRed = new EnsambladorRed(socket);

        // 4) Adaptador IReceptor: MecanismoRecepcion → capa de eventos
        IReceptor receptorAdapter = new ReceptorEventosAdapter(ensambladorEventos);

        // 5) Ensamblar capa de red con ese receptor
        ensambladorRed.ensamblar(receptorAdapter);

        // 6) Adaptador de dispatcher: capa de red → capa de eventos
        Dispatcher dispatcherRed = ensambladorRed.getDispatcher();
        DispatcherAdapter dispatcherEventos = new DispatcherAdapter(dispatcherRed);

        // 7) Conectar dispatcher adaptado a la capa de eventos
        ensambladorEventos.asignarDispatcher(dispatcherEventos);

        System.out.println("[EnsambladorServidor] Servidor ensamblado correctamente.");
    }

    // Método para que la lógica (u otro componente) pueda mandar eventos a la red
    public void enviarEvento(IEvento evento) {
        ensambladorEventos.enviarEventoDesdeLogica(evento);
    }
}


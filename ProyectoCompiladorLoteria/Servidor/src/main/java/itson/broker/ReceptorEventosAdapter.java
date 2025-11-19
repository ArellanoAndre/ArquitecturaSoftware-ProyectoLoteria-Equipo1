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
import interfaces.IReceptor;

public class ReceptorEventosAdapter implements IReceptor {

    private final EnsambladorComunicacionEventos eventos;

    public ReceptorEventosAdapter(EnsambladorComunicacionEventos eventos) {
        this.eventos = eventos;
    }

    @Override
    public void mandarMensaje(String json) {
        // Lo que llega desde MecanismoRecepcion va a la capa de eventos
        eventos.recibirJSONDesdeRed(json);
    }
}


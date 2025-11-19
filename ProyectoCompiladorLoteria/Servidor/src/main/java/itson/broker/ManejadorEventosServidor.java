/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.broker;

/**
 *
 * @author Arell
 */

import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;

public class ManejadorEventosServidor implements IManejadorEvento {

    @Override
    public void manejar(IEvento evento) {
        System.out.println("[ManejadorEventosServidor] Evento recibido:");
        System.out.println("  tipo: " + evento.getTipo());
        System.out.println("  detalle: " + evento.toString());
        // Aquí en el futuro puedes decidir si generas respuestas, etc.
    }
}


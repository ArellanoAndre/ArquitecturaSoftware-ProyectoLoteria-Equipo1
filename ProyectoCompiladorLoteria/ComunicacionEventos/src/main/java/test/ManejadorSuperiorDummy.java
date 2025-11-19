/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author Arell
 */

import Empaquetador.Empaquetador;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;

public class ManejadorSuperiorDummy implements IManejadorEvento {

    private Empaquetador empaquetador;

    public ManejadorSuperiorDummy(Empaquetador empaquetador) {
        this.empaquetador = empaquetador;
    }

    public ManejadorSuperiorDummy() {
    }

    @Override
    public void manejar(IEvento evento) {
        System.out.println("[ManejadorDummy] Recibí un evento: " + evento.getTipo());

        // Crear una respuesta y mandarla para afuera
        EventoDummy respuesta = new EventoDummy("Respuesta desde el manejador");
        empaquetador.empaquetar(respuesta);
    }
}


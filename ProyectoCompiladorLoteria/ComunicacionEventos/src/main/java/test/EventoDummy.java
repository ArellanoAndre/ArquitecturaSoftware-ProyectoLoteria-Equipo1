/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author Arell
 */

import interfacesGlobales.IEvento;

public class EventoDummy implements IEvento {
    private String tipo = "TEST_EVENTO";
    private String mensaje;

    public EventoDummy(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() { return mensaje; }

    public String getTipo() {
        return tipo;
    }
}


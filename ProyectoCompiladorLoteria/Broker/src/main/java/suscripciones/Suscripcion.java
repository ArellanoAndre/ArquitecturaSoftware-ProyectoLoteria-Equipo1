/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package suscripciones;

import java.util.Objects;

/**
 *
 * @author isaac
 */
public class Suscripcion {

    private final String host;
    private final int puerto;

    public Suscripcion(String host, int puerto) {
        this.host = host;
        this.puerto = puerto;
    }

    public String getHost() {
        return host;
    }

    public int getPuerto() {
        return puerto;
    }

    @Override
    public String toString() {
        return "Suscriptor[" + host + ":" + puerto + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suscripcion)) {
            return false;
        }
        Suscripcion s = (Suscripcion) o;
        return puerto == s.puerto && host.equals(s.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, puerto);
    }

}

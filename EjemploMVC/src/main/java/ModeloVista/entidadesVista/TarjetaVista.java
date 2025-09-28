/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista.entidadesVista;

import ModeloJuego.*;

/**
 *
 * @author rodri
 */
public class TarjetaVista {

    private boolean[] marcadas; // si la carta fue marcada
    // imagen de toda la tarjeta

    public TarjetaVista(CartaVista[] casillas) {
        this.marcadas = new boolean[casillas.length];
    }

    
    public void marcarCasilla(int numCasilla) {
        marcadas[numCasilla] = true;
    }

    public boolean[] getMarcadas() {
        return marcadas;
    }

    @Override
    public String toString() {
        return "Tarjeta{" + "marcadas= " + marcadas + '}';
    }

}

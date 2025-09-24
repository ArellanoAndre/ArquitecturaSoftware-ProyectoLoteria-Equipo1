/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

/**
 *
 * @author rodri
 */
public class Tarjeta {

    private Carta[] casillas;   // 4x4
    private boolean[] marcadas; // si la carta fue marcada

    public Tarjeta(Carta[] casillas) {
        this.casillas = casillas;
        this.marcadas = new boolean[16];
    }

    public Carta getCasilla(int numCasilla) {
        return casillas[numCasilla];
    }
    
    public void marcarCasilla(int numCasilla) {
        marcadas[numCasilla] = true;
    }

    public boolean[] getMarcadas() {
        return marcadas;
    }

    public Carta[] getCasillas() {
        return casillas;
    }

}

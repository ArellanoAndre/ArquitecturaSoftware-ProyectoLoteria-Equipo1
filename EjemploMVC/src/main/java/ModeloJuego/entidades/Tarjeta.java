/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego.entidades;

/**
 *
 * @author rodri
 */
public class Tarjeta {

    private int[] casillas;   // 4x4
    private boolean[] marcadas; // si la carta fue marcada
    // imagen de toda la tarjeta

    public Tarjeta(int[] casillas) {
        this.casillas = casillas;
        this.marcadas = new boolean[casillas.length];
    }

    public int getCasilla(int numCasilla) {
        return casillas[numCasilla];
    }
    
    public void marcarCasilla(int numCasilla) {
        marcadas[numCasilla] = true;
    }

    public boolean[] getMarcadas() {
        return marcadas;
    }

    public int[] getCasillas() {
        return casillas;
    }

    @Override
    public String toString() {
        return "Tarjeta{" + "casillas=" + casillas + ", marcadas=" + marcadas + '}';
    }

    public void setMarcadas(boolean[] marcadas) {
        this.marcadas = marcadas;
    }
    

}

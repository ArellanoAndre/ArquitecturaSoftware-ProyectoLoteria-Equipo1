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
    private String img;

    public Tarjeta(int[] casillas, String img) {
        this.casillas = casillas;
        this.marcadas = new boolean[casillas.length];
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Tarjeta{" + "casillas=" + casillas + ", marcadas=" + marcadas + '}';
    }

    public void setMarcadas(boolean[] marcadas) {
        this.marcadas = marcadas;
    }
    

}

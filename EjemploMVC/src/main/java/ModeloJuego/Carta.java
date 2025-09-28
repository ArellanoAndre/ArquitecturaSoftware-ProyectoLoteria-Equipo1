/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

/**
 *
 * @author rodri
 */
public class Carta {
    
    private int numCarta;
    private String nombreCarta;
    private boolean activada;
    // poner imagen 

    public Carta(int numCarta, String nombreCarta) {
        this.numCarta = numCarta;
        this.nombreCarta = nombreCarta;
        this.activada = false;
    }

    public int getNumCarta() {
        return numCarta;
    }

    public void setNumCarta(int numCarta) {
        this.numCarta = numCarta;
    }

    public String getNombreCarta() {
        return nombreCarta;
    }

    public void setNombreCarta(String nombreCarta) {
        this.nombreCarta = nombreCarta;
    }

    public boolean isActivada() {
        return activada;
    }

    public void setActivada(boolean activada) {
        this.activada = activada;
    }

    @Override
    public String toString() {
        return "Carta{" + "numCarta=" + numCarta + ", nombreCarta=" + nombreCarta + ", activada=" + activada + '}';
    }
    
    
    
}

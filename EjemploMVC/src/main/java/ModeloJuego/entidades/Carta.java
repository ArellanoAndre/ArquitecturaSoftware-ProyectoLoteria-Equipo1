/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego.entidades;

/**
 *
 * @author rodri
 */
public class Carta {
    
    private int numCarta;
    private String nombreCarta;
    // poner imagen 

    public Carta(int numCarta, String nombreCarta) {
        this.numCarta = numCarta;
        this.nombreCarta = nombreCarta;
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

    @Override
    public String toString() {
        return "Carta{" + "numCarta=" + numCarta + ", nombreCarta=" + nombreCarta + '}';
    }
    
    
    
}

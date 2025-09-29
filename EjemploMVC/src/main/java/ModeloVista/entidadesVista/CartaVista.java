/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista.entidadesVista;

/**
 *
 * @author rodri
 */
public class CartaVista {
    
    private int numCarta;
    private String nombreCarta;
    private String ruta;

    public CartaVista(int numCarta, String nombreCarta, String ruta) {
        this.numCarta = numCarta;
        this.nombreCarta = nombreCarta;
        this.ruta = ruta;
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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String url) {
        this.ruta = url;
    }
    
    

    @Override
    public String toString() {
        return "Carta{" + "numCarta=" + numCarta + ", nombreCarta=" + nombreCarta + '}';
    }
    
    
    
}

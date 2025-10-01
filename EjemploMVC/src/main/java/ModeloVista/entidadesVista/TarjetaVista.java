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
    private String img;

    public TarjetaVista(boolean[] casillas,String img) {
        this.marcadas = casillas;
        this.img = img;
    }

    public void setMarcadas(boolean[] marcadas) {
        this.marcadas = marcadas;
    }

    public boolean[] getMarcadas() {
        return marcadas;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    
    
    @Override
    public String toString() {
        return "Tarjeta{" + "marcadas= " + marcadas + '}';
    }

}

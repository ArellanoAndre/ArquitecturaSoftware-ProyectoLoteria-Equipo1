/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConvertidorJSON;

/**
 *
 * @author Arell
 */

public class Evento {
    private EnumTipoEvento tipo;
    private int idJugador;
    private String cartaSeleccionada;
    private int posicion;

    public Evento(EnumTipoEvento tipo, int idJugador, String cartaSeleccionada, int posicion) {
        this.tipo = tipo;
        this.idJugador = idJugador;
        this.cartaSeleccionada = cartaSeleccionada;
        this.posicion = posicion;
    }

    public EnumTipoEvento getTipo() { return tipo; }
    public int getIdJugador() { return idJugador; }
    public String getCartaSeleccionada() { return cartaSeleccionada; }
    public int getPosicion() { return posicion; }
}

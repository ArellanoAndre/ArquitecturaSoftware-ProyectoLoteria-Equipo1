/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import ModeloJuego.Carta;
import ModeloJuego.ModeloJuego;
import Observer.Observer;

/**
 *
 * @author isaac
 */
public interface IModeloVista {
    
    void setModeloJuego(ModeloJuego modeloJuego);
    void setCartaCantada(Carta carta);
     String getCartaCantada();
     int getMarcador();
     void setMarcador(int marcador);
     JugadorVista getJugadorPrincipal();
    void setJugadorPrincipal(JugadorVista jugadorPrincipal);
    void mostrarTarjetaJugador(int[] arregloCartas);
    void actualizarTarjetaJugadorP (int[] casillas);
    void seleccionarCarta(int pos);
    void addObserver(Observer o);
     void notificar();
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import ModeloJuego.ModeloJuego;
import ModeloJuego.entidades.Carta;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;

/**
 *
 * @author isaac
 */
public interface IModeloVista {
    
    // Métodos para la Vista 
    String getCartaCantada();
    JugadorVista getJugadorPrincipal();
    void setJugadorPrincipal(JugadorVista jugadorPrincipal);

    // Métodos para el Controlador (enviar acciones)
    void seleccionarCarta(int pos);

    // Métodos para el ModeloJuego
    void setCartaCantada(Carta carta);
    void actualizarTarjetaJugadorP(boolean[] casillas);
    void setModeloJuego(ModeloJuego modeloJuego);
    void setMarcador(int marcador);
    int getMarcador();
    void setCartaCantada(CartaVista carta);
    
   
   
    // Métodos para el patrón Observer
    void addObserver(Observer o);
    void removeObserver(Observer o);
}


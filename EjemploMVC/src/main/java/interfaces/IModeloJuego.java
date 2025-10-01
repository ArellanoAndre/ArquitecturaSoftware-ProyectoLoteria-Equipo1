/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import ModeloJuego.Carta;
import ModeloJuego.Jugador;
import java.util.List;

/**
 *
 * @author isaac
 */
public interface IModeloJuego {
    
     void iniciarJuego();
     void siguienteCarta();
     void verificarCarta(int cartaSeleccionada);
     Carta getCartaActual();
     int getMarcador();
     List<Carta> crearMazo();
     void barajear();
     Jugador crearJugador(List<Carta> mazo);
     
     
    
}

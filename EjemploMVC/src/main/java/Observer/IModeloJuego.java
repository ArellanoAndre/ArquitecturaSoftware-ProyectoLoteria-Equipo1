/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import ModeloJuego.ModeloJuego;
import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import java.util.List;

/**
 *
 * @author isaac
 */
public interface IModeloJuego {
    
    void iniciarJuego();
    void siguienteCarta();
    void verificarCarta(int cartaSeleccionada);
    void actualizarJugadorSecundario(int casilla, int numJugador);
    Carta getCartaActual();
    int getMarcador();
    IModeloJuego getModeloJuegoSecundario();
    void setModeloJuegoSecundario(IModeloJuego modeloJuegoSecundario);
    List<Carta> getMazo();
    void setMazo(List<Carta> mazo);
    List<Carta> crearMazo();
    void barajear();
    
    
    
}

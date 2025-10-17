/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import java.util.List;

/**
 *
 * @author isaac
 */
public interface IControlVista {
    
    void setJugadorPrincipal(Jugador jugador);
    void actualizarTarjetaJugadorPrincipal(boolean[] marcadas);
    void setJugadoresSecundarios(List<Jugador> jugadores);
    void actualizarCartaCantada (Carta cartaActual);
    
}

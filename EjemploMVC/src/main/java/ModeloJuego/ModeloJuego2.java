/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import Observer.IControlVista;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author rodri
 */
public class ModeloJuego2 {
    
    private List<Carta> mazo;
    private Jugador jugadorPrincipal;
    private List<Jugador> jugadoresSecundarios;
    private Carta cartaActual;
    private int marcador = 0;
    private int contador = 0;
    private Timer timer;
    private IControlVista controlVista;
    
    public ModeloJuego2(IControlVista controlVista, Jugador jugador, List<Jugador> jugadores, List<Carta> mazo) {
        this.controlVista = controlVista;
        this.mazo = mazo;
        this.jugadorPrincipal = jugador;
        this.jugadoresSecundarios = jugadores;
        controlVista.setJugadorPrincipal(jugador);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
    }
    
    public void verificarCarta(int casillaSeleccionada){
    }
    
    
    
}

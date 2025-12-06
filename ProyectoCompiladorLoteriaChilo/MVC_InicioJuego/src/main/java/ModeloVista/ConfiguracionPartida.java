/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista;

import interfacesEntidades.IConfiguracionPartida;
import interfacesEntidades.IJugador;
import java.util.List;

/**
 *
 * @author Arell
 */
public class ConfiguracionPartida extends Subject implements IConfiguracionPartida{

    private String dificultad;
    private List<IJugador> jugadores; 
    private int puntuacionMaxima;
    
    


    @Override
    public void setDatos(String dificultad, List<IJugador> jugadores, int puntuacionMaxima) {
        this.dificultad = dificultad;
        this.jugadores = jugadores;
        this.puntuacionMaxima = puntuacionMaxima;
        notifyObservers();
        
    }

    @Override
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    @Override
    public void setJugadores(List<IJugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public void setPuntuacionMaxima(int puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    @Override
    public String getDificultad() { return dificultad; }
    @Override
    public List<IJugador> getJugadores() { return jugadores; }
    @Override
    public int getPuntuacionMaxima() { return puntuacionMaxima; }
    
}

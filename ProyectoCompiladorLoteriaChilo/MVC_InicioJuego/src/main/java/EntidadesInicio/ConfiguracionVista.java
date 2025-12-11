/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesInicio;

import interfacesEntidades.IConfiguracionPartida;
import interfacesEntidades.IJugador;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arell
 */
public class ConfiguracionVista implements IConfiguracionPartida {
  
    private String dificultad;
    private List<IJugador> jugadores; 
    private int numRondas;
    private List<String> imagenesTarjetas;
    private int numJugadores;

    @Override
    public void setDatos(String dificultad,
                     List<IJugador> jugadores,
                     int numRondas,
                     List<String> tarjetas,
                     int numJugadores) {

    this.dificultad = dificultad;
    this.jugadores = jugadores != null ? jugadores : new ArrayList<>();
    this.numRondas = numRondas;
    this.imagenesTarjetas = tarjetas != null ? tarjetas : new ArrayList<>();
    this.numJugadores = numJugadores;
}


    @Override
    public int getNumJugadores() {
        return numJugadores;
    }
    
    @Override
    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
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
    public void setNumeroRondas(int numRondas) {
        this.numRondas = numRondas;
    }

    @Override
    public String getDificultad() { return dificultad; }
    @Override
    public List<IJugador> getJugadores() { return jugadores; }
    
    @Override
    public int getNumeroRondas() { return numRondas; }

    @Override
    public List<String> getImagenesTarjetas() {
        return imagenesTarjetas;
    }

    @Override
    public void setImagenesTarjetas(List<String> imagenesTarjetas) {
        this.imagenesTarjetas = imagenesTarjetas;
    }

    
}

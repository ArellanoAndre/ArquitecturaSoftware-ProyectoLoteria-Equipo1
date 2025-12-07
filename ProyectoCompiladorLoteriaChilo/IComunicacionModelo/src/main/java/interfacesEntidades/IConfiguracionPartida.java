/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesEntidades;

import java.util.List;

/**
 *
 * @author Arell
 */
public interface IConfiguracionPartida {
    void  setDatos(String dificultad, List<IJugador> jugadores, int puntuacionMaxima,List<String> imagenesTarjetas,int numJugadores);
    void setDificultad(String dificultad);
    void setJugadores(List<IJugador> jugadores);
    void setPuntuacionMaxima(int puntuacionMaxima);
    String getDificultad();
    int getNumJugadores();
    List<IJugador> getJugadores();
    void setNumJugadores(int numJugadores);
    int getPuntuacionMaxima();
    List<String> getImagenesTarjetas();
    void setImagenesTarjetas(List<String> imagenesTarjetas);
    
}

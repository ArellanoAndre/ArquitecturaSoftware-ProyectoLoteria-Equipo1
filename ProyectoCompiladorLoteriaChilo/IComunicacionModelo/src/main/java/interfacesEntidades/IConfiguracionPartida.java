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
    void setDatos(String dificultad, List<IJugador> jugadores, int puntuacionMaxima);
    void setDificultad(String dificultad);
    void setJugadores(List<IJugador> jugadores);
    void setPuntuacionMaxima(int puntuacionMaxima);
    String getDificultad();
    List<IJugador> getJugadores();
    int getPuntuacionMaxima();
}

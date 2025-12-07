/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesLogica;

import interfacesEntidades.IJugador;
import java.util.List;
import logicaJuego.entidades.Jugador;

/**
 *
 * @author isaac
 */
public interface IModeloLogica {

    void EnviarEventoCartaSeleccionada(int pos, int idJugador);

    void notificarCartaCantada(int idCarta, String nombreCarta);

    void notificarJugadaValida(int posicion, int idJugador);

    void notificarGanador(String nombreGanador);
    
     void enviarConfirmacionReglas(
        String dificultad,
        int puntuacionMaxima,
        List<Jugador> jugadores,
        List<String> tarjetas);
     
       void enviarAbrirPantallaConfig();

    void enviarAbrirPantallaSeleccionAvatar();

}

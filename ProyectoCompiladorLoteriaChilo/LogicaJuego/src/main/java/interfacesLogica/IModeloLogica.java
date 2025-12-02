/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesLogica;

/**
 *
 * @author isaac
 */
public interface IModeloLogica {

    void EnviarEventoCartaSeleccionada(int pos, int idJugador);

    void notificarCartaCantada(int idCarta, String nombreCarta);

    void notificarJugadaValida(int posicion, int idJugador);

    void notificarGanador(String nombreGanador);

}

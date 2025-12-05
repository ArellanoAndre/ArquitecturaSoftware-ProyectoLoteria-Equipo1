/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesComunicacionModelo;

/**
 *
 * @author rodri
 */
public interface IModeloJuego {
    
    void EnviarEventoCartaSeleccionada(int pos, int idJugador);
    
    void EnviarEventoConfigurarPartida();
    
    void EnviarEventoIniciarRonda();
}

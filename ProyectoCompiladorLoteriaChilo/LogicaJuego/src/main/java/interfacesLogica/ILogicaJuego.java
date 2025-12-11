/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesLogica;

/**
 *
 * @author isaac
 */
public interface ILogicaJuego {

     void iniciarRonda();

    void agregarJugadores();

    void verificarCarta(int jugadorId, int casillaSeleccionada);

    void siguienteCarta();
    
    boolean estaConfiguradaPartida();

    void enviarAbrirPantallaConfig(int idJugador);

    void enviarAbrirPantallaSeleccionAvatar(int idJugador);

}

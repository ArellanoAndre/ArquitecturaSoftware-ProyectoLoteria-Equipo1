/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesGlobales;

/**
 *
 * @author isaac
 */
public interface ILogicaJuego {

    void iniciarJuego();

    void agregarJugador(String nombre);

    void verificarCarta(int jugadorId, int casillaSeleccionada);

    void siguienteCarta();

    void barajear();

}

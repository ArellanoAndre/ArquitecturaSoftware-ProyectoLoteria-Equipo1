package interfacesComunicacionModelo;

import interfacesEntidades.ICarta;
import interfacesEntidades.IJugador;
import java.util.List;

/**
 * Interfaz que define las operaciones del controlador de la vista. Permite
 * actualizar la información visual de jugadores y cartas en pantalla.
 */
public interface IControlVistaMVC_Juego {

    /**
     * Asigna el jugador principal en la vista.
     *
     * @param jugador Jugador principal del juego.
     */
    void setJugadorPrincipal(IJugador jugador);

    /**
     * Actualiza las casillas marcadas de la tarjeta del jugador principal.
     *
     * @param marcadas Arreglo que indica qué casillas están marcadas.
     */
    void actualizarTarjetaJugadorPrincipal(boolean[] marcadas);

    /**
     * Asigna la lista de jugadores secundarios en la vista.
     *
     * @param jugadores Lista de jugadores secundarios.
     */
    void setJugadoresSecundarios(List<IJugador> jugadores);

    /**
     * Actualiza la carta cantada actual en la vista.
     *
     * @param cartaActual Carta que fue cantada en el juego.
     */
    void actualizarCartaCantada(ICarta cartaActual);

    /**
     * Muestra mensaje de fin de ronda en la vista.
     *
     *
     */
    void mostrarMensajeFinRonda(int ronda, String ganador);

    /**
     * Muestra mensaje de fin de partida en la vista.
     *
     *
     */
    void procesarFinPartida(List<IJugador> ranking);

    

}

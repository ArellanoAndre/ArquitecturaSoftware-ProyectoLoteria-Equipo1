package Observer;

import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import java.util.List;

/**
 * Interfaz que define las operaciones del controlador de la vista. Permite
 * actualizar la información visual de jugadores y cartas en pantalla.
 */
public interface IControlVista {

    /**
     * Asigna el jugador principal en la vista.
     *
     * @param jugador Jugador principal del juego.
     */
    void setJugadorPrincipal(Jugador jugador);

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
    void setJugadoresSecundarios(List<Jugador> jugadores);

    /**
     * Actualiza la carta cantada actual en la vista.
     *
     * @param cartaActual Carta que fue cantada en el juego.
     */
    void actualizarCartaCantada(Carta cartaActual);
}

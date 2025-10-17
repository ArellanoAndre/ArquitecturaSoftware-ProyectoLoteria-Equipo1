package Observer;

import ModeloJuego.entidades.Carta;
import java.util.List;

/**
 * Interfaz que define las operaciones principales del modelo del juego. Permite
 * controlar el flujo del juego, manejar cartas, jugadores y el mazo.
 */
public interface IModeloJuego {

    /**
     * Inicia el juego y establece la carta inicial.
     */
    void iniciarJuego();

    /**
     * Avanza a la siguiente carta del mazo.
     */
    void siguienteCarta();

    /**
     * Verifica si la carta seleccionada coincide con la carta cantada.
     *
     * @param cartaSeleccionada Posición o índice de la carta seleccionada.
     */
    void verificarCarta(int cartaSeleccionada);

    /**
     * Actualiza la información de un jugador secundario.
     *
     * @param casilla Índice de la casilla marcada.
     * @param numJugador Número identificador del jugador.
     */
    void actualizarJugadorSecundario(int casilla, int numJugador);

    /**
     * @return La carta actual cantada en el juego.
     */
    Carta getCartaActual();

    /**
     * @return Marcador actual del jugador principal.
     */
    int getMarcador();

    /**
     * @return Modelo de juego secundario asociado, si existe.
     */
    IModeloJuego getModeloJuegoSecundario();

    /**
     * Establece un modelo de juego secundario.
     */
    void setModeloJuegoSecundario(IModeloJuego modeloJuegoSecundario);

    /**
     * @return Lista completa de cartas del mazo.
     */
    List<Carta> getMazo();

    /**
     * Asigna un mazo de cartas.
     */
    void setMazo(List<Carta> mazo);

    /**
     * Crea un mazo nuevo con todas las cartas del juego.
     */
    List<Carta> crearMazo();

    /**
     * Mezcla el mazo de cartas de manera aleatoria.
     */
    void barajear();
}

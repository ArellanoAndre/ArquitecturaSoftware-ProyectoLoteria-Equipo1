package Observer;

import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import java.util.List;

public interface IModeloVista {

    // Métodos para la Vista
    /**
     * @return la carta cantada actual.
     */
    CartaVista getCartaCantada();

    /**
     * @return el jugador principal.
     */
    JugadorVista getJugadorPrincipal();

    /**
     * Asigna el jugador principal.
     */
    void setJugadorPrincipal(JugadorVista jugadorPrincipal);

    // Métodos para el Controlador (enviar acciones)
    /**
     * Llamado por el controlador cuando se selecciona una carta. Envía la
     * selección al modelo del juego para su validación.
     *
     * @param pos posición de la carta seleccionada.
     */
    void seleccionarCarta(int pos);

    // Métodos para el ModeloJuego
    /**
     * Actualiza las casillas marcadas del jugador principal y notifica a los
     * observadores.
     *
     * @param casillas arreglo con el estado de las casillas.
     */
    void actualizarTarjetaJugadorP(boolean[] casillas);

    /**
     * Asigna el modelo de juego con el cual se comunica.
     */
    void setModeloJuego(IModeloJuego modeloJuego);

    /**
     * Actualiza el marcador y notifica a los observadores.
     *
     * @param marcador nuevo valor del marcador.
     */
    void setMarcador(int marcador);

    /**
     * @return el marcador actual del jugador.
     */
    int getMarcador();

    /**
     * Recibe una nueva carta cantada desde el modelo del juego y notifica a los
     * observadores (la vista) para actualizar la pantalla.
     */
    void setCartaCantada(CartaVista carta);

    /**
     * @return lista de jugadores secundarios.
     */
    List<JugadorVista> getJugadoresSecundarios();

    /**
     * Asigna la lista de jugadores secundarios y notifica a los observadores.
     */
    void setJugadoresSecundarios(List<JugadorVista> jugadoresSecundarios);

    // Métodos para el patrón Observer
    /**
     * Agrega un observador (por lo general, la vista principal del juego).
     *
     * @param o objeto que implementa la interfaz Observer.
     */
    void addObserver(Observer o);

    /**
     * Notifica a todos los observadores que hubo un cambio para que actualicen
     * su interfaz.
     */
    void notificar();
}

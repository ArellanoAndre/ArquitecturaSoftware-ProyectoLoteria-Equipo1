package Observer;

/**
 * Interfaz que define las operaciones del controlador para seleccionar cartas.
 * Permite notificar al modelo cuando un jugador selecciona una carta.
 */
public interface IControlSeleccionarCarta {

    /**
     * Indica que se ha seleccionado una carta en la posición indicada.
     * @param pos Posición de la carta seleccionada.
     */
    void seleccionarCarta(int pos);
}


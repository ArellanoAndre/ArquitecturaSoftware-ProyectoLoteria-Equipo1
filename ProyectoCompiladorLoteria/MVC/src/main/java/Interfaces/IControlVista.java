package Interfaces;

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
<<<<<<< Updated upstream:ProyectoCompiladorLoteria/MVC/src/main/java/Interfaces/IControlVista.java
    void actualizarCartaCantada(Carta cartaActual);
=======
    void actualizarCartaCantada(ICarta cartaActual);

    /**
     * Muestra mensaje de fin de ronda en la vista.
     *
     *
     */
    void mostrarMensajeFinRonda(int ronda, String ganador);

    public void mostrarMensajeFinRondaBaraja();
    
    /**
     * Muestra mensaje de fin de partida en la vista.
     *
     *
     */
    void procesarFinPartida(List<IJugador> ranking);

    void solicitarIntentoLoteria();

    void solicitarSiguienteRonda();
    
    public void cambiarMVC();
    
    public void actualizarVistaJugadas(int puntos, String jugada, int idJugador);
    void actualizarPuntos(int puntos);

>>>>>>> Stashed changes:ProyectoCompiladorLoteriaChilo/IComunicacionModelo/src/main/java/interfacesComunicacionModelo/IControlVistaMVC_Juego.java
}

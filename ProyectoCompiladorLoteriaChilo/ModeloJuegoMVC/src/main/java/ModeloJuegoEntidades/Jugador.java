package ModeloJuegoEntidades;

import interfacesEntidades.IJugador;
import interfacesEntidades.ITarjeta;

/**
 * Representa a un jugador dentro del ModeloJuego. Contiene información como su
 * nombre, número, puntaje, tarjeta.
 */
public class Jugador implements IJugador{

    private int numJugador;
    private String nombre;
    private double puntaje;
    private ITarjeta tarjeta;
    private boolean principal;

    /**
     * Constructor que inicializa un jugador con su nombre, tarjeta y número.
     *
     * @param nombre Nombre del jugador.
     * @param tarjeta Tarjeta asignada al jugador.
     * @param numJugador Número identificador del jugador.
     */
    public Jugador(String nombre, ITarjeta tarjeta, int numJugador) {
        this.nombre = nombre;
        this.tarjeta = tarjeta;
        this.puntaje = 0;
        this.principal = false;
        this.numJugador = numJugador;
    }

    /**
     * @return Nombre del jugador.
     */
    @Override
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Puntaje actual del jugador.
     */
    @Override
    public double getPuntaje() {
        return puntaje;
    }

    /**
     * Suma puntos al puntaje actual del jugador.
     *
     * @param puntos Cantidad de puntos a agregar.
     */
    @Override
    public void addPuntos(double puntos) {
        this.puntaje += puntos;
    }

    /**
     * @return Tarjeta del jugador.
     */
    @Override
    public ITarjeta getTarjeta() {
        return tarjeta;
    }

    /**
     * @return true si es el jugador principal, false en caso contrario.
     */
    @Override
    public boolean isPrincipal() {
        return principal;
    }

    /**
     * @param principal Define si el jugador es el principal.
     */
    @Override
    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    /**
     * @return Número del jugador.
     */
    @Override
    public int getNumJugador() {
        return numJugador;
    }

    /**
     * @param numJugador Nuevo número del jugador.
     */
    @Override
    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

}

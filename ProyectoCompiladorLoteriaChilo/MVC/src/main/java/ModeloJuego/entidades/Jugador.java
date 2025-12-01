package ModeloJuego.entidades;

/**
 * Representa a un jugador dentro del ModeloJuego. Contiene información como su
 * nombre, número, puntaje, tarjeta.
 */
public class Jugador {

    private int numJugador;
    private String nombre;
    private double puntaje;
    private Tarjeta tarjeta;
    private boolean principal;

    /**
     * Constructor que inicializa un jugador con su nombre, tarjeta y número.
     *
     * @param nombre Nombre del jugador.
     * @param tarjeta Tarjeta asignada al jugador.
     * @param numJugador Número identificador del jugador.
     */
    public Jugador(String nombre, Tarjeta tarjeta, int numJugador) {
        this.nombre = nombre;
        this.tarjeta = tarjeta;
        this.puntaje = 0;
        this.principal = false;
        this.numJugador = numJugador;
    }

    /**
     * @return Nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return Puntaje actual del jugador.
     */
    public double getPuntaje() {
        return puntaje;
    }

    /**
     * Suma puntos al puntaje actual del jugador.
     *
     * @param puntos Cantidad de puntos a agregar.
     */
    public void addPuntos(double puntos) {
        this.puntaje += puntos;
    }

    /**
     * @return Tarjeta del jugador.
     */
    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    /**
     * @return true si es el jugador principal, false en caso contrario.
     */
    public boolean isPrincipal() {
        return principal;
    }

    /**
     * @param principal Define si el jugador es el principal.
     */
    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    /**
     * @return Número del jugador.
     */
    public int getNumJugador() {
        return numJugador;
    }

    /**
     * @param numJugador Nuevo número del jugador.
     */
    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

}

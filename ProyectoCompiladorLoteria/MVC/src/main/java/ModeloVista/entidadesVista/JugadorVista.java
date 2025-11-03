package ModeloVista.entidadesVista;

/**
 * Representa a un jugador dentro del ModeloVista. Contiene información visual
 * como su nombre, puntaje, tarjeta y avatar mostrado en pantalla.
 */
public class JugadorVista {

    private int numJugador;         // Número identificador del jugador
    private String nombre;          // Nombre del jugador
    private double puntaje;         // Puntaje actual
    private TarjetaVista tarjeta;   // Tarjeta visual asociada al jugador
    private boolean principal;      // Indica si es el jugador principal
    private String rutaAvatar;      // Ruta de la imagen del avatar del jugador

    /**
     * Constructor que inicializa los datos principales del jugador.
     *
     * @param nombre Nombre del jugador.
     * @param tarjeta Tarjeta asociada al jugador.
     * @param numJugador Número identificador del jugador.
     */
    public JugadorVista(String nombre, TarjetaVista tarjeta, int numJugador) {
        this.nombre = nombre;
        this.tarjeta = tarjeta;
        this.puntaje = 0;
        this.principal = false;
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
     * Agrega puntos al puntaje actual del jugador.
     *
     * @param puntos Cantidad de puntos a sumar.
     */
    public void addPuntos(double puntos) {
        this.puntaje += puntos;
    }

    /**
     * @return Tarjeta asociada al jugador.
     */
    public TarjetaVista getTarjeta() {
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
     * @return Número identificador del jugador.
     */
    public int getNumJugador() {
        return numJugador;
    }

    /**
     * @param numJugador Nuevo número para el jugador.
     */
    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }

    /**
     * @return Ruta de la imagen del avatar del jugador.
     */
    public String getRutaAvatar() {
        return rutaAvatar;
    }

    /**
     * @param rutaAvatar Nueva ruta de avatar para el jugador.
     */
    public void setRutaAvatar(String rutaAvatar) {
        this.rutaAvatar = rutaAvatar;
    }
}

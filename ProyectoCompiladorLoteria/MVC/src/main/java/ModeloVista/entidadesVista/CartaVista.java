package ModeloVista.entidadesVista;

/**
 * Representa una carta dentro del ModeloVista, con su número, nombre y ruta de
 * imagen. Se utiliza para mostrar la carta en la interfaz del juego.
 */
public class CartaVista {

    private int numCarta; // Número identificador de la carta
    private String nombreCarta; // Nombre de la carta
    private String ruta;    // Ruta de la imagen asociada

    /**
     * Constructor que inicializa los datos de la carta.
     *
     * @param numCarta Número identificador de la carta.
     * @param nombreCarta Nombre de la carta.
     * @param ruta Ruta de la imagen asociada.
     */
    public CartaVista(int numCarta, String nombreCarta, String ruta) {
        this.numCarta = numCarta;
        this.nombreCarta = nombreCarta;
        this.ruta = ruta;
    }

    /**
     * @return Número de la carta.
     */
    public int getNumCarta() {
        return numCarta;
    }

    /**
     * @param numCarta Nuevo número para la carta.
     */
    public void setNumCarta(int numCarta) {
        this.numCarta = numCarta;
    }

    /**
     * @return Nombre de la carta.
     */
    public String getNombreCarta() {
        return nombreCarta;
    }

    /**
     * @param nombreCarta Nuevo nombre para la carta.
     */
    public void setNombreCarta(String nombreCarta) {
        this.nombreCarta = nombreCarta;
    }

    /**
     * @return Ruta de la imagen asociada a la carta.
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param url Nueva ruta de imagen para la carta.
     */
    public void setRuta(String url) {
        this.ruta = url;
    }

    /**
     * @return Representación en texto de la carta.
     */
    @Override
    public String toString() {
        return "Carta{" + "numCarta=" + numCarta + ", nombreCarta=" + nombreCarta + '}';
    }

}

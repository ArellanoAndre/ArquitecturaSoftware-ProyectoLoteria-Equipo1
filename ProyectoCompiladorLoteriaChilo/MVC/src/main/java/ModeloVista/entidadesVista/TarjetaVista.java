package ModeloVista.entidadesVista;

/**
 * Representa la versión visual de una tarjeta dentro del ModeloVista. Contiene
 * las casillas marcadas y la ruta de la imagen asociada.
 */
public class TarjetaVista {

    private boolean[] marcadas;  // Indica qué casillas fueron marcadas
    private String img;          // Ruta de la imagen de la tarjeta

    /**
     * Constructor que inicializa la tarjeta visual con sus casillas y su
     * imagen.
     *
     * @param casillas Arreglo de booleanos indicando casillas marcadas.
     * @param img Ruta de la imagen asociada a la tarjeta.
     */
    public TarjetaVista(boolean[] casillas, String img) {
        this.marcadas = casillas;
        this.img = img;
    }

    /**
     * @param marcadas Nuevo arreglo de casillas marcadas.
     */
    public void setMarcadas(boolean[] marcadas) {
        this.marcadas = marcadas;
    }

    /**
     * @return Arreglo indicando qué casillas están marcadas.
     */
    public boolean[] getMarcadas() {
        return marcadas;
    }

    /**
     * @return Ruta de la imagen asociada a la tarjeta.
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img Nueva ruta de imagen para la tarjeta.
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return Representación en texto de la tarjeta visual.
     */
    @Override
    public String toString() {
        return "Tarjeta{" + "marcadas= " + marcadas + '}';
    }
}

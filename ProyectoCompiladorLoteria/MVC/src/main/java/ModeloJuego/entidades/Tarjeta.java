package ModeloJuego.entidades;

/**
 * Representa una tarjeta del ModeloJuego con sus casillas, marcas y la imagen
 * asociada. Cada tarjeta contiene una cuadrícula de números (casillas) y un
 * arreglo de booleanos que indica qué posiciones han sido marcadas.
 */
public class Tarjeta {

    private int[] casillas;   // Números en la tarjeta (ej. 4x4)
    private boolean[] marcadas; // Indica si una casilla fue marcada
    private String img; // Ruta o nombre de la imagen asociada

    /**
     * Constructor que inicializa la tarjeta con sus casillas e imagen.
     *
     * @param casillas Arreglo con los números de la tarjeta.
     * @param img Ruta o nombre de la imagen de la tarjeta.
     */
    public Tarjeta(int[] casillas, String img) {
        this.casillas = casillas;
        this.marcadas = new boolean[casillas.length];
        this.img = img;
    }

    /**
     * Obtiene el número de una casilla específica.
     *
     * @param numCasilla Índice de la casilla.
     * @return Valor de la casilla.
     */
    public int getCasilla(int numCasilla) {
        return casillas[numCasilla];
    }

    /**
     * Marca una casilla como seleccionada.
     *
     * @param numCasilla Índice de la casilla a marcar.
     */
    public void marcarCasilla(int numCasilla) {
        marcadas[numCasilla] = true;
    }

    /**
     * @return Arreglo con las casillas marcadas.
     */
    public boolean[] getMarcadas() {
        return marcadas;
    }

    /**
     * @return Arreglo con los números de las casillas.
     */
    public int[] getCasillas() {
        return casillas;
    }

    /**
     * @return Imagen asociada a la tarjeta.
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img Nueva imagen para la tarjeta.
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @param marcadas Nuevo arreglo de casillas marcadas.
     */
    @Override
    public String toString() {
        return "Tarjeta{" + "casillas=" + casillas + ", marcadas=" + marcadas + '}';
    }

    /**
     * @return Representación en texto de la tarjeta.
     */
    public void setMarcadas(boolean[] marcadas) {
        this.marcadas = marcadas;
    }

}

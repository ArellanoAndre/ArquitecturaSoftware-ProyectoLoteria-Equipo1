package ModeloJuego.entidades;

/**
 * Representa una carta del ModeloJuego, con un número y un nombre asociado.
 */
public class Carta {

    private int numCarta;
    private String nombreCarta;

    /**
     * Constructor que inicializa una carta con su número y nombre.
     *
     * @param numCarta Número identificador de la carta.
     * @param nombreCarta Nombre de la carta.
     */
    public Carta(int numCarta, String nombreCarta) {
        this.numCarta = numCarta;
        this.nombreCarta = nombreCarta;
    }

    /**
     * @return Número de la carta.
     */
    public int getNumCarta() {
        return numCarta;
    }

    /**
     * @param numCarta Nuevo número de la carta.
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
     * @param nombreCarta Nuevo nombre de la carta.
     */
    public void setNombreCarta(String nombreCarta) {
        this.nombreCarta = nombreCarta;
    }

    /**
     * @return Representación en texto de la carta.
     */
    @Override
    public String toString() {
        return "Carta{" + "numCarta=" + numCarta + ", nombreCarta=" + nombreCarta + '}';
    }

}

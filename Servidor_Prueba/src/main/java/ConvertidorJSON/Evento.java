package ConvertidorJSON;

public class Evento {

    private EnumTipoEvento tipo;
    private int idJugador;
    private String cartaSeleccionada;
    private int posicion;

    // Constructor
    public Evento(EnumTipoEvento tipo, int idJugador, String cartaSeleccionada, int posicion) {
        this.tipo = tipo;
        this.idJugador = idJugador;
        this.cartaSeleccionada = cartaSeleccionada;
        this.posicion = posicion;
    }

    // Getters
    public EnumTipoEvento getTipo() {
        return tipo;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getCartaSeleccionada() {
        return cartaSeleccionada;
    }

    public int getPosicion() {
        return posicion;
    }
}

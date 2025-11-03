package Objetos;

public class Evento {

    private String tipo;
    private int idJugador;
    private String cartaSeleccionada;
    private int posicion;

    public Evento() {
    }

    public Evento(String tipo, int idJugador, String cartaSeleccionada, int posicion) {
        this.tipo = tipo;
        this.idJugador = idJugador;
        this.cartaSeleccionada = cartaSeleccionada;
        this.posicion = posicion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getCartaSeleccionada() {
        return cartaSeleccionada;
    }

    public void setCartaSeleccionada(String cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return "Evento{"
                + "tipo='" + tipo + '\''
                + ", idJugador=" + idJugador
                + ", cartaSeleccionada='" + cartaSeleccionada + '\''
                + ", posicion=" + posicion
                + '}';
    }
}

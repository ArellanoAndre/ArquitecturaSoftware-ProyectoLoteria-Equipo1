package Objetos;

public class Evento {

    private EnumTipoEvento tipo;
    private int idJugador;
    private String cartaSeleccionada;
    private int posicion;
    private String topico;

    public Evento() {
    }

    public Evento(EnumTipoEvento tipo, int idJugador, String cartaSeleccionada, int posicion, String topico) {
        this.tipo = tipo;
        this.idJugador = idJugador;
        this.cartaSeleccionada = cartaSeleccionada;
        this.posicion = posicion;
        this.topico = topico;
    }

    public EnumTipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoEvento tipo) {
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

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    @Override
    public String toString() {
        return "Evento{" + "tipo=" + tipo + ", idJugador=" + idJugador + ", cartaSeleccionada=" + cartaSeleccionada + ", posicion=" + posicion + ", topico=" + topico + '}';
    }

    
}

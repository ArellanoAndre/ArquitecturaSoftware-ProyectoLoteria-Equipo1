package Objetos;

public class Evento {

    private EnumTipoEvento tipo;
    private int idJugador;
    private int posicion;
    private String topico;

    public Evento() {
    }

    public Evento(EnumTipoEvento tipo, int idJugador, int posicion, String topico) {
        this.tipo = tipo;
        this.idJugador = idJugador;
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
        return "Evento{" + "tipo=" + tipo + ", idJugador=" + idJugador + ", posicion=" + posicion + ", topico=" + topico + '}';
    }

    
}

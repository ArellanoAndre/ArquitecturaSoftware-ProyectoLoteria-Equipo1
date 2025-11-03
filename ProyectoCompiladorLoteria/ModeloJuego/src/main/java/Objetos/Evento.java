package Objetos;


public class Evento {
    private String tipo;
    private int idJugador;
    private int posicion;

    public Evento() {}

    public Evento(String tipo, int idJugador, int posicion) {
        this.tipo = tipo;
        this.idJugador = idJugador;
        this.posicion = posicion;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getIdJugador() { return idJugador; }
    public void setIdJugador(int idJugador) { this.idJugador = idJugador; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    @Override
    public String toString() {
        return "Evento{" +
                "tipo='" + tipo + '\'' +
                ", idJugador=" + idJugador +
                ", posicion=" + posicion +
                '}';
    }
}

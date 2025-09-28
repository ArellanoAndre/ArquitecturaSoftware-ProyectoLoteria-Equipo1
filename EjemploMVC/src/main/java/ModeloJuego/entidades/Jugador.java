/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego.entidades;

/**
 *
 * @author rodri
 */
public class Jugador {

    private int numJugador;
    private String nombre;
    private double puntaje;
    private Tarjeta tarjeta;
    private boolean principal;

    public Jugador(String nombre, Tarjeta tarjeta, int numJugador) {
        this.nombre = nombre;
        this.tarjeta = tarjeta;
        this.puntaje = 0;
        this.principal = false;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void addPuntos(double puntos) {
        this.puntaje += puntos;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public int getNumJugador() {
        return numJugador;
    }

    public void setNumJugador(int numJugador) {
        this.numJugador = numJugador;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista.entidadesVista;

import ModeloJuego.*;

/**
 *
 * @author rodri
 */
public class JugadorVista {

    private int numJugador;
    private String nombre;
    private double puntaje;
    private TarjetaVista tarjeta;
    private boolean principal;
    private String rutaAvatar;

    public JugadorVista(String nombre, TarjetaVista tarjeta, int numJugador) {
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

    public TarjetaVista getTarjeta() {
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

    public String getRutaAvatar() {
        return rutaAvatar;
    }

    public void setRutaAvatar(String rutaAvatar) {
        this.rutaAvatar = rutaAvatar;
    }
    
    
    
    
}

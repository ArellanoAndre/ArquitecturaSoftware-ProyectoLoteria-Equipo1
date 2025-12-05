/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesEntidades;

/**
 *
 * @author rodri
 */
public interface IJugador {
    
    public String getNombre();

    public double getPuntaje();

    public void addPuntos(double puntos);

    public ITarjeta getTarjeta();

    public boolean isPrincipal();

    public void setPrincipal(boolean principal);

    public int getNumJugador();

    public void setNumJugador(int numJugador);
}

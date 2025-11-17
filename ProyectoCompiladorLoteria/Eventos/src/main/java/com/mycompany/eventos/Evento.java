/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.eventos;

/**
 *
 * @author isaac
 */

public class Evento {

   
    private int idJugador;
    private int posicion;
    private String topico;

    public Evento() {
    }

    public Evento(int idJugador, int posicion, String topico) {
        this.idJugador = idJugador;
        this.posicion = posicion;
        this.topico = topico;
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
        return "Evento{" + "idJugador=" + idJugador + ", posicion=" + posicion + ", topico=" + topico + '}';
    }

   
    
}


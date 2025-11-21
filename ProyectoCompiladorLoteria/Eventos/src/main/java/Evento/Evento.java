/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Evento;

import interfacesGlobales.IEvento;

/**
 *
 * @author isaac
 */
public class Evento implements IEvento {

    private String topico;
    private String evento;
    private String JSON;
    private String ipLocal;
    private String ipDestino;
    private int puertoLocal;
    private int puertoDestino;

    public Evento() {
    }

    @Override
    public String getTopico() {
        return topico;
    }

    @Override
    public void setTopico(String topico) {
        this.topico = topico;
    }

    @Override
    public String getEvento() {
        return evento;
    }

    @Override
    public void setEvento(String evento) {
        this.evento = evento;
    }

    @Override
    public String getJSON() {
        return JSON;
    }

    @Override
    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    @Override
    public String getIpLocal() {
        return ipLocal;
    }

    @Override
    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }

    @Override
    public String getIpDestino() {
        return ipDestino;
    }

    @Override
    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    @Override
    public int getPuertoLocal() {
        return puertoLocal;
    }

    @Override
    public void setPuertoLocal(int puertoLocal) {
        this.puertoLocal = puertoLocal;
    }

    @Override
    public int getPuertoDestino() {
        return puertoDestino;
    }

    @Override
    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    @Override
    public String toString() {
        return "Evento{" + "topico=" + topico + ", evento=" + evento + ", JSON=" + JSON + ", ipLocal=" + ipLocal + ", ipDestino=" + ipDestino + ", puertoLocal=" + puertoLocal + ", puertoDestino=" + puertoDestino + '}';
    }
}

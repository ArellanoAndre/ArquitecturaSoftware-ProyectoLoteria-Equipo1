/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Evento;

import interfacesGlobales.IEvento;
import java.net.InetAddress;

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

    public Evento() {
    }

    public Evento(String topico, String evento, String JSON, InetAddress ipLocal, String ipDestino) {
        this.topico = topico;
        this.evento = evento;
        this.JSON = JSON;
        this.ipLocal = ipLocal.getHostAddress();
        this.ipDestino = ipDestino;
    }

    public String getTopico() {
        return topico;
    }

    public void setTopico(String topico) {
        this.topico = topico;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }
    
    public String getIpLocal() {
        return ipLocal;
    }

    public void setIpLocal(String ipLocal) {
        this.ipLocal = ipLocal;
    }

    public String getIpDestino() {
        return ipDestino;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    @Override
    public String toString() {
        return "Evento{" + "topico=" + topico + ", evento=" + evento + ", JSON=" + JSON + ", ipLocal=" + ipLocal + ", ipDestino=" + ipDestino + '}';
    }

   
    
}


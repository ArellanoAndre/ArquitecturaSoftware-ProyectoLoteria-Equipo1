/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Evento;

/**
 *
 * @author rodri
 */
public class EventoRed {

    private String eventoJson;
    private String ipDestino;
    private int puertoDestino;

    public EventoRed() {
    }

    public EventoRed(String eventoJson, String ipDestino, int puertoDestino) {
        this.eventoJson = eventoJson;
        this.ipDestino = ipDestino;
        this.puertoDestino = puertoDestino;
    }

    public String getEvento() {
        return eventoJson;
    }

    
    public String getIpDestino() {
        return ipDestino;
    }

   
    public int getPuertoDestino() {
        return puertoDestino;
    }

    public void setEventoJson(String eventoJson) {
        this.eventoJson = eventoJson;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    public void setPuertoDestino(int puertoDestino) {
        this.puertoDestino = puertoDestino;
    }

    @Override
    public String toString() {
        return "EventoRed{" + "eventoJson=" + eventoJson + ", ipDestino=" + ipDestino + ", puertoDestino=" + puertoDestino + '}';
    }

}

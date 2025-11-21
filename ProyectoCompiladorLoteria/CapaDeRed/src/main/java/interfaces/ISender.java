/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import eventoRed.EventoRed;

/**
 * @author Arell
 * Interfaz ISender
 * Define el comportamiento básico de cualquier componente que envíe datos por red.
 */
public interface ISender {
    /**
     * Envía un mensaje JSON a través de la red.
     * 
     * @param eventoRed El envoltorio que contiene payload en json, ip y puerto destino.
     */
    void send(EventoRed eventoRed);
}

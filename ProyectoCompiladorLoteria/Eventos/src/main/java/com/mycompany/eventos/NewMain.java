/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.eventos;

import Evento.Evento;
import builder.EventBuilder;

/**
 *
 * @author rodri
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EventBuilder eb = new EventBuilder("localhost");
        
        Evento e = eb.crearEvento();
        
        System.out.println(e);
        
        
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package builder;

import Evento.Evento;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rodri
 */
public class EventBuilder {

    private String ipDestino;
    
    public EventBuilder(String ipDestino) {
        this.ipDestino = ipDestino;
    }
    
    public Evento crearEvento() {
        Evento e = new Evento();
        try {
            
            e.setIpLocal(InetAddress.getLocalHost().getHostAddress());
            e.setIpDestino(ipDestino);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(EventBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return e;
        }
    }
    
}

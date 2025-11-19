/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Helper.HelperJSON;
import colaGenerica.ColaDePrioridad;
import interfaces.IBroker;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import pruebas.Broker;
import pruebas.Suscripcion;
/**
 *
 * @author abrilislas
 */
public class ClientHandler implements Runnable{
    
    IBroker broker;
    Socket socket;

    public ClientHandler(Broker broker, Socket socket) {
        this.broker = broker;
        this.socket = socket;
    }

    
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}

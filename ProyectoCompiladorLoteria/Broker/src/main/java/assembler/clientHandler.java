/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Ensamblador.EnsambladorComunicacionEventos;
import Ensamblador.EnsambladorRed;
import Helper.HelperJSON;
import colaGenerica.ColaDePrioridad;
import interfaces.IBroker;
import java.io.IOException;
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
    EnsambladorRed ensambladorRed;
    EnsambladorComunicacionEventos ensambladorEventos;

    public ClientHandler(Broker broker, Socket socket) throws IOException {
        this.broker = broker;
        this.socket = socket;
        this.ensambladorRed = new EnsambladorRed(socket);
//        this.ensambladorEventos = new EnsambladorComunicacionEventos(manejadorSuperior);
    }

    
    
    @Override
    public void run() {
        
        
//        ensambladorRed.ensamblar(receptor);
        
    }
    
    
    
}

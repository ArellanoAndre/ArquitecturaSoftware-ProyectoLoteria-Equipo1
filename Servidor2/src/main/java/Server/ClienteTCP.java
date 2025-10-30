/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import java.io.IOException;
import java.net.Socket;

public class ClienteTCP {

    private static final String SERVER = "localhost";
    private static final int PUERTO = 4999;
    private Socket socket;

    public void conectar() throws IOException {
        socket = new Socket(SERVER, PUERTO);
        System.out.println("[ClienteTCP] 🔗 Conectado al servidor en " + SERVER + ":" + PUERTO);
    }

    public Socket getSocket() {
        return socket;
    }
}

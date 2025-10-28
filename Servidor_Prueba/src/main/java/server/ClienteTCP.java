/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author abrilislas
 */
public class ClienteTCP {
    
    private static final int PUERTO_SERVER = 4999; // Puerto de conexión
    private static final String SERVER = "localhost"; // Host
    
    public static void main(String[] args) throws IOException {
        
        //Socket de conexión por parte del cliente
        Socket socketConexion = new Socket(SERVER, PUERTO_SERVER);
        System.out.println("Conectado al servidor en " + SERVER + ":" + PUERTO_SERVER);
        
        
        // flujos de entrada y salida
        InputStream in = socketConexion.getInputStream();
        OutputStream out = socketConexion.getOutputStream();
        
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)); // lectura desde teclado
        byte[] buffer = new byte[1024];
        
        System.out.println("Escribe un mensaje (o 'exit' para terminar):");
        
        while (true) {
            System.out.print("> ");
            String mensaje = teclado.readLine();
            
            if (mensaje.equalsIgnoreCase("exit")) {
                break;
            }
            // envía mensaje al servidor
            out.write(mensaje.getBytes());
            
            // leer respuesta del servidor
            int bytesLeidos = in.read(buffer);
            if (bytesLeidos == -1) break;
            
            String respuesta = new String(buffer, 0, bytesLeidos);
            System.out.println("Respuesta del servidor: " + respuesta);
        }
        
        socketConexion.close(); // cierra la conexión
        System.out.println("Conexión finalizada.");
    }
}

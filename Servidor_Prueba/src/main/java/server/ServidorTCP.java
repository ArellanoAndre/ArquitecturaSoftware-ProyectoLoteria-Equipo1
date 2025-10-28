/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author abrilislas
 */
public class ServidorTCP {
    
    private static final int BUFSIZE = 32; //tamanio de la entrada del buffer
    private static String MENSAJE_SERVIDOR = "etamo activo, papi"; //mensaje del servidor en tiempo de ejecución 
    private static final int PUERTO_SERVER = 4999; //Puerto del servidor
    
        public static void main(String[] args) throws IOException{
        
            ServerSocket serverSocket= new ServerSocket(PUERTO_SERVER);
            System.out.println(MENSAJE_SERVIDOR);
            
            while (true){
                Socket clientSocket = serverSocket.accept(); //tomamos la conexión del cliente
                SocketAddress direccioncliente = 
                clientSocket.getRemoteSocketAddress(); //almacena la direccion del cliente (IP y puerto)
                
                System.out.println("Conectado al cliente, datos: " + direccioncliente);
                
                 InputStream in = clientSocket.getInputStream(); //lectura de datos
                 OutputStream out = clientSocket.getOutputStream(); //flujo de salida
                 
                 byte[] recibeBuf = new byte[BUFSIZE]; // Receive buffer
                 int bytesLeidos;

                 //recibe mensajes y hace eco
                 while ((bytesLeidos = in.read(recibeBuf)) != -1) {
                    out.write(recibeBuf, 0, bytesLeidos);
            }

            clientSocket.close(); // Cierra el socket del cliente    
            System.out.println("Cliente desconectado: " + direccioncliente);
            }
        }
}


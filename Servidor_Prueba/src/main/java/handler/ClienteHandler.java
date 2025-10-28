/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author abrilislas
 * Clase para el manejo del cliente
 */

public class ClienteHandler implements Runnable{
    
    //socket del cliente (canal de comunicación)
    private Socket clienteSocket;
    private int BUFSIZE;
    
    /**
     * Constructor 
     * @param socket 
     */
    public ClienteHandler(Socket socket, int BUFSIZE){
        this.clienteSocket=socket;
        this.BUFSIZE=BUFSIZE;
    }
    
    /**
     * Comunciacion con el cliente
     */
    @Override
    public void run() {
        //inicializacion de variables
        InputStream in = null; //variable que lee los datos que envia el cliente
        OutputStream out = null;//envio de datos
        byte[] buffer = new byte[BUFSIZE];
        int bytesLeidos;
        
        try{
            // flujos de entrada y salida
            in = clienteSocket.getInputStream(); //lee los datos que envia el cliente
            out = clienteSocket.getOutputStream(); //envía los datos al server
            
            /**
             * Dentro del while llenamos el buffer con los datos del cliente
             * mientras éste envíe datos
             */
            while ((bytesLeidos = in.read(buffer)) != -1) {
                String mensaje = new String(buffer, 0, bytesLeidos);
                System.out.println("Cliente: " + mensaje);
                
                String respuesta = "Servidor recibió: " + mensaje;
                out.write(respuesta.getBytes());
            }
        }catch(IOException e){
            System.getLogger(ClienteHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        }
        //primera vez q uso finally wtf
        finally{
            try{
                clienteSocket.close(); //intentamos cerrar el socket cuando sea -1
            }catch(IOException e){
                System.out.println("Error al cerrar socket: " + e.getMessage());
            }
            System.out.println("Cliente desconectado");
        }
        
    }
}

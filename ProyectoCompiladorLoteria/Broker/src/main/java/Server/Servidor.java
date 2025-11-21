/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import interfaces.IBroker;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import pruebas.LaSecuelaDelBroker;

/**
 * Servidor principal.
 * - Crea un ServerSocket.
 * - Crea un Broker único.
 * - Acepta conexiones de clientes y le crea en un hilo.
 */
public class Servidor {
    
    private static final int PUERTO = 5000;
    
    public static void main(String[] args) {
        System.out.println("[Servidor] Iniciando en puerto " + PUERTO + "...");

        // Broker único para todo el servidor
        IBroker broker = new LaSecuelaDelBroker();

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("[Servidor] Escuchando en puerto " + PUERTO);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("[Servidor] Nuevo cliente: " 
                        + cliente.getRemoteSocketAddress());
                
                // Crear y arrancar el hilo para este cliente
                Thread hiloCliente = new Thread();
                hiloCliente.start();
            }

        } catch (IOException e) {
            System.err.println("[Servidor] Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

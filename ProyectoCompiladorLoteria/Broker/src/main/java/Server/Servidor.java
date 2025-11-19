/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import assembler.ClientHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import pruebas.Broker;

/**
 * Servidor principal.
 * - Crea un ServerSocket.
 * - Crea un Broker único.
 * - Acepta conexiones de clientes.
 * - Crea un ClientHandler por cliente, lo guarda en una lista y lo lanza en un hilo.
 */
public class Servidor {
    
    private static final int PUERTO = 5000;

    // Lista global de clientes conectados (opcional pero muy útil)
    private static final List<ClientHandler> clientes = new CopyOnWriteArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("[Servidor] Iniciando en puerto " + PUERTO + "...");

        // Broker único para todo el servidor
        Broker broker = new Broker();

        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("[Servidor] Escuchando en puerto " + PUERTO);

            while (true) {
                // Espera bloqueante hasta que llegue un cliente
                Socket cliente = servidor.accept();

                System.out.println("[Servidor] Nuevo cliente: " 
                        + cliente.getRemoteSocketAddress());

                // Crear handler para este cliente, pasándole el socket y el broker
                ClientHandler handler = new ClientHandler(broker, cliente);

                // Guardar el handler en la lista de clientes conectados
                clientes.add(handler);

                // Crear y arrancar el hilo para este cliente
                Thread hiloCliente = new Thread(handler, "ClientHandler-" + cliente.getPort());
                hiloCliente.start();
            }

        } catch (IOException e) {
            System.err.println("[Servidor] Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

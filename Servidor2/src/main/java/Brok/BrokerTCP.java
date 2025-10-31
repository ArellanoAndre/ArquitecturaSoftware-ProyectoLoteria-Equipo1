/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Brok;

/**
 *
 * @author Arell
 */

import Red.ClienteHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;


public class BrokerTCP {
    private static final int PUERTO = 6000;
    private static final ConcurrentHashMap<String, ClienteHandler> clientes = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        System.out.println("[Broker] Escuchando en puerto " + PUERTO);
        try (ServerSocket server = new ServerSocket(PUERTO)) {
            while (true) {
                Socket socket = server.accept();
                String id = socket.getRemoteSocketAddress().toString();
                ClienteHandler handler = new ClienteHandler(socket, id);
                clientes.put(id, handler);
                new Thread(handler).start();
                System.out.println("[Broker] Nuevo cliente: " + id);
            }
        } catch (IOException e) {
            System.err.println("[Broker] Error: " + e.getMessage());
        }
    }

    public static void procesarMensaje(String id, String mensaje, ClienteHandler origen) {
        try {
            for (ClienteHandler ch : clientes.values()) {
                if (!ch.getId().equals(id)) {
                    ch.enviar(mensaje);
                }
            }
            System.out.println("[Broker] Mensaje reenviado: " + mensaje);
        } catch (Exception e) {
            System.err.println("[Broker] Error reenviando mensaje");
        }
    }
}

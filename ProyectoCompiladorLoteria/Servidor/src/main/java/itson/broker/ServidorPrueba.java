/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package itson.broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServidorPrueba {

    // Puerto donde escuchará el servidor
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        System.out.println("[Servidor] Iniciando servidor en puerto " + PUERTO);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {

            while (true) {
                // Aceptar conexión de un cliente
                Socket socket = serverSocket.accept();
                System.out.println("[Servidor] Cliente conectado: " + socket.getRemoteSocketAddress());

                // Preparar lector en UTF-8
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)
                );

                // Leer UNA línea enviada por Sender
                String mensaje = reader.readLine();  // ← Esto encaja con Writer.newLine() de tu Sender

                System.out.println("[Servidor] Mensaje recibido:");
                System.out.println("           " + mensaje);

                // Cerrar socket (importante)
                socket.close();
                System.out.println("[Servidor] Conexión cerrada.");
            }

        } catch (IOException e) {
            System.err.println("[Servidor] Error: " + e.getMessage());
        }
    }
}


package itson.broker;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.*;
import java.net.*;

public class broker {
    public static void main(String[] args) {
        int puerto = 5000;
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("[Broker] Servidor iniciado en puerto " + puerto);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("[Broker] Nuevo cliente: " + cliente.getInetAddress());
                new Thread(() -> manejarCliente(cliente)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manejarCliente(Socket cliente) {
        try (
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()))
        ) {
            salida.write("Bienvenido al broker\n");
            salida.flush();

            String mensaje;
            while ((mensaje = entrada.readLine()) != null) {
                System.out.println("[Broker] Recibido de cliente " + cliente.getInetAddress() + ": " + mensaje);
                salida.write("ECO: " + mensaje + "\n");
                salida.flush();
            }

        } catch (IOException e) {
            System.err.println("[Broker] Cliente desconectado: " + cliente.getInetAddress());
        }
    }
}


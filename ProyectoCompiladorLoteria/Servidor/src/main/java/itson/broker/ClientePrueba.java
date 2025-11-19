/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.broker;

/**
 *
 * @author Arell
 */
import java.io.*;
import java.net.*;

public class ClientePrueba {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 7000);
            System.out.println("[ClientePrueba] Conectado al servidor.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Mensaje de prueba (usa un JSON que tu servidor pueda leer)
            out.println("{\"topico\":\"juego.in\",\"evento\":\"PING\",\"JSON\":\"{}\",\"ipLocal\":\"127.0.0.1\",\"ipDestino\":\"127.0.0.1\"}");

            System.out.println("[ClientePrueba] Mensaje enviado.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String respuesta;

            while ((respuesta = in.readLine()) != null) {
                System.out.println("[ClientePrueba] Respuesta del servidor: " + respuesta);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

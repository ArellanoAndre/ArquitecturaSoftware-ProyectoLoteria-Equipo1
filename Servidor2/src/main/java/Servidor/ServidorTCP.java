/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import ConvertidorJSON.EnumTipoEvento;
import ConvertidorJSON.ToJSON;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServidorTCP {
    private static final String BROKER_IP = "192.168.1.26"; // IP del broker (máquina 1)
    private static final int BROKER_PORT = 6000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(BROKER_IP, BROKER_PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("[Servidor] Conectado al broker");

            String msg;
            while ((msg = in.readLine()) != null) {
                Evento evento = ToJSON.convertirDesdeJson(msg);
                System.out.println("[Servidor] Recibido: " + evento.getCartaSeleccionada());

                // Crear un nuevo evento de PINTAR_CARTA
                Evento respuesta = new Evento(
                        EnumTipoEvento.PINTAR_CARTA,
                        evento.getIdJugador(),
                        evento.getCartaSeleccionada(),
                        evento.getPosicion()
                );
                out.println(ToJSON.convertirAJson(respuesta));
            }
        } catch (IOException e) {
            System.err.println("[Servidor] Error: " + e.getMessage());
        }
    }
}

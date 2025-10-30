/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import Brok.Publicador;
import Brok.Suscriptor;
import Brok.Broker;
import ConvertidorJSON.Evento;
import ConvertidorJSON.EnumTipoEvento;
import ConvertidorJSON.ToJSON;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;


/**
 * Servidor que recibe eventos de clientes y los reenvía a través del broker.
 */
public class ServidorTCP {

    private static final int PUERTO_SERVER = 4999;
    private static final String MENSAJE_SERVIDOR = "Servidor activo y escuchando conexiones...";

    public static void main(String[] args) throws IOException {

        Broker broker = Broker.getInstancia();
        Publicador publicador = new Publicador();
        Suscriptor suscriptor = new Suscriptor();

        // 🔹 El servidor escucha el tópico de los clientes
        suscriptor.suscribir("topico-clientes", evento -> {
            System.out.println("[Servidor] 📥 Evento recibido de cliente " + evento.getIdJugador()
                    + ": " + evento.getCartaSeleccionada());

            // Crear nuevo evento tipo PINTAR_CARTA
            Evento respuesta = new Evento(
                    EnumTipoEvento.PINTAR_CARTA,
                    evento.getIdJugador(),
                    evento.getCartaSeleccionada(),
                    evento.getPosicion()
            );

            // Publicar hacia los clientes
            publicador.publicar("topico-servidor", respuesta);
            System.out.println("[Servidor] 📤 Evento reenviado a clientes: " + evento.getCartaSeleccionada());
        });

        // 🔹 Escuchar conexiones TCP (solo simulación)
        ServerSocket serverSocket = new ServerSocket(PUERTO_SERVER);
        System.out.println(MENSAJE_SERVIDOR);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            SocketAddress direccionCliente = clientSocket.getRemoteSocketAddress();
            System.out.println("Cliente conectado: " + direccionCliente);
        }
    }
}

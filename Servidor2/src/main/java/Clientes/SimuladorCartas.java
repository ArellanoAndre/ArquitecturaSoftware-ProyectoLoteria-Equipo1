/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clientes;

/**
 *
 * @author Arell
 */


import ConvertidorJSON.EnumTipoEvento;
import ConvertidorJSON.Evento;
import ConvertidorJSON.ToJSON;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;


public class SimuladorCartas extends JFrame {
    private static final String BROKER_IP = "192.168.1.67"; // IP del servidor central
    private static final int BROKER_PORT = 6000;

    private PrintWriter out;
    private BufferedReader in;
    private JButton[] cartasPropias = new JButton[3];
    private JButton[] cartasRival = new JButton[3];
    private String[] nombres = {"La Dama", "El Gallo", "El Catrín"};
    private int idJugador;

    public SimuladorCartas(int idJugador) {
        this.idJugador = idJugador;
        setTitle("Jugador " + idJugador);
        setLayout(new GridLayout(2, 3, 10, 10));
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inicializar();
        conectar();
    }

    private void inicializar() {
        for (int i = 0; i < 3; i++) {
            cartasPropias[i] = new JButton(nombres[i]);
            cartasRival[i] = new JButton(nombres[i]);
            cartasPropias[i].setBackground(Color.WHITE);
            cartasRival[i].setBackground(Color.LIGHT_GRAY);

            int pos = i;
            cartasPropias[i].addActionListener(e -> enviarEvento(pos));
            add(cartasPropias[i]);
        }
        for (JButton b : cartasRival) add(b);
    }

    private void conectar() {
        try {
            Socket socket = new Socket(BROKER_IP, BROKER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("[Cliente " + idJugador + "] Conectado al broker");

            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        Evento evento = ToJSON.convertirDesdeJson(msg);
                        if (evento.getTipo() == EnumTipoEvento.PINTAR_CARTA) {
                            SwingUtilities.invokeLater(() ->
                                    cartasRival[evento.getPosicion()].setBackground(Color.GREEN));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("[Cliente] Desconectado");
                }
            }).start();

        } catch (IOException e) {
            System.err.println("[Cliente] Error al conectar al broker");
        }
    }

    private void enviarEvento(int pos) {
        Evento e = new Evento(EnumTipoEvento.SELECCIONAR_CARTA, idJugador, nombres[pos], pos);
        out.println(ToJSON.convertirAJson(e));
        cartasPropias[pos].setBackground(Color.YELLOW);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimuladorCartas(1).setVisible(true));
    }
}

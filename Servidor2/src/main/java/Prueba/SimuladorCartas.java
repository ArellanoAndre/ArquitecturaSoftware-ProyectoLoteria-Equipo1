/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prueba;

/**
 *
 * @author Arell
 */


import Brok.Publicador;
import Brok.Suscriptor;
import ConvertidorJSON.EnumTipoEvento;
import ConvertidorJSON.Evento;
import Server.ClienteTCP;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 * Simula un cliente que selecciona cartas y recibe las del rival.
 */
public class SimuladorCartas extends JFrame {

    private ClienteTCP cliente;
    private Publicador publicador;
    private Suscriptor suscriptor;
    private int idJugador;

    private JButton btnDamaPropia, btnGalloPropia, btnCatrinPropia;
    private JButton btnDamaRival, btnGalloRival, btnCatrinRival;

    public SimuladorCartas(int idJugador) {
        this.idJugador = idJugador;
        setTitle("Jugador " + idJugador);
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 3, 10, 10));
        setLocationRelativeTo(null);

        inicializarInterfaz();
        inicializarConexion();
    }

    private void inicializarInterfaz() {
        btnDamaPropia = crearBoton("La Dama", e -> enviarCarta("La Dama", 1));
        btnGalloPropia = crearBoton("El Gallo", e -> enviarCarta("El Gallo", 2));
        btnCatrinPropia = crearBoton("El Catrín", e -> enviarCarta("El Catrín", 3));

        btnDamaRival = crearBotonRival("La Dama");
        btnGalloRival = crearBotonRival("El Gallo");
        btnCatrinRival = crearBotonRival("El Catrín");

        add(btnDamaPropia);
        add(btnGalloPropia);
        add(btnCatrinPropia);
        add(btnDamaRival);
        add(btnGalloRival);
        add(btnCatrinRival);
    }

    private JButton crearBoton(String texto, java.util.function.Consumer<ActionEvent> accion) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.LIGHT_GRAY);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.addActionListener(e -> accion.accept(e));
        return boton;
    }

    private JButton crearBotonRival(String texto) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.WHITE);
        boton.setEnabled(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return boton;
    }

    private void inicializarConexion() {
        try {
            cliente = new ClienteTCP();
            cliente.conectar();

            publicador = new Publicador();
            suscriptor = new Suscriptor();

            suscriptor.suscribir("topico-servidor", evento -> {
                if (evento.getTipo() == EnumTipoEvento.PINTAR_CARTA && evento.getIdJugador() != idJugador) {
                    SwingUtilities.invokeLater(() -> {
                        pintarCartaRival(evento.getCartaSeleccionada(), evento.getPosicion());
                    });
                    System.out.println("[Cliente " + idJugador + "] 🟩 Rival seleccionó: " + evento.getCartaSeleccionada());
                }
            });

            System.out.println("[Cliente " + idJugador + "] ✅ Conectado al servidor.");
            JOptionPane.showMessageDialog(this, "Conectado al Servidor Correctamente");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar: " + e.getMessage());
        }
    }

    private void enviarCarta(String carta, int posicion) {
        Evento evento = new Evento(EnumTipoEvento.SELECCIONAR_CARTA, idJugador, carta, posicion);
        publicador.publicar("topico-clientes", evento);
        pintarCartaPropia(posicion);
        System.out.println("[Cliente " + idJugador + "] 🟨 Carta seleccionada: " + carta);
    }

    private void pintarCartaRival(String carta, int posicion) {
        JButton boton = switch (posicion) {
            case 1 -> btnDamaRival;
            case 2 -> btnGalloRival;
            case 3 -> btnCatrinRival;
            default -> null;
        };
        if (boton != null) boton.setBackground(Color.GREEN);
    }

    private void pintarCartaPropia(int posicion) {
        JButton boton = switch (posicion) {
            case 1 -> btnDamaPropia;
            case 2 -> btnGalloPropia;
            case 3 -> btnCatrinPropia;
            default -> null;
        };
        if (boton != null) boton.setBackground(Color.YELLOW);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SimuladorCartas(1).setVisible(true));
        SwingUtilities.invokeLater(() -> new SimuladorCartas(2).setVisible(true));
    }
}


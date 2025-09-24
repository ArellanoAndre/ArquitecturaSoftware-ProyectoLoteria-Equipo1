/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

// ====== VISTA ======

import Controlador.Controlador;
import ModeloVista.ModeloVista;
import Observer.Observer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Presentacion extends JFrame implements Observer {
    private JLabel lblCarta;
    private JLabel lblMarcador;
    private ModeloVista modeloVista;

    //basicamente todo es cosas irrelevantes de jframe de aqui para que no se vea tan gacho
    // ============================================================================================================
    public Presentacion(ModeloVista modeloVista, Controlador controlador) {
        this.modeloVista = modeloVista;
        this.modeloVista.addObserver(this);

        setTitle("Ejemplo MVC");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar la ventana
        setLayout(new BorderLayout(10, 10));

        // Panel superior para mostrar carta y marcador
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(2, 1, 5, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblCarta = new JLabel("Carta cantada: ---", SwingConstants.CENTER);
        lblCarta.setFont(new Font("Arial", Font.BOLD, 24));
        lblMarcador = new JLabel("Marcador: 0", SwingConstants.CENTER);
        lblMarcador.setFont(new Font("Arial", Font.PLAIN, 18));

        panelInfo.add(lblCarta);
        panelInfo.add(lblMarcador);

        add(panelInfo, BorderLayout.NORTH);

        // Panel central para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton btnGallo = new JButton("El gallo");
        JButton btnDama = new JButton("La dama");
        JButton btnCatrin = new JButton("El diablito");

        // Colores opcionales para cada boton
        btnGallo.setBackground(new Color(255, 230, 200));
        btnDama.setBackground(new Color(200, 230, 255));
        btnCatrin.setBackground(new Color(255, 100, 100));

        btnGallo.setFont(new Font("Arial", Font.BOLD, 16));
        btnDama.setFont(new Font("Arial", Font.BOLD, 16));
        btnCatrin.setFont(new Font("Arial", Font.BOLD, 16));

        // Events listeners a los botones para que envie al controlador en forma de texto para validarlo
        btnGallo.addActionListener(e -> controlador.seleccionarCarta("El gallo"));
        btnDama.addActionListener(e -> controlador.seleccionarCarta("La dama"));
        btnCatrin.addActionListener(e -> controlador.seleccionarCarta("El diablito"));

        panelBotones.add(btnGallo);
        panelBotones.add(btnDama);
        panelBotones.add(btnCatrin);

        add(panelBotones, BorderLayout.CENTER);

        setVisible(true);
    }
    // =========================================================================================================================

    // Como solamente nos interesa actualizar la carta cantada o el marcador, el update() que implementa la presentacion solamente actualiza estos 2 (recordando que se ejecuta despues de ser notificado por el modeloVista)
    @Override
    public void update() {
        lblCarta.setText("Carta cantada: " + modeloVista.getCartaCantada());
        lblMarcador.setText("Marcador: " + modeloVista.getMarcador());
    }
}

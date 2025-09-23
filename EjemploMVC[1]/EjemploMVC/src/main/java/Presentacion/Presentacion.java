package Presentacion;

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
    private ModeloVista modeloVista;

    // Botones de las cartas
    private JButton btnGallo;
    private JButton btnDama;
    private JButton btnCatrin;
    private JButton btnLimpiar;

    // Guarda la última carta seleccionada por el jugador
    private String cartaSeleccionada;

    public Presentacion(ModeloVista modeloVista, Controlador controlador) {
        this.modeloVista = modeloVista;
        this.modeloVista.addObserver(this);

        setTitle("Ejemplo MVC");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior para mostrar carta
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(1, 1, 5, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblCarta = new JLabel("Carta cantada: ---", SwingConstants.CENTER);
        lblCarta.setFont(new Font("Arial", Font.BOLD, 24));

        panelInfo.add(lblCarta);
        add(panelInfo, BorderLayout.NORTH);

        // Panel central para los botones de cartas
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 3, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnGallo = new JButton("El gallo");
        btnDama = new JButton("La dama");
        btnCatrin = new JButton("El catrín");

        // Colores iniciales en gris
        resetearColores();

        btnGallo.setFont(new Font("Arial", Font.BOLD, 16));
        btnDama.setFont(new Font("Arial", Font.BOLD, 16));
        btnCatrin.setFont(new Font("Arial", Font.BOLD, 16));

        // Eventos → mandan la carta al controlador
        btnGallo.addActionListener(e -> {
            cartaSeleccionada = "El gallo";
            controlador.seleccionarCarta("El gallo");
        });
        btnDama.addActionListener(e -> {
            cartaSeleccionada = "La dama";
            controlador.seleccionarCarta("La dama");
        });
        btnCatrin.addActionListener(e -> {
            cartaSeleccionada = "El catrín";
            controlador.seleccionarCarta("El catrín");
        });

        panelBotones.add(btnGallo);
        panelBotones.add(btnDama);
        panelBotones.add(btnCatrin);

        add(panelBotones, BorderLayout.CENTER);

        // Panel inferior con el botón "Limpiar"
        JPanel panelOpciones = new JPanel();
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.addActionListener(e -> {
            cartaSeleccionada = null;
            resetearColores();
        });

        panelOpciones.add(btnLimpiar);
        add(panelOpciones, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Método para reiniciar los colores de los botones
    private void resetearColores() {
        if (btnGallo != null) btnGallo.setBackground(Color.LIGHT_GRAY);
        if (btnDama != null) btnDama.setBackground(Color.LIGHT_GRAY);
        if (btnCatrin != null) btnCatrin.setBackground(Color.LIGHT_GRAY);
    }

    // Observer → actualiza carta mostrada y pinta si hubo acierto
    @Override
    public void update() {
        lblCarta.setText("Carta cantada: " + modeloVista.getCartaCantada());

        // Si hay una carta seleccionada por el jugador, verificamos coincidencia
        if (cartaSeleccionada != null && cartaSeleccionada.equals(modeloVista.getCartaCantada())) {
            if (cartaSeleccionada.equals("El gallo")) {
                btnGallo.setBackground(Color.GREEN);
            } else if (cartaSeleccionada.equals("La dama")) {
                btnDama.setBackground(Color.GREEN);
            } else if (cartaSeleccionada.equals("El catrín")) {
                btnCatrin.setBackground(Color.GREEN);
            }
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import ModeloVista.ModeloVista;
import ModeloVista.entidadesVista.JugadorVista;
import Observer.Observer;
import Presentacion.utilidades.GridPanel;
import java.awt.*;
import static java.awt.Color.GREEN;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author isaac
 */
public class JPanelJugadorSecundario extends JPanel implements Observer {

    private JLabel lblNombre;
    private JLabel lblPuntaje;
    private JLabel lblAvatar;       // nuevo JLabel para el avatar
    private JPanel[] panelCasillasGrid; // arreglo donde guardamos las casillas
    private JugadorVista jugador;
    private ModeloVista modeloVista;

    public JPanelJugadorSecundario(JugadorVista jugador, ModeloVista modeloVista) {
        this.jugador = jugador;
        this.modeloVista = modeloVista;
        this.modeloVista.addObserver(this);

        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(255, 250, 242));

        // PANEL GRID
        GridPanel panelGrid = new GridPanel();
        this.panelCasillasGrid = panelGrid.getCasillas();
        add(panelGrid, BorderLayout.CENTER);

        // PANEL INFO (Nombre + Puntaje)
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        panelInfo.setBackground(new Color(255, 250, 242));
        lblNombre = new JLabel(jugador.getNombre());
        lblPuntaje = new JLabel("Puntaje: " + jugador.getPuntaje());
        panelInfo.add(lblNombre);
        panelInfo.add(lblPuntaje);

        // PANEL AVATAR
        lblAvatar = new JLabel();
        cargarAvatar(); // método para cargar la imagen

        // Panel contenedor para info + avatar
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(255, 250, 242));
        panelNorte.add(lblAvatar, BorderLayout.WEST);
        panelNorte.add(panelInfo, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
    }

    private void cargarAvatar() {
        String rutaAvatar = jugador.getRutaAvatar();
        if (rutaAvatar != null) {
            java.net.URL url = getClass().getResource(rutaAvatar);
            if (url != null) {
                ImageIcon icono = new ImageIcon(url);

                Image imagenEscalada = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(imagenEscalada));
            } else {
                System.out.println("No se encontró la imagen: " + rutaAvatar);
            }
        }
    }

    public void actualizar(JugadorVista jugador) {
        lblPuntaje.setText("Puntaje:" + jugador.getPuntaje());

        boolean[] marcadas = jugador.getTarjeta().getMarcadas();
        for (int i = 0; i < panelCasillasGrid.length; i++) {
            if (marcadas[i]) {
                panelCasillasGrid[i].setBackground(Color.GREEN);
            }
        }
    }

    @Override
    public void update() {
        actualizar(jugador);
    }

}

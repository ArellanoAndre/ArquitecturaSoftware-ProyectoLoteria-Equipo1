package Presentacion;

import ModeloVista.ModeloVistaJuego;
import ModeloVista.entidadesVista.JugadorVista;
import Interfaces.Observer;
import Presentacion.utilidades.GridPanel;
import java.awt.*;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel que representa a un jugador secundario en la interfaz del juego.
 * Implementa Observer para actualizar su información cuando el ModeloVista
 * notifica cambios (como puntaje o casillas marcadas).
 */
public class JPanelJugadorSecundario extends JPanel implements Observer {

    private JLabel lblNombre;
    private JLabel lblPuntaje;
    private JLabel lblAvatar;
    private JPanel[] panelCasillasGrid;

    private int numJugador;
    private ModeloVistaJuego modeloVista;

    public JPanelJugadorSecundario(JugadorVista jugador, ModeloVistaJuego modeloVista) {

        this.numJugador = jugador.getNumJugador();   // ← ahora solo guardamos el número
        this.modeloVista = modeloVista;
        this.modeloVista.addObserver(this);

        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(255, 250, 242));

        // GRID
        GridPanel panelGrid = new GridPanel();
        this.panelCasillasGrid = panelGrid.getCasillas();
        add(panelGrid, BorderLayout.CENTER);

        // PANEL INFO
        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        panelInfo.setBackground(new Color(255, 250, 242));

        lblNombre = new JLabel(jugador.getNombre());
        lblPuntaje = new JLabel("Puntaje: " + jugador.getPuntaje());
        panelInfo.add(lblNombre);
        panelInfo.add(lblPuntaje);

        // AVATAR
        lblAvatar = new JLabel();
        cargarAvatar(jugador.getRutaAvatar());

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(255, 250, 242));
        panelNorte.add(lblAvatar, BorderLayout.WEST);
        panelNorte.add(panelInfo, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
    }

    private JugadorVista getJugadorActual() {
        return modeloVista.getJugadorPorNum(numJugador);
    }

    private void cargarAvatar(String ruta) {
        if (ruta != null) {
            java.net.URL url = getClass().getResource(ruta);
            if (url != null) {
                ImageIcon icono = new ImageIcon(url);
                Image img = icono.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                lblAvatar.setIcon(new ImageIcon(img));
            } else {
                System.out.println("No se encontró la imagen: " + ruta);
            }
        }
    }

    public void actualizar(JugadorVista jugador) {
        lblNombre.setText(jugador.getNombre());
        lblPuntaje.setText("Puntaje: " + jugador.getPuntaje());

        boolean[] marcadas = jugador.getTarjeta().getMarcadas();
        for (int i = 0; i < panelCasillasGrid.length; i++) {
            panelCasillasGrid[i].setBackground(marcadas[i] ? Color.GREEN : Color.WHITE);
        }
    }

    @Override
    public void update() {
        JugadorVista actualizado = getJugadorActual();
        if (actualizado != null) {
            actualizar(actualizado);
        }
    }
}

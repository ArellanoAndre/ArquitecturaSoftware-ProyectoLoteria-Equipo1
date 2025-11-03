package Presentacion;

import ModeloVista.ModeloVista;
import ModeloVista.entidadesVista.JugadorVista;
import Observer.Observer;
import Presentacion.utilidades.GridPanel;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel que representa a un jugador secundario en la interfaz del juego.
 * Implementa Observer para actualizar su información cuando el ModeloVista
 * notifica cambios (como puntaje o casillas marcadas).
 */
public class JPanelJugadorSecundario extends JPanel implements Observer {

    private JLabel lblNombre;               // Etiqueta con el nombre del jugador
    private JLabel lblPuntaje;              // Etiqueta con el puntaje del jugador
    private JLabel lblAvatar;               // Etiqueta con el avatar del jugador
    private JPanel[] panelCasillasGrid;     // Arreglo de paneles que representan las casillas de la tarjeta
    private JugadorVista jugador;           // Jugador asociado a este panel
    private ModeloVista modeloVista;        // ModeloVista del juego

    /**
     * Constructor que inicializa el panel del jugador secundario y lo registra
     * como observador.
     *
     * @param jugador JugadorVista del jugador secundario.
     * @param modeloVista ModeloVista del juego.
     */
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
        cargarAvatar();

        // Panel contenedor para info + avatar
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(new Color(255, 250, 242));
        panelNorte.add(lblAvatar, BorderLayout.WEST);
        panelNorte.add(panelInfo, BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
    }

    /**
     * Carga y escala la imagen del avatar del jugador secundario.
     */
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

    /**
     * Actualiza visualmente la información del jugador secundario, incluyendo
     * el puntaje y las casillas marcadas de su tarjeta.
     *
     * @param jugador JugadorVista con la información actualizada.
     */
    public void actualizar(JugadorVista jugador) {
        lblPuntaje.setText("Puntaje: " + jugador.getPuntaje());

        boolean[] marcadas = jugador.getTarjeta().getMarcadas();
        for (int i = 0; i < panelCasillasGrid.length; i++) {
            if (marcadas[i]) {
                panelCasillasGrid[i].setBackground(Color.GREEN);
            }
        }
    }

    /**
     * Método llamado cuando el ModeloVista notifica un cambio. Actualiza el
     * panel con los datos del jugador asociado.
     */
    @Override
    public void update() {
        actualizar(jugador);
    }
}

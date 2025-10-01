package Presentacion;

import Controlador.ControlSeleccionarCarta;
import ModeloVista.ModeloVista;
import ModeloVista.entidadesVista.JugadorVista;
import Observer.Observer;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class JPanelTarjeta extends javax.swing.JPanel implements Observer {

    private ModeloVista modeloVista;

    /**
     * Creates new form JPanelTarjeta
     */
    public JPanelTarjeta(ModeloVista modeloVista) {
        initComponents();
        this.modeloVista = modeloVista;
        this.modeloVista.addObserver(this);
        inicializarTableroConMatriz();
    }

    // --- clase interna para mostrar la imagen escalada ---
    private static class ScaledImageComponent extends javax.swing.JComponent {

        private final java.awt.Image image;

        public ScaledImageComponent(java.awt.Image img) {
            this.image = img;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                int w = getWidth();
                int h = getHeight();
                g.drawImage(image, 0, 0, w, h, this);
            }
        }
    }

    private javax.swing.JLayeredPane layeredTablero;
    private javax.swing.JButton[][] botonesMatriz; // opcional para acceder luego

    private void inicializarTableroConMatriz() {
        // Cargar la imagen de fondo una vez al iniciar
        java.net.URL url = getClass().getResource("/img/Tableros/Tablero01.png");
        if (url == null) {
            System.err.println("No se encontró la imagen /img/Tableros/Tablero01.png");
            return;
        }
        java.awt.Image img = new javax.swing.ImageIcon(url).getImage();
        ScaledImageComponent fondo = new ScaledImageComponent(img);

        // Panel con GridLayout 4x4 para los botones
        JPanel panelMatriz = new JPanel(new java.awt.GridLayout(4, 4));
        panelMatriz.setOpaque(false);

        botonesMatriz = new javax.swing.JButton[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                final int rf = r, cf = c;
                JButton btn = new JButton();
                // Apariencia transparente, sin imágenes
                btn.setRolloverEnabled(false);

                btn.setIcon(null);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
                btn.setOpaque(false);

                // Acción al hacer clic usando el controlador del constructor
                btn.addActionListener(e -> {
                    int posicion = (rf * 4) + cf + 1;
                    ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);
                    controlador.seleccionarCarta(posicion);
                    System.out.println("Carta seleccionada: " + posicion);
                });

                botonesMatriz[r][c] = btn;
                panelMatriz.add(btn);
            }
        }

        // Configurar JLayeredPane
        layeredTablero = new JLayeredPane();
        layeredTablero.setOpaque(false);

        // Limpiar y añadir al panel
        panelTableroImagen.removeAll();
        panelTableroImagen.setLayout(new BorderLayout());
        panelTableroImagen.add(layeredTablero, BorderLayout.CENTER);

        // Añadir componentes al layeredPane (fondo estático)
        layeredTablero.add(fondo, JLayeredPane.DEFAULT_LAYER);
        layeredTablero.add(panelMatriz, JLayeredPane.PALETTE_LAYER);

        // Ajuste de tamaño solo para mantener proporciones, sin repaint
        panelTableroImagen.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = panelTableroImagen.getWidth();
                int h = panelTableroImagen.getHeight();
                layeredTablero.setBounds(0, 0, w, h);
                fondo.setBounds(0, 0, w, h);
                panelMatriz.setBounds(0, 0, w, h);
            }
        });

        java.awt.EventQueue.invokeLater(() -> {

            int w = panelTableroImagen.getWidth();
            int h = panelTableroImagen.getHeight();
            if (w > 0 && h > 0) {
                layeredTablero.setBounds(0, 0, w, h);
                fondo.setBounds(0, 0, w, h);
                panelMatriz.setBounds(0, 0, w, h);
            }
        }
        );
    }

    public void actualizarCartasCorrectas(boolean[] cartasCorrectas) {
        if (cartasCorrectas == null || cartasCorrectas.length != 16) {
            return;
        }
        for (int posicion = 0; posicion < 16; posicion++) {
            int fila = posicion / 4;
            int columna = posicion % 4;
            JButton btn = botonesMatriz[fila][columna];
            if (btn != null) {
                if (cartasCorrectas[posicion]) {
                    if (btn.getComponentCount() == 0) {
                        JPanel colorPanel = new JPanel() {
                            @Override
                            protected void paintComponent(java.awt.Graphics g) {
                                super.paintComponent(g);
                                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                                int margen = 4;
                                int arco = Math.min(getWidth(), getHeight()) - 8; // hace que el recuadro a rellenar se haga mas circular

                                // Relleno difuminado verde semitransparente
                                g2.setColor(new java.awt.Color(0, 200, 0, 120));
                                g2.fillRoundRect(margen, margen, getWidth() - margen * 2, getHeight() - margen * 2, arco, arco);

                                // Borde verde mas oscuro
                                g2.setColor(new java.awt.Color(0, 70, 0, 200));
                                g2.setStroke(new java.awt.BasicStroke(2));
                                g2.drawRoundRect(margen, margen, getWidth() - margen * 2, getHeight() - margen * 2, arco, arco);

                                g2.dispose();
                            }
                        };
                        colorPanel.setOpaque(false);
                        btn.setLayout(new java.awt.BorderLayout());
                        btn.add(colorPanel, BorderLayout.CENTER);
                    } else if (!btn.getComponent(0).isVisible()) {
                        btn.getComponent(0).setVisible(true);
                    }
                } else {
                    if (btn.getComponentCount() > 0 && btn.getComponent(0).isVisible()) {
                        btn.getComponent(0).setVisible(false);
                    }
                }
            }
        }
    }

    private void procesarCasillasMarcadas() {
        JugadorVista jugadorPrincipal = modeloVista.getJugadorPrincipal();
        if (jugadorPrincipal != null) {
            boolean[] casillasMarcadas = jugadorPrincipal.getTarjeta().getMarcadas();
            actualizarCartasCorrectas(casillasMarcadas);
        }
    }

    @Override
    public void update() {
        // Procesar las casillas marcadas del jugador
        procesarCasillasMarcadas();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTableroImagen = new javax.swing.JPanel();

        javax.swing.GroupLayout panelTableroImagenLayout = new javax.swing.GroupLayout(panelTableroImagen);
        panelTableroImagen.setLayout(panelTableroImagenLayout);
        panelTableroImagenLayout.setHorizontalGroup(
            panelTableroImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        panelTableroImagenLayout.setVerticalGroup(
            panelTableroImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 525, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTableroImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTableroImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelTableroImagen;
    // End of variables declaration//GEN-END:variables
}

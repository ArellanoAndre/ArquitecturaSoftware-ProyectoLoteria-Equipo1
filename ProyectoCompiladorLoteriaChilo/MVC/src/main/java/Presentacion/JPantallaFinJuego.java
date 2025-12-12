/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Presentacion;

import Controlador.ControlSeleccionarCarta;
import ModeloVista.entidadesVista.JugadorVista;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author isaac
 */
public class JPantallaFinJuego extends javax.swing.JDialog {

    private JPanel panelContenidoRanking;
    private ControlSeleccionarCarta controlador;
    /**
     * Creates new form JPantallaFinJuegoy
     */
    public JPantallaFinJuego(java.awt.Frame parent, boolean modal, ControlSeleccionarCarta controlador) {
        super(parent, modal);
        initComponents(); // NetBeans crea el JScrollPane (pnlRanking)
        this.setLocationRelativeTo(null);

        this.controlador = controlador;
        // Creamos el panel para contenido rankign 
        panelContenidoRanking = new JPanel();
        panelContenidoRanking.setBackground(Color.WHITE); 
        panelContenidoRanking.setLayout(new BoxLayout(panelContenidoRanking, BoxLayout.Y_AXIS));

        // se mete al scrollpane
        pnlRanking.setViewportView(panelContenidoRanking);

    }

    private JPanel crearFilaJugador(int top, JugadorVista jugador) {

        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.setMaximumSize(new Dimension(350, 60));

        JLabel lblNombre = new JLabel();
        String texto = String.format("Top %d: %s (%s pts)", top, jugador.getNombre(), jugador.getPuntaje());
        lblNombre.setText(texto);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblNombre.setForeground(Color.BLACK);
        // Reservamos espacio fijo para el texto para que la imagen siempre quede a la derecha
        lblNombre.setPreferredSize(new Dimension(220, 50));

        // 3. Configuración de la Imagen (Avatar)
        JLabel lblAvatar = new JLabel();
        lblAvatar.setPreferredSize(new Dimension(40, 40)); // Tamaño fijo del contenedor de imagen
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);

        // ==== LÓGICA DE CARGA DE IMAGEN =====
        try {
            // El controlador setea
            String rutaImagen = jugador.getRutaAvatar();

            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                // buscamos recurso
                java.net.URL imgURL = getClass().getResource(rutaImagen);

                if (imgURL != null) {
                    // cargamos la imagen original
                    ImageIcon iconOriginal = new ImageIcon(imgURL);

                    // redimension a 40x40 pixeles
                    Image imgEscalada = iconOriginal.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

                    // asignamos al Label
                    lblAvatar.setIcon(new ImageIcon(imgEscalada));
                } else {
                    // Si la ruta no existe ponemos texto de error
                    System.err.println("No se encontró la imagen en: " + rutaImagen);
                    lblAvatar.setText("?");
                    lblAvatar.setForeground(Color.RED);
                }
            } else {
                lblAvatar.setText("-"); // sin ruta asignada
            }
        } catch (Exception e) {
            System.err.println("Error al cargar avatar para " + jugador.getNombre() + ": " + e.getMessage());
            lblAvatar.setText("Err");
        }

        //   Agregar componentes al panel fila
        panel.add(lblNombre);
        panel.add(lblAvatar);

        return panel;
    }

    public void mostrarRanking(List<JugadorVista> ranking) {
        if (ranking != null && !ranking.isEmpty()) {
            JugadorVista ganador = ranking.get(0);
            lblGanador.setText(ganador.getNombre().toUpperCase() + " HA GANADO LA PARTIDA!");
        }

        //  Limpiar el panel por si acaso
        panelContenidoRanking.removeAll();

        // generar dinamicamente las filas del Top
        for (int i = 0; i < ranking.size(); i++) {
            JugadorVista jugador = ranking.get(i);
            int posicion = i + 1; 

            // Crear un panel para la fila
            JPanel fila = crearFilaJugador(posicion, jugador);

            panelContenidoRanking.add(fila);
            panelContenidoRanking.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        //   Refrescar
        panelContenidoRanking.revalidate();
        panelContenidoRanking.repaint();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitulo = new javax.swing.JLabel();
        lblGanador = new javax.swing.JLabel();
        pnlRanking = new javax.swing.JScrollPane();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        labelTitulo.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        labelTitulo.setText("FIN DE LA PARTIDA");

        lblGanador.setText("EL GANADOR DE LA PARTIDA ES:");

        btnAceptar.setBackground(new java.awt.Color(102, 255, 255));
        btnAceptar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pnlRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelTitulo)
                        .addGap(54, 54, 54))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(lblGanador))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(btnAceptar)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo)
                .addGap(8, 8, 8)
                .addComponent(lblGanador)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAceptar)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        
        this.dispose();
//        controlador.cambiarMVC();
    }//GEN-LAST:event_btnAceptarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel lblGanador;
    private javax.swing.JScrollPane pnlRanking;
    // End of variables declaration//GEN-END:variables
}

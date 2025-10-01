/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion;

import ModeloVista.entidadesVista.JugadorVista;
import Observer.Observer;
import Presentacion.utilidades.GridPanel;
import java.awt.*;
import static java.awt.Color.GREEN;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author isaac
 */
public class JPanelJugadorSecundario extends JPanel  {

    private JLabel lblNombre;
    private JLabel lblPuntaje;
    private JPanel[] panelCasillasGrid; // arreglo donde guardamos las casillas

    public JPanelJugadorSecundario(JugadorVista jugador) {
        setLayout(new BorderLayout(5, 5));
        // crear instancia de la clase para construir el grid
        GridPanel panelGrid = new GridPanel();
        setBackground(new Color(255,250,242));
        this.panelCasillasGrid = panelGrid.getCasillas(); // obtenemos las casillas de GridPanel
        add(panelGrid, BorderLayout.CENTER); // agregamos el panel al centro

        JPanel panelInfo = new JPanel(new GridLayout(2, 1));
        panelInfo.setBackground(new Color(255,250,242));
        lblNombre = new JLabel(jugador.getNombre());
        lblPuntaje = new JLabel(); // por mientras todavia no se actauliza
        panelInfo.add(lblNombre);
        panelInfo.add(lblPuntaje);
        add(panelInfo, BorderLayout.NORTH);// agregamos el panel arriba

        actualizar(jugador);
    }

    public void actualizar(JugadorVista jugador) {
        lblPuntaje.setText("Puntaje:" + jugador.getPuntaje());

        boolean[] marcadas = jugador.getTarjeta().getMarcadas();
        for (int i = 0; i < panelCasillasGrid.length; i++) {
            if (marcadas[i]) {
                panelCasillasGrid[i].setBackground(GREEN);
            } else {

            }
        }
    }

}

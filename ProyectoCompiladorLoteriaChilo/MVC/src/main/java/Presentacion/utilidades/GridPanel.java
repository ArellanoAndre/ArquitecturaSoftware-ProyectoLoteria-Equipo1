/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Presentacion.utilidades;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author isaac
 */
public class GridPanel extends JPanel {

    private final JPanel[] casillas; // guarda las 16 casillas

    public GridPanel() {
        // 1. Configurar este panel (que es el contenedor)
        super(new GridLayout(4, 4, 2, 2)); // Llama al constructor de JPanel con el layout
        setBackground(Color.DARK_GRAY);

        // 2. Inicializar el arreglo
        this.casillas = new JPanel[16];

        // 3. Crear y añadir las casillas a este mismo panel
        for (int i = 0; i < 16; i++) {
            JPanel casilla = new JPanel();
            casilla.setBackground(Color.LIGHT_GRAY);

            this.add(casilla); // Se añade directamente a este GridPanel
            this.casillas[i] = casilla; // Se guarda la referencia
        }
    }

    /**
     * Un método público para que otras clases puedan obtener las casillas y
     * pintarlas.
     */
    public JPanel[] getCasillas() {
        return this.casillas;
    }

}

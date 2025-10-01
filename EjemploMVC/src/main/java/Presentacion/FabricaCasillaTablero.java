package Presentacion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author abrilislas
 */
public class FabricaCasillaTablero {
    
    /**
     * VARIABLES FINALES DE LAS PROPORCIONES DE LAS CASILLAS
     */
    private final int WIDTH_CASILLA = 88; // Ancho de la casilla (px)
    private final int HEIGHT_CASILLA = 126; // Tam. de la casilla (px)
    
    /**
     * ELEMENTOS A UTILIZAR EN EL LAYER 
     */
    private JLabel fondo; //IMAGEN DE LA CARTA (CAPA 00)
    private JButton boton; //BOTON INVISIBLE SOBRE LA CARTA (CAPA 01)
    private JPanel overlay; //SUPERPOSICION VERDE (CAPA 0)

    
    /**
     * Constructor de la clase, crea cada una de las casillas y la agrega al panel
     * @param arregloTarjeta
     * @param panelTarjeta donde se desean agregar las casillas
     * @param listenerClickCasilla Action listener para la logica del click
     * @throws FileNotFoundException 
     */
    public FabricaCasillaTablero(int[] arregloTarjeta, JPanel panelTarjeta, ActionListener listenerClickCasilla) throws FileNotFoundException {
  
        for (int numeroCarta : arregloTarjeta) {
            try {
                JLayeredPane casilla = constructorCasilla(numeroCarta, listenerClickCasilla);
                panelTarjeta.add(casilla);
            } catch (FileNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
    /**
     * 
     * @param i numero que representa la carta de la casilla a generar 
     * @return
     * @throws FileNotFoundException 
     */
    private JLayeredPane constructorCasilla(int i, ActionListener listenerClickCasilla) throws FileNotFoundException  {
        
     //Construimos nuestro layered panel para la casilla
        JLayeredPane casilla = new JLayeredPane();
        casilla.setPreferredSize(new Dimension(WIDTH_CASILLA, HEIGHT_CASILLA));
        casilla.setLayout(null);
        
     //Capa 00: crear imagen 
        URL rutaImagenCarta = getClass().getResource("/img/Cartas/"+i+".jpeg"); //se obtiene ruta de la imagen de la carta correspondiente
        if(rutaImagenCarta==null){
            throw new FileNotFoundException("no se encontro la imagen");
        }
            
        ImageIcon imagenOriginal = new ImageIcon(rutaImagenCarta);
        Image escalada = imagenOriginal.getImage().getScaledInstance(WIDTH_CASILLA, HEIGHT_CASILLA, Image.SCALE_SMOOTH);
        ImageIcon cartaEscalada = new ImageIcon(escalada);
            
     // Agregamos la imagen a un label
        JLabel fondo = new JLabel(cartaEscalada);
        fondo.setBounds(0, 0, WIDTH_CASILLA, HEIGHT_CASILLA);
        casilla.add(fondo, JLayeredPane.DEFAULT_LAYER); // agregamos la capa al layeredPanel
            
     //Capa 01: Boton transparente
     
        JButton boton = new JButton();
        boton.setBounds(0, 0, WIDTH_CASILLA, HEIGHT_CASILLA);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false); //cambios 
        boton.setBorderPainted(false);     //esteticos
        boton.setFocusPainted(true);
        boton.addActionListener(listenerClickCasilla); // delegamos la lógica al ActionListener
        casilla.add(boton, JLayeredPane.PALETTE_LAYER); // agregamos la capa al layeredPanel
        
     //Se guardan los datos en el componente, como una clave valor
        boton.putClientProperty("numCarta", i); //se puede acceder a el por medio de un getClientProperty()
        
     //Capa 02: Overlay verde (default, oculto)
        JPanel overlay = new JPanel();
        overlay.setBounds(0, 0, WIDTH_CASILLA, HEIGHT_CASILLA);
        overlay.setBackground(new Color(156, 255, 156, 100)); // verde transparente
        overlay.setVisible(false); // solo se mostrara cuando se valide la cartaz
        casilla.add(overlay, JLayeredPane.MODAL_LAYER); // agregamos la capa al layeredPanel

     //Guardar referencia al overlay en el botón para manipularlo desde fuera como una clave valor
      boton.putClientProperty("overlay", overlay);
    //agregamos el action listener al boton  
     boton.addActionListener(listenerClickCasilla);

        return casilla;
        }
    /**
     * idea para uso a futuro para validar jugadas
     */
    //public List<JLayeredPane> getCasillas() {
    //return casillas;
}


import Controlador.ControlSeleccionarCarta;
import ModeloJuego.ModeloJuego;
import ModeloVista.ControlVista;
import ModeloVista.ModeloVista;
import Presentacion.JPantallaJuego;
import Presentacion.Presentacion;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.FlowLayout;

// ====== MAIN ======
public class main {

    public static void main(String[] args) {
        // Ejecutar jframe y creacion de objetos
        SwingUtilities.invokeLater(() -> {
            ModeloVista modeloVista = new ModeloVista();
            ControlVista controlVista = new ControlVista(modeloVista);
            ModeloJuego modeloJuego = new ModeloJuego(modeloVista, controlVista);
            modeloVista.setModeloJuego(modeloJuego);
            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);
            new JPantallaJuego(modeloVista, controlador).setVisible(true);

            // Iniciar estado del juego
            modeloJuego.iniciarJuego();
        });
    }
}

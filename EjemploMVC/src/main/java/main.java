import Controlador.Controlador;
import ModeloJuego.ModeloJuego;
import ModeloVista.ModeloVista;
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
            ModeloJuego modeloJuego = new ModeloJuego(modeloVista);
            modeloVista.setModeloJuego(modeloJuego);
            Controlador controlador = new Controlador(modeloVista, modeloJuego);
            new Presentacion(modeloVista, controlador);

            // Iniciar estado del juego
            modeloJuego.iniciarJuego();
        });
    }
}

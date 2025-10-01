package Controlador;

// ====== CONTROLADOR ======
import ModeloJuego.ModeloJuego;
import ModeloVista.ModeloVista;
import javax.swing.JButton;

public class Controlador {

    private ModeloVista modeloVista;
    private ModeloJuego modeloJuego;

    public Controlador(ModeloVista modeloVista, ModeloJuego modeloJuego) {
        this.modeloVista = modeloVista;
        this.modeloJuego = modeloJuego;
    }

    // Envia desde la PRESENTACION al modeloVista la carta seleccionada para despues ser validada
    public void seleccionarCarta(int pos,JButton boton) {
        modeloVista.seleccionarCarta(pos);
    }
}

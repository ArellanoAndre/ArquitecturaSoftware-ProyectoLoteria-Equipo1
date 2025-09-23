package Controlador;

// ====== CONTROLADOR ======
import ModeloJuego.ModeloJuego;
import ModeloVista.ModeloVista;

public class Controlador {

    private ModeloVista modeloVista;
    private ModeloJuego modeloJuego;

    public Controlador(ModeloVista modeloVista, ModeloJuego modeloJuego) {
        this.modeloVista = modeloVista;
        this.modeloJuego = modeloJuego;
    }

    // Envia desde la PRESENTACION al modeloVista la carta seleccionada para despues ser validada
    public void seleccionarCarta(String carta) {
        modeloVista.seleccionarCarta(carta);
    }
}

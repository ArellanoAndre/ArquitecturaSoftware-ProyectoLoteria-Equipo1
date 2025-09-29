package Controlador;

// ====== CONTROLADOR ======
import ModeloVista.ModeloVista;

public class ControlSeleccionarCarta {

    private final ModeloVista modeloVista;


    public ControlSeleccionarCarta(ModeloVista modeloVista) {
        this.modeloVista = modeloVista;
    }

    // Envia desde la PRESENTACION al modeloVista la carta seleccionada para despues ser validada
    public void seleccionarCarta(int pos) {
        modeloVista.seleccionarCarta(pos);
    }
    
    
}

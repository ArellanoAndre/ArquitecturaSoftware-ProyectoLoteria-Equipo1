package Controlador;

// ====== CONTROLADOR ======
import Observer.IModeloVista;

public class ControlSeleccionarCarta {

    private final IModeloVista modeloVista;


    public ControlSeleccionarCarta(IModeloVista modeloVista) {
        this.modeloVista = modeloVista;
    }

    // Envia desde la PRESENTACION al modeloVista la carta seleccionada para despues ser validada
    public void seleccionarCarta(int pos) {
        modeloVista.seleccionarCarta(pos);
    }
    
    
}

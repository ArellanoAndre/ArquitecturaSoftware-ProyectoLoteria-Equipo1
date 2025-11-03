package Controlador;

import Observer.IModeloVista;

/**
 * Controlador que se encarga de manejar la interaccion entre la presentacion y
 * el modeloVista del juego.
 */
public class ControlSeleccionarCarta {

    /**
     * Referencia al modeloVista para comunicar las interacciones
     */
    private final IModeloVista modeloVista;

    /**
     * Constructor que inicializa el controlador con el modeloVista.
     *
     * @param modeloVista interfaz que conecta el modelo y la vista.
     */
    public ControlSeleccionarCarta(IModeloVista modeloVista) {
        this.modeloVista = modeloVista;
    }

    /**
     * Envía al modelo la posicion de la carta seleccionada.
     *
     * @param pos posicion de la carta seleccionada.
     */
    public void seleccionarCarta(int pos) {
        modeloVista.seleccionarCarta(pos);
    }

}

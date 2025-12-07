/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;


import interfacesComunicacionModelo.IModeloJuego;
import interfacesEntidades.IJugador;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public class ProcesadorCasillaSeleccionada implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {
        if ("CASILLA_SELECCIONADA".equals(tipoEvento)) {
            modeloJuego.actualizarCasillaSeleccionada(datos);
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        } else {
            System.out.println("[Chain] Evento desconocido: " + tipoEvento);
        }
    }

}

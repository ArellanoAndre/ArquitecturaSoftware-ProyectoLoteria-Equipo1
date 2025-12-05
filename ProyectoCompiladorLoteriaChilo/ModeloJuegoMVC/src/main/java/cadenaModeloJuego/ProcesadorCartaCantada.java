/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import ModeloJuegoEntidades.Carta;
import interfacesComunicacionModelo.IControlVista;
import modeloJuegoMVC.ModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public class ProcesadorCartaCantada implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IControlVista controlVista, ModeloJuego modeloJuego) {
        if ("CARTA_CANTADA".equals(tipoEvento)) {
            int numCarta = datos.getInt("IdCarta");
            String nombreCarta = datos.getString("Nombre");

            Carta cartaActual = new Carta(numCarta, nombreCarta);
            controlVista.actualizarCartaCantada(cartaActual);
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, controlVista, modeloJuego);
        } else {
            System.out.println("[Chain] Evento desconocido: " + tipoEvento);
        }
    }

}

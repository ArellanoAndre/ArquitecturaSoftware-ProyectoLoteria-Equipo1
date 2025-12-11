/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import interfacesComunicacionModelo.IModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author Arell
 */
public class ProcesadorAsignarID implements IModeloChain{
    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {

        if ("ID_ASIGNADO".equals(tipoEvento)) {

            int id = datos.optInt("ID", -1);

            if (id != -1) {
                System.out.println("[ProcesadorAsignarID] → ID recibido del Host: " + id);
                modeloJuego.guardarIDAsignado(id);
            } else {
                System.err.println("[ProcesadorAsignarID] ERROR: IdJugador no encontrado en el JSON.");
            }

        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        } else {
            System.out.println("[Chain] Evento desconocido: " + tipoEvento);
        }
    }
}

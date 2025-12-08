/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import interfacesComunicacionModelo.IModeloJuego;
import modeloJuegoMVC.ModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author isaac
 */
public class ProcesadorFinRonda implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {

        if ("FIN_RONDA".equals(tipoEvento)) {

            int ronda = datos.getInt("Ronda");
            String ganador = datos.getString("Ganador");

            System.out.println("[Cliente-Chain] Recibido Fin de Ronda " + ronda + " ganada por " + ganador);

            modeloJuego.notificarFinRonda(ronda, ganador);

        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        }
    }
}

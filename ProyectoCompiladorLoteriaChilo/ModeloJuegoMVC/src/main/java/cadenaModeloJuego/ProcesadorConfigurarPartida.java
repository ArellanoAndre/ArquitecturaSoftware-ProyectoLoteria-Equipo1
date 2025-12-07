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
public class ProcesadorConfigurarPartida implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {
        if ("CONFIGURAR_PARTIDA".equals(tipoEvento)) {
            modeloJuego.abrirPantallaConfig();
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        } else {
            System.out.println("[Chain] Evento desconocido: " + tipoEvento);
        }
    }
}


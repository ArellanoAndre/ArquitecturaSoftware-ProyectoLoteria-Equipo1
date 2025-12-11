/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public class ProcesadorIniciarPartida implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {
        if ("CAMBIAR_MVC".equals(tipoEvento)) {
            logica.cambioMVC();
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        }
    }

}

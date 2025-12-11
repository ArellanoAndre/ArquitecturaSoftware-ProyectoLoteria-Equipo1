/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesarCadena;

import java.util.HashMap;
import java.util.Map;
import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author Arell
 */
public class ProcesadorAsignarID implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {

        if ("ASIGNAR_ID".equals(tipoEvento)) {
            logica.procesarAsignacionID();
            System.out.println("Llegue a la cadena");
        }

        // Si no es este evento, seguir la cadena
        if (siguiente != null)
            siguiente.procesar(tipoEvento, datos, logica);
    }
}
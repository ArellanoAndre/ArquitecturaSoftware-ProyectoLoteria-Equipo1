/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author isaac
 *
 */
public class ProcesadorUnirse implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {
        if ("SOLICITUD_UNIRSE".equals(tipoEvento)) {
            String nombre = datos.getString("Nombre");
            System.out.println("[Chain] Solicitud de unión: " + nombre);
            logica.agregarJugadores();
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        }
    }
}

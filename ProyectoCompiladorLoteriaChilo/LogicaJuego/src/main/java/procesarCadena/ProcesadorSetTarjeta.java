/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author jorge
 */
public class ProcesadorSetTarjeta implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {
        if ("SET_TARJETA".equals(tipoEvento)) {
            int idJugador = datos.getInt("Jugador");
            String ruta = datos.getString("Tarjeta");
            System.out.println("[Chain] Jugador: " + idJugador + " asignandole la tarjeta: " + ruta);
            logica.asignarTarjetaAJugador(idJugador, ruta);
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        }
    }
}
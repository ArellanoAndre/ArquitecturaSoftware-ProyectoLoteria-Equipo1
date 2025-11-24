/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author isaac
 */
public class ProcesadorMarcar implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {
        if ("INTENTO_MARCAR".equals(tipoEvento)) {
            int idJugador = datos.getInt("Jugador");
            int casilla = datos.getInt("Casilla");
            System.out.println("[Chain] Jugador " + idJugador + " intenta marcar casilla " + casilla);
            logica.verificarCarta(idJugador, casilla);
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        }
    }
}

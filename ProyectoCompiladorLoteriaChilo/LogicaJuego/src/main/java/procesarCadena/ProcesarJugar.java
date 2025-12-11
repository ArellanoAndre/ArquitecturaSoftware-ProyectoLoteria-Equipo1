/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author Arell
 */
public class ProcesarJugar implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {

    if ("JUGAR".equals(tipoEvento)) {

        int idJugador = datos.optInt("ID", -1);

        System.out.println("[FiltroJugar] Evento JUGAR recibido de jugador ID = " + idJugador);

        if (!logica.estaConfiguradaPartida()) {
            System.out.println("[FiltroJugar] → La partida NO está configurada.");
            logica.enviarAbrirPantallaConfig(idJugador);  // ← ahora recibe ID
        } else {
            System.out.println("[FiltroJugar] → La partida YA está configurada.");
            logica.enviarAbrirPantallaSeleccionAvatar(idJugador); // ← recibe ID
        }

        return;
    }

    if (siguiente != null)
        siguiente.procesar(tipoEvento, datos, logica);
}

}

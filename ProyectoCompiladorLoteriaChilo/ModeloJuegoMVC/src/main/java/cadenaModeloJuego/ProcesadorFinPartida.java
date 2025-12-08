/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import ModeloJuegoEntidades.Jugador;
import interfacesComunicacionModelo.IModeloJuego;
import interfacesEntidades.IJugador;
import java.util.ArrayList;
import java.util.List;
import modeloJuegoMVC.ModeloJuego;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author isaac
 */
public class ProcesadorFinPartida implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {
        if ("FIN_PARTIDA".equals(tipoEvento)) {
            System.out.println("[Cliente-Chain] Recibido Fin de Partida. Procesando ranking...");

            List<IJugador> ranking = new ArrayList<>();
            JSONArray listaJson = datos.getJSONArray("Ranking");

            for (int i = 0; i < listaJson.length(); i++) {
                JSONObject jObj = listaJson.getJSONObject(i);

                Jugador jugador = new Jugador();
                jugador.setNumJugador(jObj.getInt("Id"));
                jugador.setNombre(jObj.getString("Nombre"));

                jugador.setPuntaje(jObj.getDouble("Puntos"));
                jugador.setAvatar(jObj.getString("Avatar"));

                ranking.add(jugador);
            }

            modeloJuego.notificarFinPartida(ranking);

        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        }

    }

}

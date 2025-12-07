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
public class ProcesadorConfigurarPartida implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {

        if ("CONFIGURAR_PARTIDA".equals(tipoEvento)) {

            System.out.println("[Chain] Evento CONFIGURAR_PARTIDA recibido");

            // Leer datos enviados por el cliente
            String dificultad = datos.getString("Dificultad");
            int numJugadores = datos.getInt("NumeroJugadores");
            int puntMax = datos.getInt("PuntuacionMaxima");

            // Puntuaciones
            JSONObject puntuacionesJSON = datos.getJSONObject("Puntuaciones");

            System.out.println("[Chain] Configuración recibida:");
            System.out.println("        Dificultad: " + dificultad);
            System.out.println("        Jugadores: " + numJugadores);
            System.out.println("        PuntMax: " + puntMax);
            System.out.println("        Puntuaciones: " + puntuacionesJSON);

            // Mandarlo a la lógica de juego
            logica.configurarPartida(
                    dificultad,
                    numJugadores,
                    puntMax,
                    puntuacionesJSON
            );

            return;
        }

        // Pasar al siguiente filtro
        if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import interfacesComunicacionModelo.IModeloJuego;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Arell
 */
public class ProcesadorFiltroJugador implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {

        System.out.println("[FiltroJugador] Evento recibido: " + tipoEvento);

        int idLocal = 0;

        try {
            if (modeloJuego.getJugadorPrincipal() != null)
                idLocal = modeloJuego.getJugadorPrincipal().getNumJugador();
        } catch (Exception ignored) {}

        int idEvento = datos.optInt("ID", -2); 
        // -2 = "no enviado", -1 = global

        // ================================================================
        // 1. TRATAMIENTO ESPECIAL: ID_ASIGNADO
        // ================================================================
        if ("ID_ASIGNADO".equals(tipoEvento)) {

            if (idLocal != 0) {
                System.out.println("[FiltroJugador] ❌ ID_ASIGNADO ignorado (ya tengo ID=" + idLocal + ")");
                return;
            }

            if (idEvento > 0) {
                System.out.println("[FiltroJugador] ✔ Aceptado ID_ASIGNADO → " + idEvento);
                modeloJuego.guardarIDAsignado(idEvento);
            } else {
                System.out.println("[FiltroJugador] ❌ ID_ASIGNADO inválido");
            }

            return;
        }

        // ================================================================
        // 2. SI NO TENGO ID → IGNORO TODO EXCEPTO ID_ASIGNADO
        // ================================================================
        if (idLocal == 0) {
            System.out.println("[FiltroJugador] ❌ Ignorado → aún no tengo ID asignado");
            return;
        }

        // ================================================================
        // 3. EVENTOS QUE TRAEN LISTA COMPLETA DE JUGADORES (broadcast)
        // ================================================================
        JSONArray jugadores = datos.optJSONArray("Jugadores");

        boolean esEventoGeneral = jugadores != null;

        if (esEventoGeneral) {
            System.out.println("[FiltroJugador] ✔ Evento general → procesado por todos");
            if (siguiente != null) siguiente.procesar(tipoEvento, datos, modeloJuego);
            return;
        }

        // ================================================================
        // 4. EVENTO GLOBAL → ID = -1
        // ================================================================
        if (idEvento == -1) {
            System.out.println("[FiltroJugador] ✔ Evento GLOBAL → todos los jugadores lo procesan");
            if (siguiente != null) siguiente.procesar(tipoEvento, datos, modeloJuego);
            return;
        }

        // ================================================================
        // 5. EVENTO PERSONAL
        // ================================================================
        if (idEvento != idLocal) {
            System.out.println("[FiltroJugador] ❌ Ignorado → Evento personal para " + idEvento + " yo soy " + idLocal);
            return;
        }

        System.out.println("[FiltroJugador] ✔ Evento personal para mí");

        if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        }
    }
}

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
public class ProcesadorFiltroJugador implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {

        System.out.println("[FiltroJugador] Evento recibido: " + tipoEvento);

        // ================================================================
        // 1. OBTENER idLocal (0 si aún no existe jugador)
        // ================================================================
        int idLocal = 0;

        try {
            if (modeloJuego.getJugadorPrincipal() != null) {
                idLocal = modeloJuego.getJugadorPrincipal().getNumJugador();
            }
        } catch (Exception ignored) {}

        // ================================================================
        // 2. Obtener ID del evento
        // ================================================================
        int idEvento = datos.optInt("ID", -1);

        // ================================================================
        // 3. TRATAMIENTO ESPECIAL: ID_ASIGNADO
        // ================================================================
        if ("ID_ASIGNADO".equals(tipoEvento)) {

            // Si YA TENGO ID → no reasignar
            if (idLocal != 0) {
                System.out.println("[FiltroJugador] ❌ ID_ASIGNADO ignorado (ya tengo ID=" + idLocal + ")");
                return;
            }

            // Aceptar SOLO si trae un ID válido > 0
            if (idEvento > 0) {
                System.out.println("[FiltroJugador] ✔ Aceptado ID_ASIGNADO → " + idEvento);
                modeloJuego.guardarIDAsignado(idEvento);
            } else {
                System.out.println("[FiltroJugador] ❌ ID_ASIGNADO inválido");
            }

            return; // NO pasamos a la cadena
        }

        // ================================================================
        // 4. SI NO TENGO ID → NO PUEDO PROCESAR OTROS EVENTOS
        // ================================================================
        if (idLocal == 0) {
            System.out.println("[FiltroJugador] ❌ Ignorado → aún no tengo ID asignado");
            return;
        }

        // ================================================================
        // 5. SI EL EVENTO NO ES PARA ESTE JUGADOR → SE IGNORA
        // ================================================================
        if (idEvento != idLocal) {
            System.out.println("[FiltroJugador] ❌ Ignorado → Evento para " + idEvento + " yo soy " + idLocal);
            return;
        }

        System.out.println("[FiltroJugador] ✔ Aceptado → Evento dirigido a mí (" + idLocal + ")");

        // ================================================================
        // 6. CONTINUAR LA CADENA NORMALMENTE
        // ================================================================
        if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        }
    }
}
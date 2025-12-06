/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import ModeloJuegoEntidades.Jugador;
import interfacesComunicacionModelo.IControlIModeloVista;
import interfacesComunicacionModelo.IControlVista;
import interfacesEntidades.IJugador;
import java.util.ArrayList;
import java.util.List;
import modeloJuegoMVC.ModeloJuego;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Arell
 */
public class ProcesadorUnirsePartida implements IModeloChain  {
    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

      @Override
public void procesar(
        String tipoEvento,
        JSONObject datos,
        IControlVista controlVista,      // IGNORADO EN ESTE CASO
        ModeloJuego modeloJuego,
        IControlIModeloVista modeloVista) {     // ESTE NECEISTA accesar al ControlModeloVista

    if ("Confirmacion_Reglas".equals(tipoEvento)) {

        System.out.println("[FiltroUnirsePartida] → Evento ConfirmacionReglas recibido");

        // === 1) Leer datos del JSON ===
        String dificultad = datos.optString("Dificultad", null);
        int puntuacionMax = datos.optInt("PuntuacionMaxima", 0);

        // === 2) Convertir jugadores JSON -> Lista<IJugador> ===
        List<IJugador> listaJugadores = new ArrayList<>();

        if (datos.has("Jugadores")) {
            JSONArray arr = datos.getJSONArray("Jugadores");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                int id = obj.getInt("IdJugador");
                String nombre = obj.getString("Nombre");
                String avatar = obj.getString("Avatar");

                listaJugadores.add(new Jugador(id, nombre, avatar));
            }
        }

        System.out.println("[FiltroUnirsePartida] Datos procesados:");
        System.out.println(" - Dificultad: " + dificultad);
        System.out.println(" - PuntMax: " + puntuacionMax);
        System.out.println(" - Jugadores: " + listaJugadores.size());

        // === 3) Llamar al ControlModeloVista según diagrama ===
        // modeloVista.getControl() representa ControlModeloVista
        modeloVista.actualizarPantalla(dificultad, listaJugadores, puntuacionMax);
        System.out.println("[FiltroUnirsePartida] → ControlModeloVista.actualizarPantalla llamado con éxito");

        return;
    }

    // No es este evento → Continuar en la cadena
    if (siguiente != null) {
        siguiente.procesar(tipoEvento, datos, controlVista, modeloJuego, modeloVista);
    }
} }

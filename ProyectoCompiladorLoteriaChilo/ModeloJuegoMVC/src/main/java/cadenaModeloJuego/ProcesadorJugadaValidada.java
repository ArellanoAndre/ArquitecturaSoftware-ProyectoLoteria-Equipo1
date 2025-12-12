/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;

import interfacesComunicacionModelo.IModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author abrilislas
 */
public class ProcesadorJugadaValidada implements IModeloChain{
    
    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {
        if ("JUGADA_VALIDA".equals(tipoEvento)) {

            System.out.println("[Chain] Evento JUGADA_VALIDA recibido");

        int idJugador = datos.getInt("Jugador");
        int puntos = datos.getInt("Puntuacion");
        String jugada = datos.getString("Jugada");

        modeloJuego.actualizarPuntuacion(idJugador, puntos, jugada);
        }

        // Pasar al siguiente si no es este evento
        if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        }
    }
    
}

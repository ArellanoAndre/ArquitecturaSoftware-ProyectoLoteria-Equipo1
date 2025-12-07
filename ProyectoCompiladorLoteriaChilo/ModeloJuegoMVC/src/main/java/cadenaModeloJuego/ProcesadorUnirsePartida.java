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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Arell
 */
public class ProcesadorUnirsePartida implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IModeloJuego modeloJuego) {
        if ("Confirmacion_Reglas".equals(tipoEvento)) {
            modeloJuego.actualizarConfiguracion(datos);
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, modeloJuego);
        } else {
            System.out.println("[Chain] Evento desconocido: " + tipoEvento);
        }
    }
}

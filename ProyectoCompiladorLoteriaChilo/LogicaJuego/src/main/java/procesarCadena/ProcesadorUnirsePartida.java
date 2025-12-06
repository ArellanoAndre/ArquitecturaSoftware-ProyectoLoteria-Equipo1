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
public class ProcesadorUnirsePartida implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {

        if ("UNIRSE_PARTIDA".equals(tipoEvento)) {

            System.out.println("[Chain] Evento UNIRSE_PARTIDA recibido");

            String nombre = datos.getString("Nombre");
            String avatar = datos.getString("Avatar");

            System.out.println("[Chain] Solicitud de unión:");
            System.out.println("        Nombre: " + nombre);
            System.out.println("        Avatar: " + avatar);

            // Mandar a la lógica de juego
            logica.agregarJugador(nombre, avatar);

            return;
        }

        // Pasar al siguiente si no es este evento
        if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        }
    }
}
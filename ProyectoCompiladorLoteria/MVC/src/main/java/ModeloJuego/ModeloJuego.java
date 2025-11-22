/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

import Interfaces.IControlVista;
import Interfaces.IModeloJuego;
import ModeloJuego.entidades.Jugador;
import interfacesGlobales.IManejadorEvento;
import java.util.List;
import org.json.JSONObject;

public class ModeloJuego implements IModeloJuego, IManejadorEvento {

    private IControlVista controlVista;
    private Jugador jugadorPrincipal;
    private List<Jugador> jugadoresSecundarios;
    private boolean[] casillasMarcadas = new boolean[16];


    public ModeloJuego(IControlVista controlVista, Jugador jugadorPrincipal, List<Jugador> jugadoresSecundarios) {
        this.controlVista = controlVista;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        controlVista.setJugadorPrincipal(this.jugadorPrincipal);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
    }

    /**
     * Recibe SOLO el JSON payload desde el Desempaquetador AQUI OCUPARIAMOS EL CHAIN OF RESPONSABILITY PARA MANEJAR EVENTO
     */
    @Override
    public void manejar(String payloadJSON) {

        System.out.println("ModeloJuegoMock recibió payload:");
        System.out.println(payloadJSON);

        try {
            JSONObject obj = new JSONObject(payloadJSON);

            String tipo = obj.getString("TipoEvento");

            if (tipo.equals("CasillaSeleccionadaValida")) {

                int jugador = obj.getInt("Jugador");
                int casilla = obj.getInt("Casilla");

                System.out.println("Procesando casilla " + casilla + " para jugador " + jugador);
                casillasMarcadas[casilla] = true;

                // Actualiza la vista
                controlVista.actualizarTarjetaJugadorPrincipal(casillasMarcadas);
            }

        } catch (Exception e) {
            System.err.println("ERROR en ModeloJuegoMock.manejar(): " + e.getMessage());
        }
    }


    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int jugador) {
        // mock vacío
    }

    @Override
    public void setControlVista(IControlVista controlVista) {
        this.controlVista = controlVista;
    }
}
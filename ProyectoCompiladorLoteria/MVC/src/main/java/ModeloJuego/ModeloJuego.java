/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

import Interfaces.IControlVista;
import Interfaces.IModeloJuego;
import ModeloJuego.entidades.Jugador;
import java.util.List;

public class ModeloJuego implements IModeloJuego {

    IControlVista controlVista;
    Jugador jugadorPrincipal;
    List<Jugador> jugadoresSecundarios;

    public ModeloJuego(IControlVista controlVista, Jugador jugadorPrincipal, List<Jugador> jugadoresSecundarios) {
        // Crear procesador y registrar este modelo como listener
        this.controlVista = controlVista;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;

        controlVista.setJugadorPrincipal(this.jugadorPrincipal);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
    }

    @Override
    public void setControlVista(IControlVista controlVista) {
    }

    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {

    }

}

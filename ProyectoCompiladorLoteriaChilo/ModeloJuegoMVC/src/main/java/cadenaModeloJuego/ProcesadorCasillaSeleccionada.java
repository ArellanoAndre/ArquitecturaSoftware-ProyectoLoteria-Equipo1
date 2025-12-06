/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cadenaModeloJuego;


import interfacesComunicacionModelo.IControlIModeloVista;
import interfacesComunicacionModelo.IControlVista;
import interfacesEntidades.IJugador;
import modeloJuegoMVC.ModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public class ProcesadorCasillaSeleccionada implements IModeloChain {

    private IModeloChain siguiente;

    @Override
    public void setSiguiente(IModeloChain siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, IControlVista controlVista, ModeloJuego modeloJuego,IControlIModeloVista modelovista) {
        if ("CASILLA_SELECCIONADA".equals(tipoEvento)) {
            int jugador = datos.getInt("Jugador");
            int casilla = datos.getInt("Casilla");

            if (modeloJuego.getJugadorPrincipal().getNumJugador() == jugador) {
                modeloJuego.getJugadorPrincipal().getTarjeta().marcarCasilla(casilla);

                controlVista.actualizarTarjetaJugadorPrincipal(modeloJuego.getJugadorPrincipal().getTarjeta().getMarcadas());
            } else {
                for (IJugador jugadoresSecundario : modeloJuego.getJugadoresSecundarios()) {
                    if (jugadoresSecundario.getNumJugador() == jugador) {
                        jugadoresSecundario.getTarjeta().marcarCasilla(casilla);
                        controlVista.setJugadoresSecundarios(modeloJuego.getJugadoresSecundarios());
                    }
                }
            }
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, controlVista, modeloJuego, modelovista);
        } else {
            System.out.println("[Chain] Evento desconocido: " + tipoEvento);
        }
    }

}

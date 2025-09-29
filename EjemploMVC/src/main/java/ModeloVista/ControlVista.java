/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista;

import ModeloJuego.entidades.Jugador;
import ModeloVista.entidadesVista.JugadorVista;
import ModeloVista.entidadesVista.TarjetaVista;

/**
 *
 * @author rodri
 */
public class ControlVista {
    
    private ModeloVista modeloVista;
    
    public ControlVista(ModeloVista modeloVista) {
        this.modeloVista = modeloVista;
    }
    
    public void setJugadorPrincipal(Jugador jugador){
        TarjetaVista tarjeta = new TarjetaVista(jugador.getTarjeta().getMarcadas());
        JugadorVista jugadorP = new JugadorVista(jugador.getNombre(), tarjeta, 1);
        modeloVista.setJugadorPrincipal(jugadorP);
    }
    
    public void actualizarTarjetaJugadorPrincipal (boolean[] marcadas){
        modeloVista.actualizarTarjetaJugadorP(marcadas);
    }
    
    
    
    
    
}

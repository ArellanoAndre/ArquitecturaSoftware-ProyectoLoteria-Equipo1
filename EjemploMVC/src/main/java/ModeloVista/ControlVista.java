/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista;

import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import ModeloVista.entidadesVista.TarjetaVista;
import java.util.ArrayList;
import java.util.List;

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
        JugadorVista jugadorP = new JugadorVista(jugador.getNombre(), tarjeta, jugador.getNumJugador());
        modeloVista.setJugadorPrincipal(jugadorP);
    }
    
    public void actualizarTarjetaJugadorPrincipal (boolean[] marcadas){
        modeloVista.actualizarTarjetaJugadorP(marcadas);
    }
    
    public void setJugadoresSecundarios(List<Jugador> jugadores){
        List<JugadorVista> jugadoresV = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            TarjetaVista tarjeta = new TarjetaVista(jugador.getTarjeta().getMarcadas());
            JugadorVista jugadorSV = new JugadorVista(jugador.getNombre(), tarjeta, jugador.getNumJugador());
            jugadoresV.add(jugadorSV);
        }
        modeloVista.setJugadoresSecundarios(jugadoresV);
    }
    
    public void actualizarCartaCantada (Carta cartaActual){
        CartaVista cv = new CartaVista(
                cartaActual.getNumCarta(),
                cartaActual.getNombreCarta(),
                "/img/Cartas/" + cartaActual.getNumCarta() + ".jpeg"
        );
        
        modeloVista.setCartaCantada(cv);
    }
    
    
    
    
    
}

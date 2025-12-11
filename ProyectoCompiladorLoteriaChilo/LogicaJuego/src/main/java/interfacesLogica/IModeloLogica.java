/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesLogica;

import interfacesEntidades.IJugador;
import java.util.List;
import logicaJuego.entidades.Jugador;

/**
 *
 * @author isaac
 */
public interface IModeloLogica {

    void EnviarEventoCartaSeleccionada(int pos, int idJugador);

    void notificarCartaCantada(int idCarta, String nombreCarta);

    void notificarJugadaValida(int posicion, int idJugador);

    void notificarGanador(String nombreGanador);

     public void enviarConfirmacionReglas(
            String dificultad,
            int numRondas,
            List<Jugador> jugadores,
            int numJugadores,
            List<String> tarjetas,int id);

    void enviarAbrirPantallaConfig(int idJugador);

    void enviarAbrirPantallaSeleccionAvatar(int idJugador);

    void EnviarEventoIniciarSiguienteRonda();

    void EnviarEventoCantarLoteria();
    
    void notificarFinDeRonda(int ronda, String nombreGanador);
    
    void notificarFinPartida(List<Jugador> ranking);
    
    void enviarEventoCambiarMVC();
    
    void enviarTarjetaAsignada(int idJugador, String rutaTarjeta);

    public void enviarIDAsignadoAlCliente(int nuevoID);
}

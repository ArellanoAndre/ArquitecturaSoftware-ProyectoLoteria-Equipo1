/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesComunicacionModelo;

import interfacesEntidades.IJugador;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public interface IModeloJuego {

    void enviarNombreAvatarConfirmado(int id, String nombre, String avatar);
    
    void enviarTarjetaSeleccionada(String tarjetaRuta);

    void enviarEventoIniciarRonda();

    void enviarEventoCartaSeleccionada(int pos, int jugador);

    void enviarEventoBroadcast(String jsonPayload, String topico, String Evento);

    public void suscribirseJuegoOut();

    public void actualizarCartaCantada(JSONObject datos);

    public void actualizarCasillaSeleccionada(JSONObject datos);

    public void actualizarConfiguracion(JSONObject datos);

    public void setControlVistaInicio(IControlVistaMVC_Inicio controlVistaInicio);

    public void setControlVistaJuego(IControlVistaMVC_Juego controlVistaJuego);

    void enviarEventoJugar();

    void abrirPantallaConfig(int id);

    void abrirPantallaAvatar(int id);
    
    public void AsignarID();
    public void guardarIDAsignado(int idJugador) ;

    public void enviarEventoConfigurarPartida(String dificultad, int numJugadores,
            int puntuacionMax,
            Map<String, Integer> puntuaciones);

    void notificarFinRonda(int ronda, String ganador);

    public void notificarFinRondaBaraja();
    
    void notificarFinPartida(List<IJugador> ranking);

    void enviarEventoCantarLoteria();

    void enviarEventoIniciarSiguienteRonda();
    
    void limpiarEntidadesJuego();
    
    void enviarEventoCambiarMVC();

    void cambiarMVC();
    
    void asignarTarjeta(JSONObject datos);
    
    boolean isRegistrado();
    
    void setRegistrado(boolean registrado);
    
    public boolean isHost();
    
    public void setHost(boolean host);
    
     int ObtenerIDJugadorPrincipal();
     
     public IJugador getJugadorPrincipal();
}

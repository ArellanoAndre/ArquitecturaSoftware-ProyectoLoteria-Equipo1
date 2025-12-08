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

    void EnviarNombreAvatarConfirmado(String nombre, String avatar);

    void EnviarEventoConfigurarPartida();

    void EnviarEventoIniciarRonda();

    void EnviarEventoCartaSeleccionada(int pos, int jugador);

    void enviarEventoBroadcast(String jsonPayload, String topico);

    public void suscribirseJuegoOut();

    public void actualizarCartaCantada(JSONObject datos);

    public void actualizarCasillaSeleccionada(JSONObject datos);

    public void actualizarConfiguracion(JSONObject datos);

    public void setControlVistaInicio(IControlVistaMVC_Inicio controlVistaInicio);

    public void setControlVistaJuego(IControlVistaMVC_Juego controlVistaJuego);

    void enviarEventoJugar();

    void abrirPantallaConfig();

    void abrirPantallaAvatar();

    public void EnviarEventoConfigurarPartida(String dificultad, int numJugadores,
            int puntuacionMax,
            Map<String, Integer> puntuaciones);

    void notificarFinRonda(int ronda, String ganador);

    void notificarFinPartida(List<IJugador> ranking);

    void EnviarEventoCantarLoteria();

    void EnviarEventoIniciarSiguienteRonda();

}

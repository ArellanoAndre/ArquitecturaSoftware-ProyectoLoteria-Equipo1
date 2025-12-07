/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesComunicacionModelo;

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
}

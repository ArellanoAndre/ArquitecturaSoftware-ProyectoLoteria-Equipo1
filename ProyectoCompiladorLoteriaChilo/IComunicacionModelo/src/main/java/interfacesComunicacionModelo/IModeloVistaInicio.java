/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesComunicacionModelo;

import interfacesEntidades.IConfiguracionPartida;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author Arell
 */
public interface IModeloVistaInicio {

    void actualizarConfiguracion(IConfiguracionPartida config);

    public void EnviarNombreAvatarConfirmado(int id, String nombre, String avatar);
    
    public void enviarTarjetaSeleccionada(String tarjetaRuta);

    public void setModeloJuego(IModeloJuego modeloJuego);

    IControladorInicio getControlador();

    void setControlador(IControladorInicio controlador);

    IConfiguracionPartida getConfiguracion();

    void setPantallaAvatar(JFrame p);

    void setPantallaLobby(JFrame l);

    void solicitarIniciarJuego();

    void abrirPantallaConfig(int id);

    void setPantallaConfig(JFrame pantallaConfig);

    void abrirPantallaAvatar(int id);

    void setMenuPrincipal(JFrame MenuPrincipal);

    public void CrearPartida(String dificultad, int numJugadores, int puntuacionMax,
            Map<String, Integer> puntuaciones);

    void abrirSeleccionAvatar();
    
    void cambiarMVC();
    
    public void notificarCambioMVC();

    public void abrirPantallaMenu(int id);
    
    public boolean isHost();
}

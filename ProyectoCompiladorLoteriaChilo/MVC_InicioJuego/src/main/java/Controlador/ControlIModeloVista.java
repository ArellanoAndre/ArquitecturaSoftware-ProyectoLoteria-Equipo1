/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import ModeloVista.ConfiguracionPartida;
import Presentacion.JFrameLobby;
import interfacesComunicacionModelo.IControlIModeloVista;
import interfacesEntidades.IConfiguracionPartida;
import interfacesEntidades.IJugador;
import java.util.List;

/**
 *
 * @author Arell
 */
public class ControlIModeloVista implements IControlIModeloVista{
     private  IConfiguracionPartida config;
     private JFrameLobby lobby;

    public ControlIModeloVista() {
    }
     

    public ControlIModeloVista(IConfiguracionPartida configPartida) {
        this.config = configPartida;
    }

    public void actualizarPantalla(String dificultad, List<IJugador> jugadores, int puntuacionMax) {

        // Actualiza el modelo (ConfiguraciónPartida)
        config.setDificultad(dificultad);
        config.setJugadores(jugadores);
        config.setPuntuacionMaxima(puntuacionMax);

        // Crear lobby si no existe
        if (lobby == null) {
            lobby = new JFrameLobby();
        }

        // Pasarle los datos al Lobby
        lobby.setDatosPartida(dificultad, jugadores.size(), puntuacionMax, jugadores.get(0).getNombre());

        // Mostrar jugadores visualmente
        lobby.actualizarJugadores(jugadores);

        lobby.setVisible(true);
    }

    public JFrameLobby getLobby() {
        return lobby;
    }
    


    public IConfiguracionPartida getConfigPartida() {
        return config;
    }

   

    @Override
    public void setConfig(IConfiguracionPartida config) {
        this.config = config;
    }

    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesComunicacionModelo;

import interfacesEntidades.IConfiguracionPartida;
import interfacesEntidades.IJugador;
import java.util.List;

/**
 *
 * @author Arell
 */
public interface IControlIModeloVista {
    void actualizarPantalla(String dificultad, List<IJugador> jugadores, int puntuacionMaxima);
     void setConfig(IConfiguracionPartida config);
}

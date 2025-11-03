/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observer;

import Observer.IControlVista;

/**
 *
 * @author rodri
 */
public interface IModeloJuego {
    
    void setControlVista (IControlVista controlVista);
    
    void EnviarEventoCartaSeleccionada(int pos, int idJugador);
    
    
}

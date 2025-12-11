/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesComunicacionModelo;

import interfacesEntidades.IConfiguracionPartida;

/**
 *
 * @author Arell
 */
public interface IControlVistaMVC_Inicio {
    void actualizarPantalla(IConfiguracionPartida configuracion);
     void setConfig(IConfiguracionPartida config);
      void abrirPantallaConfig(int id );
      void abrirPantallaAvatar(int id);
      void cambiarMVC();
      public void abrirPantallaMenu(int idJugador) ;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesEventClient;

/**
 *
 * @author Arell
 */
public interface IEnvioEvento {
    
    public IEvento crearEvento();
    
    public void enviarEvento(IEvento evento);    
}

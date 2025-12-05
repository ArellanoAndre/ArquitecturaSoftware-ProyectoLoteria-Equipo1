/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cadenaModeloJuego;

import interfacesComunicacionModelo.IControlVista;
import modeloJuegoMVC.ModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public interface IModeloChain {
    
    void setSiguiente(IModeloChain siguiente);
    void procesar(String tipoEvento, JSONObject datos, IControlVista controlVista, ModeloJuego modeloJuego);
    
}

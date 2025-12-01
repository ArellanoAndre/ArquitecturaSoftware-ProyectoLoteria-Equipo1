/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author isaac
 */
public interface IProcesadorEvento {
    
    void setSiguiente(IProcesadorEvento siguiente);
    void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica);
    
}

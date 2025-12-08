/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package procesarCadena;

import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;

/**
 *
 * @author isaac
 */
public class ProcesadorInicioSiguienteRonda implements IProcesadorEvento {

    private IProcesadorEvento siguiente;

    @Override
    public void setSiguiente(IProcesadorEvento siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public void procesar(String tipoEvento, JSONObject datos, LogicaDeJuego logica) {
        
        if ("INICIAR_SIGUIENTE_RONDA".equals(tipoEvento)) {
            System.out.println("[Chain] El Host solicitó siguiente ronda.");
            logica.iniciarSiguienteRonda(); 
        } else if (siguiente != null) {
            siguiente.procesar(tipoEvento, datos, logica);
        } else {
            System.out.println("Fin de la cadena o evento no reconocido" + tipoEvento);
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package receptor;

import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import cadenaModeloJuego.IModeloChain;
import cadenaModeloJuego.ProcesadorCartaCantada;
import cadenaModeloJuego.ProcesadorCasillaSeleccionada;
import cadenaModeloJuego.ProcesadorConfigurarPartida;
import cadenaModeloJuego.ProcesadorFinPartida;
import cadenaModeloJuego.ProcesadorFinRonda;
import cadenaModeloJuego.ProcesadorSuscripcion;
import cadenaModeloJuego.ProcesadorUnirsePartida;
import interfacesComunicacionModelo.IModeloJuego;
import org.json.JSONObject;

/**
 *
 * @author rodri
 */
public class ReceptorModelo implements IReceptorEvento {

    private IModeloChain cadenaProcesamiento;
    private IModeloJuego modeloJuego;

    public ReceptorModelo(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
        armarFiltros();
    }

    public void armarFiltros(){
        IModeloChain cartaCantada = new ProcesadorCartaCantada();
        IModeloChain casillaSeleccionada = new ProcesadorCasillaSeleccionada(); 
        IModeloChain unirsepartida = new ProcesadorUnirsePartida(); 
        IModeloChain configurar = new ProcesadorConfigurarPartida();
        IModeloChain finRonda = new ProcesadorFinRonda();
        IModeloChain finPartida = new ProcesadorFinPartida();
        IModeloChain suscripcion = new ProcesadorSuscripcion();
        
        cartaCantada.setSiguiente(casillaSeleccionada);
        casillaSeleccionada.setSiguiente(unirsepartida); 
        unirsepartida.setSiguiente(configurar);
        configurar.setSiguiente(finRonda);
        finRonda.setSiguiente(finPartida);
        finPartida.setSiguiente(suscripcion);
        suscripcion.setSiguiente(null);
              
        this.cadenaProcesamiento = cartaCantada;
    }
    
    @Override
    public void manejar(IEvento evento) {
        System.out.println("[ReceptorModelo] Evento recibido COMPLETO:");
        System.out.println(evento);

        try {
            // Extraer el JSON interno del EVENTO
            String payloadJSON = evento.getJSON();

            if (payloadJSON == null) {
                System.err.println("[ReceptorModelo] JSON interno es null");
                return;
            }
            JSONObject obj = new JSONObject(payloadJSON);
            // Identificar el tipo de evento interno
            String tipo = obj.getString("TipoEvento");

            System.out.println("[ModeloJuegoMock] TipoEvento = " + tipo);
            cadenaProcesamiento.procesar(tipo, obj, modeloJuego);

        } catch (Exception e) {
            System.err.println("[ModeloJuegoMock] ERROR manejando evento: " + e.getMessage());
        }
    }

}

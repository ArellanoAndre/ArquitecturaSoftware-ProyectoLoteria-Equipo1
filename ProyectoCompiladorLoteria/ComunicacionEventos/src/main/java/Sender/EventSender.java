package Sender;

import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverEntrada;
import colaGenerica.ObserverSalida;
import interfacesGlobales.IDispatcher;

/**
 * Sender: Toma mensajes de una cola de entrada y los envia al componente de
 * red. Implementa Observer para reaccionar cuando haya un nuevo mensaje
 * disponible.
 */
public class EventSender implements ObserverSalida {

    private IDispatcher dispatcher;
    private ColaDePrioridad<String> colaSalida;

    /**
     * @param dispatcher Componente encargado de enviarlos por la red.
     * @param colaEntrada Cola de donde se tomaran los mensajes.
     */
    public EventSender(ColaDePrioridad<String> colaEntrada) {
        this.colaSalida = colaEntrada;
    }

    @Override
    public void updateSalida() {
        try {
            // Recupera el mensaje recien agregado (o el primero)
            String json = colaSalida.take();

            if (json == null) {
                System.err.println("[Sender] No hay mensaje disponible en la cola.");
                return;
            }

            System.out.println("[Sender] Mensaje recibido desde cola de salia: " + json);
            Send(json);

        } catch (Exception e) {
            System.err.println("[Sender] Error procesando entrada: " + e.getMessage());
        }
    }

    /**
     * Verifica que el mensaje no sea nulo ni vacío, y delega al metodo dispatch
     * del Dispatcher, el cual se encarga de enviarlo por red.
     *
     * @param json cadena JSON a ser procesada
     */
    public void Send(String json) {
        try {
            dispatcher.dispatch(json);
            System.out.println("[Sender] Mensaje enviado al Dispatcher.");
        } catch (Exception e) {
            System.err.println("[Sender] Error al enviar JSON: " + e.getMessage());
        }
    }

    public void setDispatcher(IDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}

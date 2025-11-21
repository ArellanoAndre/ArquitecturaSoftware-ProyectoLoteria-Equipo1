package Sender;

import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverSalida;
import eventoRed.EventoRed;
import interfacesGlobales.IDispatcher;

/**
 * EventSender: Toma mensajes de una cola de salida y los envia al componente de
 * red. Implementa Observer para reaccionar cuando haya un nuevo mensaje
 * disponible.
 */
public class EventSender implements ObserverSalida {

    private IDispatcher dispatcher;
    private ColaDePrioridad<EventoRed> colaSalida;

    /**
     * @param colaEntrada Cola de donde se tomaran los mensajes.
     */
    public EventSender(ColaDePrioridad<EventoRed> colaEntrada) {
        this.colaSalida = colaEntrada;
    }

    /**
     * Responde a un nuevo elemento en la cola de salida y llama a send
     */
    @Override
    public void updateSalida() {
        try {
            // Recupera el mensaje recien agregado (o el primero)
            EventoRed evento = colaSalida.take();

            if (evento == null) {
                System.err.println("[Sender] No hay mensaje disponible en la cola.");
                return;
            }

            System.out.println("\n [Sender] Enviando a dispatcher");
            send(evento);

        } catch (Exception e) {
            System.err.println("[Sender] Error procesando entrada: " + e.getMessage());
        }
    }

    /**
     * Delega al metodo dispatch del Dispatcher, el cual se encarga de enviarlo
     * por red.
     *
     * @param eventoRed EventoRed ya empaquetado para mandar a dispatcher
     */
    public void send(EventoRed eventoRed) {
        try {
            dispatcher.dispatch(eventoRed);
            System.out.println("[Sender] Mensaje enviado al Dispatcher.");
        } catch (Exception e) {
            System.err.println("[Sender] Error al enviar JSON: " + e.getMessage());
        }
    }

    /**
     * @param dispatcher Componente encargado de enviar mensajes a la capa de
     * red.
     */
    public void setDispatcher(IDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

}

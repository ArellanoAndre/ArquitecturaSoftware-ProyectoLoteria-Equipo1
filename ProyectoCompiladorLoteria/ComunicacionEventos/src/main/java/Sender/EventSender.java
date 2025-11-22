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
     * @param colaSalida Cola de donde se tomaran los mensajes.
     */
    public EventSender(ColaDePrioridad<EventoRed> colaSalida) {
        this.colaSalida = colaSalida;
    }

    /**
     * Responde a un nuevo elemento en la cola de salida y llama a send
     */
    @Override
    public void updateSalida() {
        try {
            // Recupera el mensaje recien agregado (o el primero)
            EventoRed evento = colaSalida.take();

            System.out.println("\n [EventSender] Evento tomado de la cola. Enviando a dispatcher...");
            send(evento);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[EventSender] Hilo interrumpido al tomar la cola.");
        } catch (Exception e) {
            System.err.println("[EventSender] Error procesando evento: " + e.getMessage());
        }
    }

    /**
     * Delega al metodo dispatch del Dispatcher, el cual se encarga de enviarlo
     * por red.
     *
     * @param eventoRed EventoRed ya empaquetado para mandar a dispatcher
     */
    public void send(EventoRed eventoRed) {
        if (dispatcher == null) {
            System.err.println("[EventSender] Dispatcher no inicializado. Evento no enviado.");
            return;
        }

        try {
            dispatcher.dispatch(eventoRed);
            System.out.println("[EventSender] Mensaje enviado al Dispatcher.");
        } catch (Exception e) {
            System.err.println("[EventSender] Error al enviar JSON: " + e.getMessage());
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

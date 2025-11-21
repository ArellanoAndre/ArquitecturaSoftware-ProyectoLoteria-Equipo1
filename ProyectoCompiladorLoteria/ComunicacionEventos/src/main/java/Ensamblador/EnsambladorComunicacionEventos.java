package Ensamblador;

import colaGenerica.ColaDePrioridad;
import Sender.EventSender;
import eventoRed.EventoRed;
import listener.EventListener;
import interfacesGlobales.IDispatcher;
import interfacesGlobales.IManejadorEvento;


public class EnsambladorComunicacionEventos {

    private ColaDePrioridad<String> colaEntradaEventos;
    private ColaDePrioridad<EventoRed> colaSalidaEventos;

    private EventListener listener;
    private EventSender sender;

    public EnsambladorComunicacionEventos(IManejadorEvento manejadorSuperior) {
        ensamblar(manejadorSuperior);
    }

    private void ensamblar(IManejadorEvento manejadorSuperior) {
        colaEntradaEventos = new ColaDePrioridad<>();
        colaSalidaEventos  = new ColaDePrioridad<>();

        // Desde red → JSON → colaEntradaEventos
        listener = new EventListener(colaEntradaEventos);

        // colaSalidaEventos → EventSender → IDispatcher (que luego va a la red)
        sender = new EventSender(colaSalidaEventos);
        colaSalidaEventos.addObserverSalida(sender);
    }

    // Entrada desde la capa de red (cuando llega JSON del socket)
    public void recibirJSONDesdeRed(String json) {
        listener.recibirJSON(json);
    }

    // Para conectar el dispatcher de la capa de red (adaptado)
    public void asignarDispatcher(IDispatcher dispatcherEventos) {
        sender.setDispatcher(dispatcherEventos);
    }

    public EventListener getListener() {
        return listener;
    }
}

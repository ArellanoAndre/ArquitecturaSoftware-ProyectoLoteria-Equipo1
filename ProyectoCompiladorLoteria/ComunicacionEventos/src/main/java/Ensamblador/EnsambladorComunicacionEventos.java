package Ensamblador;

import colaGenerica.ColaDePrioridad;
import Empaquetador.Empaquetador;
import Desempaquetador.Desempaquetador;
import Sender.EventSender;
import interfaces.IReceptor;
import listener.EventListener;
import interfacesGlobales.IDispatcher;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;
import interfacesGlobales.IReceptorJSON;


public class EnsambladorComunicacionEventos {

    private ColaDePrioridad<String> colaEntradaEventos;
    private ColaDePrioridad<String> colaSalidaEventos;

    private EventListener listener;
    private Desempaquetador desempaquetador;
    private Empaquetador empaquetador;
    private EventSender sender;

    public EnsambladorComunicacionEventos(IManejadorEvento manejadorSuperior) {
        ensamblar(manejadorSuperior);
    }

    private void ensamblar(IManejadorEvento manejadorSuperior) {
        colaEntradaEventos = new ColaDePrioridad<>();
        colaSalidaEventos  = new ColaDePrioridad<>();

        // Desde red → JSON → colaEntradaEventos
        listener = new EventListener(colaEntradaEventos);
        
        // colaEntradaEventos → Desempaquetador → IManejadorEvento
        desempaquetador = new Desempaquetador(colaEntradaEventos, manejadorSuperior);
        colaEntradaEventos.addObserverEntrada(desempaquetador);

        // Lógica → Empaquetador → JSON → colaSalidaEventos
        empaquetador = new Empaquetador();
        empaquetador.setColaSalida(colaSalidaEventos);

        // colaSalidaEventos → EventSender → IDispatcher (que luego va a la red)
        sender = new EventSender(colaSalidaEventos);
        colaSalidaEventos.addObserverSalida(sender);
    }

    // Entrada desde la capa de red (cuando llega JSON del socket)
    public void recibirJSONDesdeRed(String json) {
        listener.recibirJSON(json);
    }

    // Salida hacia la red (la lógica genera un IEvento)
    public void enviarEventoDesdeLogica(IEvento evento) {
        empaquetador.empaquetar(evento);
    }

    // Para conectar el dispatcher de la capa de red (adaptado)
    public void asignarDispatcher(IDispatcher dispatcherEventos) {
        sender.setDispatcher(dispatcherEventos);
    }

    public EventListener getListener() {
        return listener;
    }
}

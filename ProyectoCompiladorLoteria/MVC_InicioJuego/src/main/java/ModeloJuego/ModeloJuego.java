package ModeloJuego;

import Empaquetador.Empaquetador;
import Interfaces.IModeloJuego;
import builder.EventBuilder;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;

public class ModeloJuego implements IModeloJuego, IManejadorEvento {

    private EventBuilder eventBuilder;
    private Empaquetador empaquetador;

    @Override
    public void setEventBuilder(EventBuilder eventBuilder) {
        this.eventBuilder = eventBuilder;
    }

    @Override
    public void setEmpaquetador(Empaquetador empaquetador) {
        this.empaquetador = empaquetador;
    }

    @Override
    public void manejar(String payloadJSON) {
        System.out.println("[ModeloJuego] Payload recibido: " + payloadJSON);
    }

    @Override
    public void enviarEventoCartaSeleccionada(int pos, int jugador) {
        if (eventBuilder == null || empaquetador == null) {
            System.err.println("[ModeloJuego] Error: faltan dependencias (EventBuilder o Empaquetador).");
            return;
        }

        IEvento evento = eventBuilder.crearEvento();
        evento.setTopico("Juego-in");
        evento.setEvento("Juego");
        evento.setJSON("{ \"TipoEvento\": \"CasillaSeleccionadaValida\", \"Jugador\": " + jugador
                + ", \"Casilla\": " + pos + " }");
        empaquetador.empaquetar(evento);
    }
}

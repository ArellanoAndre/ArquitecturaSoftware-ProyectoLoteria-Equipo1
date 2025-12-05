package ModeloJuego;

import Empaquetador.Empaquetador;
import Interfaces.IModeloJuegoInicio;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;

public class ModeloJuegoInicio implements IModeloJuegoInicio, IReceptorEvento {

    private Empaquetador empaquetador;

    public ModeloJuegoInicio() {
    }

    @Override
    public void setEmpaquetador(Empaquetador empaquetador) {
        this.empaquetador = empaquetador;
    }

    @Override
    public void enviarEventoCartaSeleccionada(int pos, int jugador) {
        if (empaquetador == null) {
            System.err.println("[ModeloJuego] Error: faltan dependencias (EventBuilder o Empaquetador).");
            return;
        }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setEvento("Juego");
        evento.setJSON("{ \"TipoEvento\": \"CasillaSeleccionadaValida\", \"Jugador\": " + jugador
                + ", \"Casilla\": " + pos + " }");
        empaquetador.enviarEvento(evento);
    }

    @Override
    public void enviarEventoInicioPartida(String jugador, String dificultad, Integer numeroJugadores, Integer puntuacionMaxima) {
        if (empaquetador == null) {
            System.err.println("[ModeloJuego] Error: faltan dependencias (EventBuilder o Empaquetador).");
            return;
        }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setEvento("Juego");
        evento.setJSON("{ \"TipoEvento\": \"InicioPartida\", \"Jugador\": \"" + jugador
                + "\", \"Dificultad\": \"" + dificultad + "\", \"Jugadores\": " + numeroJugadores
                + ", \"PuntuacionMaxima\": " + puntuacionMaxima + " }");
        empaquetador.enviarEvento(evento);
    }

    @Override
    public void manejar(IEvento evento) {
        System.out.println("[ModeloJuego] Payload recibido: " + evento);
    }

}

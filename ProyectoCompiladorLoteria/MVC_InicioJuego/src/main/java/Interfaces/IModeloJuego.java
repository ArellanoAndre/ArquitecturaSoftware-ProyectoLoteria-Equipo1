package Interfaces;

import builder.EventBuilder;
import Empaquetador.Empaquetador;

public interface IModeloJuego {

    void setEventBuilder(EventBuilder eventBuilder);

    void setEmpaquetador(Empaquetador empaquetador);

    void manejar(String payloadJSON);

    void enviarEventoCartaSeleccionada(int pos, int jugador);

    void enviarEventoInicioPartida(String jugador, String dificultad, Integer numeroJugadores, Integer puntuacionMaxima);
}
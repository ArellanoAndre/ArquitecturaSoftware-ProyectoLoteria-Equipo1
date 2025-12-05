package Interfaces;

import Empaquetador.Empaquetador;

public interface IModeloJuegoInicio {

  //  void setEventBuilder(EventBuilder ev);

    void setEmpaquetador(Empaquetador empaquetador);

    void enviarEventoCartaSeleccionada(int pos, int jugador);

    void enviarEventoInicioPartida(String jugador, String dificultad, Integer numeroJugadores, Integer puntuacionMaxima);
}
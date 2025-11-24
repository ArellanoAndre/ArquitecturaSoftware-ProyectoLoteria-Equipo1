package Interfaces;

import java.util.Map;

public interface IControladorInicio {

    void onConfigChanged(String dificultad, Integer numeroJugadores, Integer puntuacionMaxima,
            Map<String, Integer> puntuaciones);

    void onConfirmarConfig();

    void onSalir();

    void onNombreAvatarConfirmado(String nombre);

    void onIniciarLobby();
}

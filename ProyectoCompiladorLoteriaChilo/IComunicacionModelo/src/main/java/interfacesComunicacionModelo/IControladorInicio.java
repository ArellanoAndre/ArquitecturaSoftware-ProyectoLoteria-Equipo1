package interfacesComunicacionModelo;

import java.util.Map;

public interface IControladorInicio {

    void onConfigChanged(String dificultad, Integer numeroJugadores, Integer puntuacionMaxima,
            Map<String, Integer> puntuaciones);

    void onConfirmarConfig();

    void onSalir();

    void EnviarNombreAvatarConfirmado(String nombre, String avatar);

    void onIniciarLobby();

    void onJugar();

    public void onConfirmarConfig(
            String dificultad,
            int numJugadores,
            int puntuacionMax,
            Map<String, Integer> puntuaciones
    );

    void abrirLobby();

    void abrirSeleccionAvatar();
    
    void enviarTarjetaSeleccionada(String tarjetaRuta);
    
    void cambiarMVC();
}

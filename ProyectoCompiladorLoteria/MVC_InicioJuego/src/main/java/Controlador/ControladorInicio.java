package Controlador;

import ModeloVista.ModeloVista;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import Presentacion.JPantallaConfigurarPartida;
import java.util.Map;
import javax.swing.JOptionPane;

public class ControladorInicio {

    private final ModeloVista modeloVista;
    private JPantallaConfigurarPartida pantallaConfig;
    private JFrameSeleccionAvatar seleccionAvatar;
    private JFrameLobby lobby;

    public ControladorInicio() {
        modeloVista = new ModeloVista();
        pantallaConfig = new JPantallaConfigurarPartida(this);
        pantallaConfig.setVisible(true);
    }

    public void onConfigChanged(String dificultad, Integer numeroJugadores, Integer puntuacionMaxima,
            Map<String, Integer> puntuaciones) {
        modeloVista.setDificultad(dificultad);
        modeloVista.setNumeroJugadores(numeroJugadores);
        modeloVista.setPuntuacionMaxima(puntuacionMaxima);
        modeloVista.setPuntuaciones(puntuaciones);
        if (lobby != null && dificultad != null) {
            lobby.setNivel(dificultad);
        }
    }

    public void onConfirmarConfig() {
        String dificultad = pantallaConfig.getDificultadSeleccionada();
        Integer numeroJugadores = pantallaConfig.getNumeroJugadoresSeleccionado();
        Integer puntuacionMaxima = pantallaConfig.getPuntuacionMaxima();
        Map<String, Integer> puntuaciones = pantallaConfig.getPuntuaciones();

        if (dificultad == null) {
            pantallaConfig.mostrarError("Selecciona un nivel de dificultad.");
            return;
        }

        if (numeroJugadores == null) {
            pantallaConfig.mostrarError("Selecciona la cantidad de jugadores.");
            return;
        }

        if (puntuacionMaxima == null || puntuacionMaxima <= 0) {
            pantallaConfig.mostrarError("Ingresa una puntuación máxima válida (mayor a 0).");
            return;
        }

        if (puntuaciones == null || puntuaciones.values().stream().anyMatch(v -> v == null || v <= 0)) {
            pantallaConfig.mostrarError("Todas las puntuaciones deben ser números mayores a 0.");
            return;
        }

        modeloVista.setDificultad(dificultad);
        modeloVista.setNumeroJugadores(numeroJugadores);
        modeloVista.setPuntuacionMaxima(puntuacionMaxima);
        modeloVista.setPuntuaciones(puntuaciones);

        abrirSeleccionAvatar();
        pantallaConfig.dispose();
    }

    public void onSalir() {
        if (pantallaConfig != null) {
            pantallaConfig.dispose();
        }
        if (seleccionAvatar != null) {
            seleccionAvatar.dispose();
        }
        if (lobby != null) {
            lobby.dispose();
        }
        System.exit(0);
    }

    public void onNombreAvatarConfirmado(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            if (seleccionAvatar != null) {
                seleccionAvatar.mostrarError("Ingresa un nombre de jugador.");
            }
            return;
        }

        modeloVista.setNombreJugador(nombre.trim());
        abrirLobby();
        if (seleccionAvatar != null) {
            seleccionAvatar.dispose();
        }
    }

    public void onIniciarLobby() {
        JOptionPane.showMessageDialog(lobby, "Partida lista para iniciar. Jugador: " + modeloVista.getNombreJugador(),
                "Lobby", JOptionPane.INFORMATION_MESSAGE);
    }

    private void abrirSeleccionAvatar() {
        if (seleccionAvatar == null) {
            seleccionAvatar = new JFrameSeleccionAvatar(this);
        }
        seleccionAvatar.setVisible(true);
    }

    private void abrirLobby() {
        if (lobby == null) {
            lobby = new JFrameLobby(this);
        }
        lobby.setNivel(modeloVista.getDificultad());
        lobby.setVisible(true);
    }
}

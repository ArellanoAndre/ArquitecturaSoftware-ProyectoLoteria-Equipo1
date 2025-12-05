package Controlador;

import Interfaces.IControladorInicio;
import ModeloVista.ModeloVista;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import Presentacion.JPantallaConfigurarPartida;
import Presentacion.JPantallaMenuPrincipal;
import java.util.Map;
import javax.swing.JOptionPane;
import Interfaces.IModeloJuegoInicio;

public class ControladorInicio implements IControladorInicio {

    private final ModeloVista modeloVista;
    private final IModeloJuegoInicio modeloJuego;
    private JPantallaMenuPrincipal inicio;
    private JPantallaConfigurarPartida pantallaConfig;
    private JFrameSeleccionAvatar seleccionAvatar;
    private JFrameLobby lobby;

    public ControladorInicio(IModeloJuegoInicio modeloJuego) {
        this.modeloJuego = modeloJuego;
        this.modeloVista = new ModeloVista();
        
    }

    public ControladorInicio(ModeloVista modeloVista, IModeloJuegoInicio modeloJuego) {
        this.modeloVista = modeloVista;
        this.modeloJuego = modeloJuego;
    }

    public ControladorInicio(IModeloJuegoInicio modeloJuego, JPantallaMenuPrincipal inicio) {
        this.modeloJuego = modeloJuego;
        this.inicio = inicio;
        this.modeloVista = null;
    }


    @Override
    public void onConfigChanged(String dificultad, Integer numeroJugadores, Integer puntuacionMaxima,
            Map<String, Integer> puntuaciones) {
        modeloVista.setDificultad(dificultad);
        modeloVista.setNumeroJugadores(numeroJugadores);
        modeloVista.setPuntuacionMaxima(puntuacionMaxima);
        modeloVista.setPuntuaciones(puntuaciones);
        actualizarLobby();
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void onIniciarLobby() {
        modeloJuego.enviarEventoInicioPartida(modeloVista.getNombreJugador(), modeloVista.getDificultad(),
                modeloVista.getNumeroJugadores(), modeloVista.getPuntuacionMaxima());
        JOptionPane.showMessageDialog(lobby, "Partida lista para iniciar. Jugador: " + modeloVista.getNombreJugador(),
                "Lobby", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarLobby() {
        if (lobby != null) {
            lobby.setDatosPartida(modeloVista.getDificultad(), modeloVista.getNumeroJugadores(),
                    modeloVista.getPuntuacionMaxima(), modeloVista.getNombreJugador());
        }
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
        actualizarLobby();
        lobby.setVisible(true);
    }

    public JPantallaConfigurarPartida getPantallaConfig() {
        return pantallaConfig;
    }

    public void setPantallaConfig(JPantallaConfigurarPartida pantallaConfig) {
        this.pantallaConfig = pantallaConfig;
    }
    
    
    
}
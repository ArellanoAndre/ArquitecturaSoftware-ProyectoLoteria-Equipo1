package Controlador;

import interfacesComunicacionModelo.IControladorInicio;
import ModeloVista.ModeloVistaInicio;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import Presentacion.JPantallaConfigurarPartida;
import Presentacion.JPantallaMenuPrincipal;
import interfacesComunicacionModelo.IModeloJuego;
import interfacesComunicacionModelo.IModeloVistaInicio;
import java.util.Map;

public class ControladorInicio implements IControladorInicio {

    private  IModeloVistaInicio modeloVista = null;
    private  IModeloJuego modeloJuego = null;
    private JPantallaMenuPrincipal inicio;
    private JPantallaConfigurarPartida pantallaConfig;
    private JFrameSeleccionAvatar seleccionAvatar;
    private JFrameLobby lobby;

    public ControladorInicio(IModeloVistaInicio modeloVista) {
        this.modeloVista = modeloVista;

    }

    public ControladorInicio(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
        
    }

    public void setModeloJuego(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    public ControladorInicio(ModeloVistaInicio modeloVista, IModeloJuego modeloJuego) {
        this.modeloVista = modeloVista;
        this.modeloJuego = modeloJuego;
    }

    public ControladorInicio(IModeloJuego modeloJuego, JPantallaMenuPrincipal inicio) {
        this.modeloJuego = modeloJuego;
        this.inicio = inicio;
    }


    @Override
    public void onConfigChanged(String dificultad, Integer numeroJugadores, Integer puntuacionMaxima,
          Map<String, Integer> puntuaciones) {
//        modeloVista.setDificultad(dificultad);
//        modeloVista.setNumeroJugadores(numeroJugadores);
//        modeloVista.setPuntuacionMaxima(puntuacionMaxima);
//        modeloVista.setPuntuaciones(puntuaciones);
//        actualizarLobby();
    }

    @Override
    public void onConfirmarConfig(String dificultad, int numJugadores, int puntuacionMax, Map<String, Integer> puntuaciones) {
       
        if (dificultad == null) {
            pantallaConfig.mostrarError("Selecciona un nivel de dificultad.");
            return;
        }

        if (numJugadores <= 0) {
            pantallaConfig.mostrarError("Selecciona la cantidad de jugadores.");
            return;
        }

        if ( puntuacionMax <= 0) {
            pantallaConfig.mostrarError("Ingresa una puntuación máxima válida (mayor a 0).");
            return;
        }

        if (puntuaciones == null || puntuaciones.values().stream().anyMatch(v -> v == null || v <= 0)) {
            pantallaConfig.mostrarError("Todas las puntuaciones deben ser números mayores a 0.");
            return;
        }
        
     modeloVista.CrearPartida(dificultad, numJugadores, puntuacionMax, puntuaciones);

     //   pantallaConfig.dispose();
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
    public void EnviarNombreAvatarConfirmado(String nombre,String avatar) {
    System.out.println("[ControlInicio] → Enviando UNIRSE_PARTIDA");

    if (modeloVista != null) {
        modeloVista.EnviarNombreAvatarConfirmado(nombre, avatar);
    }
}

    @Override
    public void onIniciarLobby() {
//        modeloJuego.enviarEventoInicioPartida(modeloVista.getNombreJugador(), modeloVista.getDificultad(),
//                modeloVista.getNumeroJugadores(), modeloVista.getPuntuacionMaxima());
//        JOptionPane.showMessageDialog(lobby, "Partida lista para iniciar. Jugador: " + modeloVista.getNombreJugador(),
//                "Lobby", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarLobby() {
//        if (lobby != null) {
//            lobby.setDatosPartida(modeloVista.getDificultad(), modeloVista.getNumeroJugadores(),
//                    modeloVista.getPuntuacionMaxima(), modeloVista.getNombreJugador());
//        }
    }


    

    public JPantallaConfigurarPartida getPantallaConfig() {
        return pantallaConfig;
    }

    public void setPantallaConfig(JPantallaConfigurarPartida pantallaConfig) {
        this.pantallaConfig = pantallaConfig;
    }
    
    @Override
public void onJugar() {

    System.out.println("[Inicio] Botón JUGAR presionado");

    if (modeloVista != null) {
        modeloVista.solicitarIniciarJuego();
    }
}

    @Override
    public void onConfirmarConfig() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void abrirLobby() {
        if (lobby == null) {
            lobby = new JFrameLobby();
        }
        actualizarLobby();
        lobby.setVisible(true);    }

    
    @Override
    public void abrirSeleccionAvatar() {
        if (seleccionAvatar == null) {
            seleccionAvatar = new JFrameSeleccionAvatar(); }
        
        seleccionAvatar.setModeloVista(modeloVista);
        seleccionAvatar.setVisible(true);    }

    



    
    
}
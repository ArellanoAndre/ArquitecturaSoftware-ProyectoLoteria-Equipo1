package ModeloVista;

import Interfaces.IObserver;
import EntidadesInicio.ConfiguracionVista;
import Interfaces.IObserverCambioMVCInicio;
import interfacesComunicacionModelo.IControladorInicio;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import Presentacion.JPantallaConfigurarPartida;
import Presentacion.JPantallaMenuPrincipal;
import interfacesComunicacionModelo.IModeloJuego;
import interfacesComunicacionModelo.IModeloVistaInicio;
import interfacesEntidades.IConfiguracionPartida;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

public class ModeloVistaInicio extends Subject implements IModeloVistaInicio {

    private IModeloJuego modeloJuego;
    private ConfiguracionVista configuracion;
    private JFrameSeleccionAvatar pantallaAvatar;
    private JFrameLobby pantallaLobby;
    private IControladorInicio controlador;
    private JPantallaConfigurarPartida pantallaConfig;
    private JPantallaMenuPrincipal MenuPrincipal;
    private String jugador;
    private int id;
    private List<IObserverCambioMVCInicio> observersCambioMVC = new ArrayList<>();

    public ModeloVistaInicio() {
        // NO LLAMAR notifyObservers() AQUÍ
    }

    public JPantallaMenuPrincipal getMenuPrincipal() {
        return MenuPrincipal;
    }

    public void setMenuPrincipal(JPantallaMenuPrincipal MenuPrincipal) {
        this.MenuPrincipal = MenuPrincipal;
    }

    public void setControlador(IControladorInicio controlador) {
        this.controlador = controlador;
    }

    @Override
    public void setModeloJuego(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    @Override
    public void setPantallaAvatar(JFrame p) {
        this.pantallaAvatar = (JFrameSeleccionAvatar) p;
    }

    @Override
    public void setPantallaLobby(JFrame l) {
        this.pantallaLobby = (JFrameLobby) l;
    }

    @Override
    public void EnviarNombreAvatarConfirmado(int id, String nombre, String avatar) {
        System.out.println("[ModeloVista] → Solicitando UNIRSE_PARTIDA");
        this.jugador = nombre;
        if (modeloJuego != null) {
            modeloJuego.enviarNombreAvatarConfirmado(id, nombre, avatar);
        }
    }

    @Override
    public void enviarTarjetaSeleccionada(String tarjetaRuta) {
        if (modeloJuego != null) {
            modeloJuego.enviarTarjetaSeleccionada(tarjetaRuta);
        }
    }

    @Override
    public void actualizarConfiguracion(IConfiguracionPartida config) {

        if (config == null) {
            System.err.println("[ModeloVistaInicio] Configuración recibida es null");
            return;
        }

        if (this.configuracion == null) {
            this.configuracion = new ConfiguracionVista();
        }

        configuracion.setDatos(
                config.getDificultad(),
                config.getJugadores(),
                config.getNumeroRondas(),
                config.getImagenesTarjetas(),
                config.getNumJugadores()
        );

        System.out.println("[ModeloVistaInicio] Actualizando configuración en Vista...");
        System.out.println("Instancia ModeloVistaInicio = " + this);
        System.out.println("Observers registrados = " + observers.size());

        // Aquí se despiertan JFrameLobby y/o otros
        notifyObservers();
    }

    public ConfiguracionVista getConfiguracion() {
        return configuracion;
    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public JFrameSeleccionAvatar getPantallaAvatar() {
        return pantallaAvatar;
    }

    public JFrameLobby getPantallaLobby() {
        return pantallaLobby;
    }

    @Override
    public IControladorInicio getControlador() {
        return this.controlador;
    }

    @Override
    public void solicitarIniciarJuego() {

        System.out.println("[ModeloVistaInicio] → Enviando solicitud de JUGAR al ModeloJuego");

        if (modeloJuego != null) {
            modeloJuego.enviarEventoJugar();
        } else {
            System.err.println("[ModeloVistaInicio] ERROR: modeloJuego es null");
        }
    }

    @Override
    public void abrirPantallaConfig(int id) {
        pantallaAvatar.setId(id); pantallaAvatar.setVisible(false);
        pantallaLobby.setVisible(false);
        pantallaConfig.setVisible(true);
        MenuPrincipal.setVisible(false);
        
        
    }

    @Override
    public void abrirPantallaAvatar(int id) {
        pantallaConfig.setVisible(false);
        pantallaLobby.setVisible(false);
        pantallaAvatar.setId(id); pantallaAvatar.setVisible(true);
    }

    public JPantallaConfigurarPartida getPantallaConfig() {
        return pantallaConfig;
    }

    @Override
    public void setPantallaConfig(JFrame pantallaConfig) {
        this.pantallaConfig = (JPantallaConfigurarPartida) pantallaConfig;
    }

    @Override
    public void setMenuPrincipal(JFrame MenuPrincipal) {
        this.MenuPrincipal = (JPantallaMenuPrincipal) MenuPrincipal;
    }

    @Override
    public void CrearPartida(String dificultad, int numJugadores, int numRondas,
            Map<String, Integer> puntuaciones) {

        System.out.println("[ModeloVistaInicio] CrearPartida() llamado");
        // ========== 1) ENVIAR EVENTO AL MODELO JUEGO ==========
        if (modeloJuego != null) {
            modeloJuego.enviarEventoConfigurarPartida(dificultad, numJugadores, numRondas, puntuaciones);

        }

        // ========== 2) MOSTRAR SELECCION DE AVATAR ==========
        if (controlador != null) {
            abrirPantallaAvatar(id);
        }
    }

    @Override
    public void abrirSeleccionAvatar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cambiarMVC() {
        modeloJuego.enviarEventoCambiarMVC();
    }

    @Override
    public void notificarCambioMVC() {
        System.out.println("Notificando a pantalla de juego");
        List<IObserverCambioMVCInicio> copiaObservers;
        
        synchronized (observersCambioMVC) {
            copiaObservers = new ArrayList<>(observersCambioMVC);
        }
        for (IObserverCambioMVCInicio o : copiaObservers) {
            o.updateCambioMVC();
        }
    }

    public void addObserverCambioMVC(IObserverCambioMVCInicio o) {
        observersCambioMVC.add(o);
    }
    
    @Override
    public void abrirPantallaMenu(int id) {
        pantallaAvatar.setVisible(false);
        pantallaLobby.setVisible(false);
        pantallaConfig.setVisible(false);
        MenuPrincipal.setVisible(true);
        this.id = id;
    }

}

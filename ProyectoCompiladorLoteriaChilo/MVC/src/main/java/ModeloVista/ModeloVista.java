package ModeloVista;

import java.util.ArrayList;
import java.util.List;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import Interfaces.IModeloVista;
import Interfaces.Observer;
import interfacesComunicacionModelo.IModeloJuego;
import interfacesComunicacionModelo.IControlVistaMVC_Juego;

/**
 * Clase que representa el modeloVista del juego. Actúa como intermediario entre
 * la lógica del juego (modelo) y la presentación (vista), utilizando el patrón
 * Observer.
 */
public class ModeloVista implements IModeloVista {

    private CartaVista cartaCantada;
    private int marcador;
    private JugadorVista jugadorPrincipal;
    private List<JugadorVista> jugadoresSecundarios;
    private List<Observer> observers = new ArrayList<>();

    private IModeloJuego modeloJuego;
    private IControlVistaMVC_Juego controlVista;

    // para el estado de la ronda y partida
    private boolean finDeRonda = false;
    private boolean juegoTerminado = false;
    private String ganadorRonda;

    private int numRonda;
    private boolean host = false;
    private List<JugadorVista> rankingFinal;

    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     * Constructor vacío del modeloVista.
     */
    public ModeloVista() {
    }

    public void setControlVista(IControlVistaMVC_Juego controlVista) {
        this.controlVista = controlVista;
    }

    /**
     * Asigna el modelo de juego con el cual se comunica.
     */
    @Override
    public void setModeloJuego(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    /**
     * Recibe una nueva carta cantada desde el modelo del juego y notifica a los
     * observadores (la vista) para actualizar la pantalla.
     */
    @Override
    public void setCartaCantada(CartaVista carta) {
        cartaCantada = carta;
        
        // checar si esta bien o mal hacer esto
        // si llega una carta nueva, entonces la ronda de espera termino
        // o se puede hacer todo en pantallajuego nose
        if (this.finDeRonda) {
            this.finDeRonda = false; 
        }
        notificar();
    }

    /**
     * @return la carta cantada actual.
     */
    @Override
    public CartaVista getCartaCantada() {
        return cartaCantada;
    }

    /**
     * @return el marcador actual del jugador.
     */
    @Override
    public int getMarcador() {
        return marcador;
    }

    /**
     * Actualiza el marcador y notifica a los observadores.
     *
     * @param marcador nuevo valor del marcador.
     */
    @Override
    public void setMarcador(int marcador) {
        this.marcador = marcador;
        notificar();
    }

    /**
     * @return el jugador principal.
     */
    @Override
    public JugadorVista getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    /**
     * Asigna el jugador principal.
     */
    @Override
    public void setJugadorPrincipal(JugadorVista jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    /**
     * Actualiza las casillas marcadas del jugador principal y notifica a los
     * observadores.
     *
     * @param casillas arreglo con el estado de las casillas.
     */
    @Override
    public void actualizarTarjetaJugadorP(boolean[] casillas) {
        jugadorPrincipal.getTarjeta().setMarcadas(casillas);
        notificar();
    }

    /**
     * @return lista de jugadores secundarios.
     */
    @Override
    public List<JugadorVista> getJugadoresSecundarios() {
        return jugadoresSecundarios;
    }

    /**
     * Asigna la lista de jugadores secundarios y notifica a los observadores.
     */
    @Override
    public void setJugadoresSecundarios(List<JugadorVista> jugadoresSecundarios) {
        this.jugadoresSecundarios = jugadoresSecundarios;
        notificar();
    }

    /**
     * Llamado por el controlador cuando se selecciona una carta. Envía la
     * selección al modelo del juego para su validación.
     *
     * @param pos posición de la carta seleccionada.
     */
    @Override
    public void seleccionarCarta(int pos) {
        // VER SI ES NECESARIO ENVIAR STRING CON NOMBRE CARTA
        // Y SI ES AGREGAR ESA FUNCIONALIDAD
        modeloJuego.EnviarEventoCartaSeleccionada(pos, jugadorPrincipal.getNumJugador());
    }

    /**
     * Agrega un observador (por lo general, la vista principal del juego).
     *
     * @param o objeto que implementa la interfaz Observer.
     */
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Notifica a todos los observadores que hubo un cambio para que actualicen
     * su interfaz.
     */
    @Override
    public void notificar() {
        for (Observer o : observers) {
            o.update();
        }
    }

    // ----------------------- metodos de la ronda ----------------------------------
    @Override
    public void setDatosFinRonda(int ronda, String ganador) {

        this.numRonda = ronda;
        this.ganadorRonda = ganador;
        this.finDeRonda = true;
        notificar();
    }

    @Override
    public boolean isFinDeRonda() {

        return finDeRonda;
    }

    @Override
    public String getGanadorRonda() {

        return ganadorRonda;
    }

    @Override
    public int getNumRonda() {
        return numRonda;
    }

    @Override
    public void limpiarEstadoRonda() {

        this.finDeRonda = false;
    }

    @Override
    public void setRankingFinal(List<JugadorVista> ranking) {

        this.rankingFinal = ranking;
        this.juegoTerminado = true;
        notificar();
    }

    @Override
    public boolean isJuegoTerminado() {

        return juegoTerminado;
    }

    @Override
    public List<JugadorVista> getListaRanking() {

        return rankingFinal;
    }

    @Override
    public boolean isHost() {

        return host;
    }

    @Override
    public void solicitarEnvioCantarLoteria() {

        if (modeloJuego != null) {
            modeloJuego.EnviarEventoCantarLoteria();
        }
    }

    @Override
    public void solicitarEnvioSiguienteRonda() {

        if (modeloJuego != null) {
            modeloJuego.EnviarEventoIniciarSiguienteRonda();
        }
    }

    public void reiniciarTableroUsuario() {
        if (jugadorPrincipal != null && jugadorPrincipal.getTarjeta() != null) {

            boolean[] vacias = new boolean[16];
            jugadorPrincipal.getTarjeta().setMarcadas(vacias);

            notificar();
        }
    }

    @Override
    public void reiniciarTableroJugador() {
        if (jugadorPrincipal != null && jugadorPrincipal.getTarjeta() != null) {

            boolean[] vacias = new boolean[16];
            jugadorPrincipal.getTarjeta().setMarcadas(vacias);

            notificar();
        }
    }

}

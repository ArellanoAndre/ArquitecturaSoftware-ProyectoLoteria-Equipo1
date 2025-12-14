package ModeloVista;

import java.util.ArrayList;
import java.util.List;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import Interfaces.IModeloVista;
import Interfaces.IObserverCambioMVCJuego;
import Interfaces.Observer;
import interfacesComunicacionModelo.IModeloJuego;
import interfacesComunicacionModelo.IControlVistaMVC_Juego;
import java.lang.reflect.Array;

/**
 * Clase que representa el modeloVista del juego. Actúa como intermediario entre
 * la lógica del juego (modelo) y la presentación (vista), utilizando el patrón
 * Observer.
 */
public class ModeloVistaJuego implements IModeloVista {

    private CartaVista cartaCantada;
    private int marcador;
    private JugadorVista jugadorPrincipal;
    private List<JugadorVista> jugadoresSecundarios;
    private List<Observer> observers = new ArrayList<>();
    private List<IObserverCambioMVCJuego> observersCambioMVC = new ArrayList<>();

    private IModeloJuego modeloJuego;
    private IControlVistaMVC_Juego controlVista;

    // para el estado de la ronda y partida
    private boolean finDeRonda = false;
    private boolean finDeRondaBaraja = false;
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
    public ModeloVistaJuego() {
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
    
    public JugadorVista getJugadorPorNum(int numJugador) {
    if (jugadoresSecundarios != null) {
        for (JugadorVista j : jugadoresSecundarios) {
            if (j.getNumJugador() == numJugador) {
                return j;
            }
        }
    }

    // También busca al jugador principal si aplica
    if (jugadorPrincipal != null && jugadorPrincipal.getNumJugador() == numJugador) {
        return jugadorPrincipal;
    }

    return null;
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
        modeloJuego.enviarEventoCartaSeleccionada(pos, jugadorPrincipal.getNumJugador());
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

    public void addObserverCambioMVC(IObserverCambioMVCJuego o) {
        observersCambioMVC.add(o);
    }

    /**
     * Notifica a todos los observadores que hubo un cambio para que actualicen
     * su interfaz.
     */
    @Override
    public void notificar() {
        System.out.println("*********************************");
        System.out.println("**LISTA JUGADORES SECUNDARIOS**");

        if (jugadoresSecundarios == null) {
            System.out.println("jugadoresSecundarios ES NULL");
        } else if (jugadoresSecundarios.isEmpty()) {
            System.out.println("jugadoresSecundarios está VACÍA");
        } else {
            System.out.println("Total: " + jugadoresSecundarios.size());
            int i = 1;
            for (JugadorVista jv : jugadoresSecundarios) {
                System.out.println(
                        "  #" + (i++)
                        + " → ID=" + jv.getNumJugador()
                        + ", Nombre=" + jv.getNombre()
                        + ", Avatar=" + jv.getRutaAvatar()
                );
            }
        }

        System.out.println("*********************************\n");

        List<Observer> copiaObservers;

        synchronized (observers) {
            copiaObservers = new ArrayList<>(observers);
        }
        for (Observer o : copiaObservers) {
            o.update();
        }
    }

    @Override
    public void reiniciarEstadoFinPartida() {
        juegoTerminado = false;
        if (rankingFinal != null) {
            rankingFinal.clear();
        }
    }

    @Override
    public void reiniciarClienteCompleto() {
        reiniciarEstadoFinPartida();
        reiniciarTableroJugador();
        if (jugadoresSecundarios != null) {
            jugadoresSecundarios.clear();
            notificar();
        }
        if (modeloJuego != null) {
            modeloJuego.reiniciarCliente();
        }
    }

    @Override
    public void notificarCambioMVC() {
        System.out.println("Notificando a pantalla de juego");
        reiniciarEstadoFinPartida();
        List<IObserverCambioMVCJuego> copiaObservers;

        synchronized (observersCambioMVC) {
            copiaObservers = new ArrayList<>(observersCambioMVC);
        }
        for (IObserverCambioMVCJuego o : copiaObservers) {
            o.updateCambioMVC();
        }
    }

    // ----------------------- metodos de la ronda ----------------------------------
    @Override
    public void setDatosFinRonda(int ronda, String ganador) {

        this.numRonda = ronda;
        this.ganadorRonda = ganador;
        finDeRonda = true;
        notificar();
        finDeRonda = false;
    }

    @Override
    public void setDatosFinRondaBaraja() {
        finDeRondaBaraja = true;
        notificar();
        finDeRondaBaraja = false;
    }

    @Override
    public boolean isFinDeRonda() {

        return finDeRonda;
    }

    @Override
    public boolean isFinDeRondaBaraja() {

        return finDeRondaBaraja;
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
        this.finDeRondaBaraja = false;

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

        return modeloJuego.isHost();
    }

    @Override
    public void solicitarEnvioCantarLoteria() {

        if (modeloJuego != null) {
            modeloJuego.enviarEventoCantarLoteria();
        }
    }

    @Override
    public void solicitarEnvioSiguienteRonda() {

        if (modeloJuego != null) {
            modeloJuego.enviarEventoIniciarSiguienteRonda();
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

        // limpieza interna 
        if (modeloJuego != null) {
            modeloJuego.limpiarEntidadesJuego();
        }

        // principal
        if (jugadorPrincipal != null && jugadorPrincipal.getTarjeta() != null) {

            boolean[] susCasillas = jugadorPrincipal.getTarjeta().getMarcadas();
            if (susCasillas != null) {
                java.util.Arrays.fill(susCasillas, false);
            }
        }

        // secundarios
        if (jugadoresSecundarios != null) {
            for (JugadorVista j : jugadoresSecundarios) {
                if (j != null && j.getTarjeta() != null) {

                    boolean[] susCasillas = j.getTarjeta().getMarcadas();
                    if (susCasillas != null) {
                        java.util.Arrays.fill(susCasillas, false);
                    }
                }
            }
        }

        notificar();
    }

    @Override
    public void cambiarMVC() {
        modeloJuego.enviarEventoCambiarMVC();
    }
}

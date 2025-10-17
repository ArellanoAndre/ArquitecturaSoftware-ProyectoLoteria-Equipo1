package ModeloVista;

import java.util.ArrayList;
import java.util.List;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import Observer.IModeloJuego;
import Observer.IModeloVista;
import Observer.Observer;

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

    /**
     * Constructor vacío del modeloVista.
     */
    public ModeloVista() {
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
        modeloJuego.verificarCarta(pos);
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

}

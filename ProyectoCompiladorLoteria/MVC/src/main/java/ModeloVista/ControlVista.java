package ModeloVista;

import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import ModeloVista.entidadesVista.TarjetaVista;
import Observer.IControlVista;
import Observer.IModeloVista;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que actúa como controlador entre el modelo del juego y la vista.
 * Convierte los datos del modelo (jugadores y cartas) en sus versiones visuales
 * para mostrarlos en pantalla.
 */
public class ControlVista implements IControlVista {

    private IModeloVista modeloVista;

    /**
     * Constructor que recibe el modeloVista con el que se comunicará.
     *
     * @param modeloVista instancia que conecta el modelo y la presentación.
     */
    public ControlVista(IModeloVista modeloVista) {
        this.modeloVista = modeloVista;
    }

    /**
     * Asigna el jugador principal en la vista, creando su versión visual con su
     * tarjeta y avatar.
     *
     * @param jugador jugador principal del modelo.
     */
    @Override
    public void setJugadorPrincipal(Jugador jugador) {
        TarjetaVista tarjeta = new TarjetaVista(jugador.getTarjeta().getMarcadas(), jugador.getTarjeta().getImg());
        JugadorVista jugadorP = new JugadorVista(jugador.getNombre(), tarjeta, jugador.getNumJugador());
        jugadorP.setRutaAvatar("/img/Avatares/user" + jugador.getNumJugador() + ".png");
        modeloVista.setJugadorPrincipal(jugadorP);
    }

    /**
     * Actualiza la tarjeta del jugador principal en la vista.
     *
     * @param marcadas arreglo que indica las casillas marcadas.
     */
    @Override
    public void actualizarTarjetaJugadorPrincipal(boolean[] marcadas) {
        modeloVista.actualizarTarjetaJugadorP(marcadas);
    }

    /**
     * Convierte la lista de jugadores del modelo en objetos visuales y los
     * asigna a la vista.
     *
     * @param jugadores lista de jugadores del modelo.
     */
    @Override
    public void setJugadoresSecundarios(List<Jugador> jugadores) {
        List<JugadorVista> jugadoresV = new ArrayList<>();
        for (Jugador jugador : jugadores) {
            TarjetaVista tarjeta = new TarjetaVista(jugador.getTarjeta().getMarcadas(), jugador.getTarjeta().getImg());
            JugadorVista jugadorSV = new JugadorVista(jugador.getNombre(), tarjeta, jugador.getNumJugador());
            jugadorSV.setRutaAvatar("/img/Avatares/user" + jugador.getNumJugador() + ".png");
            jugadoresV.add(jugadorSV);
        }
        modeloVista.setJugadoresSecundarios(jugadoresV);
    }

    /**
     * Actualiza la carta cantada actual en la vista, creando su representación
     * visual con la imagen correspondiente.
     *
     * @param cartaActual carta que fue cantada en el modelo del juego.
     */
    @Override
    public void actualizarCartaCantada(Carta cartaActual) {
        CartaVista cv = new CartaVista(
                cartaActual.getNumCarta(),
                cartaActual.getNombreCarta(),
                "/img/Cartas/" + cartaActual.getNumCarta() + ".jpeg"
        );

        modeloVista.setCartaCantada(cv);
    }

}

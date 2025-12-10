package Controlador;

import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import ModeloVista.entidadesVista.TarjetaVista;
import Interfaces.IModeloVista;

import interfacesEntidades.ICarta;
import interfacesEntidades.IJugador;
import java.util.ArrayList;
import java.util.List;
import interfacesComunicacionModelo.IControlVistaMVC_Juego;

/**
 * Clase que actúa como controlador entre el modelo del juego y la vista.
 * Convierte los datos del modelo (jugadores y cartas) en sus versiones visuales
 * para mostrarlos en pantalla.
 */
public class ControlVista implements IControlVistaMVC_Juego {

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
    public void setJugadorPrincipal(IJugador jugador) {
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
    // EN ControlVista.java
    @Override
    public void setJugadoresSecundarios(List<IJugador> jugadoresModelo) {
        if (jugadoresModelo == null) {
            return;
        }

        // 1. Obtenemos la lista ACTUAL de la vista (la que se está pintando)
        List<JugadorVista> actualesEnVista = modeloVista.getJugadoresSecundarios();
        if (actualesEnVista == null) {
            actualesEnVista = new ArrayList<>();
        }

        // 2. Lista temporal para guardar los nuevos si no existen
        List<JugadorVista> listaFinal = new ArrayList<>();

        for (IJugador jModelo : jugadoresModelo) {
            if (jModelo == null || jModelo.getTarjeta() == null) {
                continue;
            }

            JugadorVista jugadorVistaExistente = null;
            for (JugadorVista jugadorVista : actualesEnVista) {

                if (jugadorVista.getNumJugador() == jModelo.getNumJugador()) {
                    jugadorVistaExistente = jugadorVista;
                    break;
                }
            }

            if (jugadorVistaExistente != null) {

                jugadorVistaExistente.getTarjeta().setMarcadas(jModelo.getTarjeta().getMarcadas());

                // Agregamos el existente a la lista final para mantener el orden
                listaFinal.add(jugadorVistaExistente);
            } else {

                TarjetaVista tarjeta = new TarjetaVista(jModelo.getTarjeta().getMarcadas(), jModelo.getTarjeta().getImg());
                JugadorVista nuevo = new JugadorVista(jModelo.getNombre(), tarjeta, jModelo.getNumJugador());

                String ruta = "/img/Avatares/user" + jModelo.getNumJugador() + ".png";
                if (jModelo.getAvatar() != null && !jModelo.getAvatar().startsWith("/img")) {
                    ruta = jModelo.getAvatar();
                }
                nuevo.setRutaAvatar(ruta);

                listaFinal.add(nuevo);
            }
        }

        modeloVista.setJugadoresSecundarios(listaFinal);
    }

    /**
     * Actualiza la carta cantada actual en la vista, creando su representación
     * visual con la imagen correspondiente.
     *
     * @param cartaActual carta que fue cantada en el modelo del juego.
     */
    @Override
    public void actualizarCartaCantada(ICarta cartaActual) {

        CartaVista cv = new CartaVista(
                cartaActual.getNumCarta(),
                cartaActual.getNombreCarta(),
                "/img/Cartas/" + cartaActual.getNumCarta() + ".jpeg"
        );

        modeloVista.setCartaCantada(cv);

    }

    @Override
    public void mostrarMensajeFinRonda(int ronda, String ganador
    ) {

        modeloVista.setDatosFinRonda(ronda, ganador);
    }

    @Override
    public void procesarFinPartida(List<IJugador> ranking
    ) {

        List<JugadorVista> rankingVisual = new ArrayList<>();

        for (IJugador jugador : ranking) {
            // Creamos una tarjeta vacía o dummy para la vista del ranking (no necesitamos ver las casillas ahí)
            TarjetaVista tarjetaDummy = new TarjetaVista(new boolean[16], "dummy.png");

            JugadorVista jugadorVista = new JugadorVista(jugador.getNombre(), tarjetaDummy, jugador.getNumJugador());
            jugadorVista.setPuntaje(jugador.getPuntaje());

            // Asignamos el avatar (que viene del servidor o se construye la ruta)
            // Si el servidor manda "avatar1.png", construimos la ruta completa si es necesario
            String avatar = jugador.getAvatar();
            if (avatar != null && !avatar.startsWith("/img")) {

                jugadorVista.setRutaAvatar("/img/Avatares/user" + jugador.getNumJugador() + ".png");
            } else {
                jugadorVista.setRutaAvatar(avatar);
            }

            rankingVisual.add(jugadorVista);
        }

        modeloVista.setRankingFinal(rankingVisual);
    }

    @Override
    public void solicitarSiguienteRonda() {

        modeloVista.solicitarEnvioSiguienteRonda();
    }

    @Override
    public void solicitarIntentoLoteria() {

        modeloVista.solicitarEnvioCantarLoteria();
    }

}

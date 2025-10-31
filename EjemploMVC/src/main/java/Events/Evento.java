package Events;

import Events.EnumTipoEvento;
import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Jugador;
import java.util.List;

public class Evento {

    private EnumTipoEvento tipo;

    // Identificación y datos del jugador
    private int idJugador;         // ID numérico (1, 2, 3...)
    private String nombreJugador;  // Nombre para CONECTAR

    // Datos de juego (C -> S)
    private int posicion;          // Para SELECCIONAR_CARTA

    // Datos de juego (S -> C)
    private Carta cartaCantada;    // Para CANTAR_LOTERIA
    private boolean[] marcadas;    // Para ACTUALIZAR_TABLERO
    private Jugador jugador;       // Para INICIAR_PARTIDA (tu jugador)
    private List<Jugador> listaJugadores; // Para INICIAR_PARTIDA (oponentes)
    private String mensaje; // mensaje para responder a un canto de victoria falso al que lo hizo

    // --- CONSTRUCTORES SOBRECARGADOS ---
    /**
     * Constructor para CONECTAR_JUGADOR (C -> S)
     */
    public Evento(EnumTipoEvento tipo, String nombreJugador) {
        this.tipo = tipo;
        this.nombreJugador = nombreJugador;
    }

    public Evento(EnumTipoEvento tipo, int idJugador) {
        this.tipo = tipo;
        this.idJugador = idJugador;
    }

    public Evento(EnumTipoEvento tipo, int idJugador, String nombreJugador) {
        this.tipo = tipo;
        this.idJugador = idJugador;
        this.nombreJugador = nombreJugador;
    }

    public Evento(EnumTipoEvento tipo, Jugador jugador) {
        this.tipo = tipo;
        this.jugador = jugador;
    }

    /**
     * Constructor para SELECCIONAR_CARTA (C -> S)
     */
    public Evento(EnumTipoEvento tipo, int idJugador, int posicion) {
        this.tipo = tipo;
        this.idJugador = idJugador; // El cliente debe saber su ID
        this.posicion = posicion;
    }

    /**
     * Constructor para CANTAR_LOTERIA (S -> C)
     */
    public Evento(EnumTipoEvento tipo, Carta cartaCantada) {
        this.tipo = tipo;
        this.cartaCantada = cartaCantada;
    }

    /**
     * Constructor para ACTUALIZAR_TABLERO (S -> C)
     */
    public Evento(EnumTipoEvento tipo, int idJugador, boolean[] marcadas) {
        this.tipo = tipo;
        this.idJugador = idJugador; // El ID del jugador que marcó
        this.marcadas = marcadas;
    }

    /**
     * Constructor para INICIAR_PARTIDA (S -> C, privado a un jugador)
     */
    public Evento(EnumTipoEvento tipo, Jugador tuJugador, List<Jugador> oponentes) {
        this.tipo = tipo;
        this.jugador = tuJugador;
        this.listaJugadores = oponentes;
    }

    

    public EnumTipoEvento getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoEvento tipo) {
        this.tipo = tipo;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Carta getCartaCantada() {
        return cartaCantada;
    }

    public void setCartaCantada(Carta cartaCantada) {
        this.cartaCantada = cartaCantada;
    }

    public boolean[] getMarcadas() {
        return marcadas;
    }

    public void setMarcadas(boolean[] marcadas) {
        this.marcadas = marcadas;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public List<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaJugadores(List<Jugador> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }
}

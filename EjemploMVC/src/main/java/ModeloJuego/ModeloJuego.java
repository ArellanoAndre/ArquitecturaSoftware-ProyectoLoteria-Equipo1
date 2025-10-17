package ModeloJuego;

import ModeloJuego.entidades.Jugador;
import ModeloJuego.entidades.Carta;
import Observer.IControlVista;
import Observer.IModeloJuego;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;

public class ModeloJuego implements IModeloJuego{

    private List<Carta> mazo;
    private Jugador jugadorPrincipal;
    private List<Jugador> jugadoresSecundarios;
    private Carta cartaActual;
    private int marcador = 0;
    private int contador = 0;
    private Timer timer;
    private IControlVista controlVista;
    private IModeloJuego modeloJuegoSecundario;

    public ModeloJuego(IControlVista controlVista, Jugador jugador, List<Jugador> jugadores) {
        this.controlVista = controlVista;
        this.mazo = crearMazo();
        barajear();
        this.jugadorPrincipal = jugador;
        this.jugadoresSecundarios = jugadores;
        controlVista.setJugadorPrincipal(jugador);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
    }

    //Inicia el juego enviandole una carta al modeloVista y despues lo vuelve a ejecutar repetidamente segun el tiempo indicado.
    @Override
    public void iniciarJuego() {
        siguienteCarta();

        // Cada segundo cambia la carta cantada
        timer = new Timer(2500, e -> siguienteCarta());
        timer.start();
    }

    // Obtiene una nueva carta random del 1 al 3 y se la envia al modelo vista
    @Override
    public void siguienteCarta() {
        cartaActual = mazo.get(contador);
        controlVista.actualizarCartaCantada(cartaActual);
        contador++;

        if (contador == 54) {
            contador = 0;
        }
    }

    @Override
    public void verificarCarta(int casillaSeleccionada) {
        // Ajustar la posición a índice (de 1-16 a 0-15)
        int indice = casillaSeleccionada - 1;

        // Obtener el arreglo de casillas de la tarjeta
        int[] casillas = jugadorPrincipal.getTarjeta().getCasillas();

        // Verificar que el índice sea válido
        if (indice >= 0 && indice < casillas.length) {
            // Obtener el estado de las casillas marcadas
            boolean[] marcadas = jugadorPrincipal.getTarjeta().getMarcadas();

            // Validar que la casilla no esté ya marcada
            if (!marcadas[indice]) {
                // Comparar el número de la carta cantada con el valor en la posición seleccionada
                if (cartaActual != null && casillas[indice] == cartaActual.getNumCarta()) {
                    jugadorPrincipal.getTarjeta().marcarCasilla(casillaSeleccionada - 1);
                    controlVista.actualizarTarjetaJugadorPrincipal(jugadorPrincipal.getTarjeta().getMarcadas());
                    //Mandar llamada al control secundario
                    modeloJuegoSecundario.actualizarJugadorSecundario(indice, jugadorPrincipal.getNumJugador());
                    System.out.println("ModeloJuego.si");
                } else {
                    System.out.println("ModeloJuego.no - No coincide la carta cantada (" + cartaActual.getNumCarta() + ") con la casilla " + casillas[indice]);
                }
            } else {
                System.out.println("ModeloJuego.skip - Casilla " + casillaSeleccionada + " ya está marcada");
            }
        } else {
            System.out.println("ModeloJuego.error - Índice inválido: " + indice);
        }
    }

    @Override
    public void actualizarJugadorSecundario(int casilla, int numJugador) {
        for (Jugador jugador : jugadoresSecundarios) {
            if (numJugador == jugador.getNumJugador()) {
                jugador.getTarjeta().marcarCasilla(casilla);
            }
        }
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
    }

    //Regresa la carta actual del juego
    @Override
    public Carta getCartaActual() {
        return cartaActual;
    }

    //Regresa el marcador actual del jugador
    @Override
    public int getMarcador() {
        return marcador;
    }

    @Override
    public IModeloJuego getModeloJuegoSecundario() {
        return modeloJuegoSecundario;
    }

    @Override
    public void setModeloJuegoSecundario(IModeloJuego modeloJuegoSecundario) {
        this.modeloJuegoSecundario = modeloJuegoSecundario;

        // compartir mazo a modelo secundario
        if (this.mazo != null && !this.mazo.isEmpty()) {
            modeloJuegoSecundario.setMazo(this.mazo);
        } else if (modeloJuegoSecundario.getMazo() != null) {
            this.mazo = modeloJuegoSecundario.getMazo();
        }
    }

    // Getter y Setter del mazo
    @Override
    public List<Carta> getMazo() {
        return mazo;
    }

    @Override
    public void setMazo(List<Carta> mazo) {
        this.mazo = mazo;
    }

    @Override
    public List<Carta> crearMazo() {
        String[] nombres = {
            "El Gallo", "El Diablito", "La Dama", "El Catrín", "El Paraguas", "La Sirena",
            "La Escalera", "La Botella", "El Barril", "El Árbol", "El Melón", "El Valiente",
            "El Gorrito", "La Muerte", "La Pera", "La Bandera", "El Bandolón", "El Violoncello",
            "La Garza", "El Pájaro", "La Mano", "La Bota", "La Luna", "El Cotorro",
            "El Borracho", "El Negrito", "El Corazón", "La Sandía", "El Tambor", "El Camarón",
            "Las Jaras", "El Músico", "La Araña", "El Soldado", "La Estrella", "El Cazo",
            "El Mundo", "El Apache", "El Nopal", "El Alacrán", "La Rosa", "La Calavera",
            "La Campana", "El Cantarito", "El Venado", "El Sol", "La Corona", "La Chalupa",
            "El Pino", "El Pescado", "La Palma", "La Maceta", "El Arpa", "La Rana"
        };

        List<Carta> mazo = new ArrayList<>();
        for (int i = 0; i < nombres.length; i++) {
            mazo.add(new Carta(i + 1, nombres[i]));
        }
        return mazo;

    }

    @Override
    public void barajear() {
        Collections.shuffle(mazo);
    }
}

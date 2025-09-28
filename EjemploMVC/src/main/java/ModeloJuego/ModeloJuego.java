package ModeloJuego;

import ModeloJuego.entidades.Jugador;
import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Tarjeta;
import ModeloVista.ModeloVista;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class ModeloJuego {

    private List<Carta> mazo;
    private Jugador jugador;
    private Carta cartaActual;
    private int marcador = 0;
    private int contador = 0;
    private ModeloVista modeloVista;
    private Timer timer;

    public ModeloJuego(ModeloVista modeloVista) {
        this.modeloVista = modeloVista;
        mazo = crearMazo();
        jugador = crearJugador(mazo);
        barajear();
    }

    //Inicia el juego enviandole una carta al modeloVista y despues lo vuelve a ejecutar repetidamente segun el tiempo indicado.
    public void iniciarJuego() {
        siguienteCarta();

        // Cada segundo cambia la carta cantada
        timer = new Timer(2500, e -> siguienteCarta());
        timer.start();
    }

    // Obtiene una nueva carta random del 1 al 3 y se la envia al modelo vista
    private void siguienteCarta() {
        cartaActual = mazo.get(contador);
        modeloVista.setCartaCantada(cartaActual);
        contador++;

        if (contador == 3) {
            contador = 0;
        }
    }

    // Validación de la carta seleccionada y actualización del marcador
    public void verificarCarta(int cartaSeleccionada) {
        int casilla = jugador.getTarjeta().getCasilla(cartaSeleccionada);
        if (casilla == cartaActual.getNumCarta()){
            marcador++;
            jugador.getTarjeta().marcarCasilla(cartaSeleccionada);
            System.out.println(Arrays.toString(jugador.getTarjeta().getMarcadas()));
        }
        // Actualiza ModeloVista con estado actual
        modeloVista.setMarcador(marcador);
    }

    //Regresa la carta actual del juego
    public Carta getCartaActual() {
        return cartaActual;
    }

    //Regresa el marcador actual del jugador
    public int getMarcador() {
        return marcador;
    }

    private List<Carta> crearMazo() {
//        String[] nombres = {
//            "El gallo", "El diablito", "La dama", "El catrín", "El paraguas", "La sirena",
//            "La escalera", "La botella", "El barril", "El árbol", "El melón", "El valiente",
//            "El gorrito", "La muerte", "La pera", "La bandera", "El bandolón", "El violoncello",
//            "La garza", "El pájaro", "La mano", "La bota", "La luna", "El cotorro",
//            "El borracho", "El negrito", "El corazón", "La sandía", "El tambor", "El camarón",
//            "Las jaras", "El músico", "La araña", "El soldado", "La estrella", "El cazo",
//            "El mundo", "El apache", "El nopal", "El alacrán", "La rosa", "La calavera",
//            "La campana", "El cantarito", "El venado", "El sol", "La corona", "La chalupa",
//            "El pino", "El pescado", "La palma", "La maceta", "El arpa", "La rana"
//        };

        String[] nombres = {
            "El gallo", "El diablito", "La dama"};

        List<Carta> mazo = new ArrayList<>();
        for (int i = 0; i < nombres.length; i++) {
            mazo.add(new Carta(i + 1, nombres[i]));
        }
        return mazo;
    }

    private void barajear() {
        Collections.shuffle(mazo);
    }

    private Jugador crearJugador(List<Carta> mazo) {
        int[] casillas = {mazo.get(0).getNumCarta(), mazo.get(1).getNumCarta(), mazo.get(2).getNumCarta()};

        Tarjeta tarjetaPrueba = new Tarjeta(casillas);
        Jugador jugador1 = new Jugador("Rodri", tarjetaPrueba, marcador);
        return jugador1;
    }

}

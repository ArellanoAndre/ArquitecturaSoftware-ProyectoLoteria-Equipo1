package ModeloJuego;

import ModeloJuego.entidades.Jugador;
import ModeloJuego.entidades.Carta;
import ModeloJuego.entidades.Tarjeta;
import ModeloVista.ControlVista;
import ModeloVista.ModeloVista;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;

public class ModeloJuego {

    private List<Carta> mazo;
    private Jugador jugador;
    private Carta cartaActual;
    private int marcador = 0;
    private int contador = 0;
    private ModeloVista modeloVista;
    private Timer timer;
    private ControlVista controlVista;

    public ModeloJuego(ModeloVista modeloVista, ControlVista controlVista) {
        this.modeloVista = modeloVista;
        this.controlVista = controlVista;
        mazo = crearMazo();
        jugador = crearJugador(mazo);
        controlVista.setJugadorPrincipal(jugador);
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
        controlVista.actualizarCartaCantada(cartaActual);
        contador++;

        if (contador == 52) {
            contador = 0;
        }
    }

    // Validación de la carta seleccionada y actualización del marcador
    public void verificarCarta(int cartaSeleccionada) {
        int casilla = jugador.getTarjeta().getCasilla(cartaSeleccionada);
        if (casilla == cartaActual.getNumCarta()) {
            jugador.getTarjeta().marcarCasilla(cartaSeleccionada);
            controlVista.actualizarTarjetaJugadorPrincipal(jugador.getTarjeta().getMarcadas());
        }
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

    private void barajear() {
        Collections.shuffle(mazo);
    }

private Jugador crearJugador(List<Carta> mazo) {
    // Crear un arreglo de 16 casillas con números de cartas del mazo
    int[] casillas = new int[16];
    for (int i = 0; i < 16; i++) {
        casillas[i] = mazo.get(i).getNumCarta();
    }

    // Crear una tarjeta con las 16 casillas
    Tarjeta tarjetaPrueba = new Tarjeta(casillas);

    // Hardcodear un arreglo booleano de 16 posiciones con al menos 3 seleccionadas
    boolean[] marcadas = new boolean[16];
    marcadas[2] = true;  // Posición 3
    marcadas[6] = true;  // Posición 7
    marcadas[13] = true; // Posición 14

    // Asignar las casillas marcadas a la tarjeta
    tarjetaPrueba.setMarcadas(marcadas);

    // Crear y devolver el jugador
    Jugador jugador1 = new Jugador("Rodri", tarjetaPrueba, marcador);
    return jugador1;
}

}

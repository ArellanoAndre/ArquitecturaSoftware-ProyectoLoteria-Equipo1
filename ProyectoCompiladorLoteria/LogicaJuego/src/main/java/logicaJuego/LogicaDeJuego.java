/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaJuego;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.Timer;
import logicaJuego.entidades.Carta;
import logicaJuego.entidades.Jugador;
import modeloJuego.IModeloJuego;
import modeloJuego.ModeloJuego;

/**
 *
 * @author rodri
 */
public class LogicaDeJuego {

    private List<Carta> mazo;
    private List<Jugador> jugadores;
    private Carta cartaActual;
    private int contador = 0;
    private Timer timer;
    
    private IModeloJuego modeloJuego;

    /**
     * Constructor que inicializa el modelo con la vista, el jugador principal y
     * la lista de jugadores secundarios.
     *
     * @param jugadores lista de jugadores secundarios.
     */
    public LogicaDeJuego() {
        this.mazo = crearMazo();
        barajear();
        this.jugadores = new ArrayList<>();
        this.modeloJuego = new ModeloJuego();
    }

    /**
     * Inicia el juego mostrando la primera carta y repitiendo el proceso
     * automáticamente cada cierto tiempo.
     */
    public void iniciarJuego() {
        siguienteCarta();

        // Cada segundo cambia la carta cantada
        timer = new Timer(2500, e -> siguienteCarta());
        timer.start();
    }

    /**
     * Obtiene la siguiente carta del mazo y la envía a la vista.
     */
    public void siguienteCarta() {
        cartaActual = mazo.get(contador);
//        controlVista.actualizarCartaCantada(cartaActual); Llamar a modeloJuego
        contador++;

        if (contador == 54) {
            contador = 0;
        }
    }

    /**
     * Verifica si la carta seleccionada por el jugador coincide con la carta
     * actual.
     *
     * @param jugadorId número de jugador que hizo la jugada.
     * @param casillaSeleccionada número de casilla seleccionada (1–16).
     */
    public void verificarCarta(int jugadorId, int casillaSeleccionada) {
        
        Jugador jugadorP = null;
        
        for (Jugador jugadorL : jugadores) {
            if (jugadorId == jugadorL.getNumJugador()) {
                jugadorP = jugadorL;
            }
        }
        
        // Ajustar la posición a índice (de 1-16 a 0-15)
        int indice = casillaSeleccionada - 1;

        // Obtener el arreglo de casillas de la tarjeta
        int[] casillas = jugadorP.getTarjeta().getCasillas();

        // Verificar que el índice sea válido
        if (indice >= 0 && indice < casillas.length) {
            // Obtener el estado de las casillas marcadas
            boolean[] marcadas = jugadorP.getTarjeta().getMarcadas();

            // Validar que la casilla no esté ya marcada
            if (!marcadas[indice]) {
                // Comparar el número de la carta cantada con el valor en la posición seleccionada
                if (cartaActual != null && casillas[indice] == cartaActual.getNumCarta()) {
                    jugadorP.getTarjeta().marcarCasilla(casillaSeleccionada - 1);
                    
                    modeloJuego.EnviarEventoCartaSeleccionada(casillaSeleccionada, jugadorId);
                    
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

    /**
     * @return carta actual cantada.
     */
    public Carta getCartaActual() {
        return cartaActual;
    }

    /**
     * @return lista de cartas del mazo.
     */
    public List<Carta> getMazo() {
        return mazo;
    }

    /**
     * Asigna un nuevo mazo.
     */
    public void setMazo(List<Carta> mazo) {
        this.mazo = mazo;
    }

    /**
     * Crea un nuevo mazo de 54 cartas de lotería.
     *
     * @return lista con las cartas generadas.
     */
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

    /**
     * Mezcla las cartas del mazo.
     */
    public void barajear() {
        Collections.shuffle(mazo);
    }
    
    
}

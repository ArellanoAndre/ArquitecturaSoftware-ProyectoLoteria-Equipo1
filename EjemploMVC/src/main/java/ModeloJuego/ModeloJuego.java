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
    private List<Jugador> jugadoresSecundarios;
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
        jugador = crearJugador();
        jugadoresSecundarios = crearJugadoresSecundarios();
        controlVista.setJugadorPrincipal(jugador);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
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

    public void verificarCarta(int cartaSeleccionada) {
        // Ajustar la posición a índice (de 1-16 a 0-15)
        int indice = cartaSeleccionada - 1;

        // Obtener el arreglo de casillas de la tarjeta
        int[] casillas = jugador.getTarjeta().getCasillas();

        // Verificar que el índice sea válido
        if (indice >= 0 && indice < casillas.length) {
            // Obtener el estado de las casillas marcadas
            boolean[] marcadas = jugador.getTarjeta().getMarcadas();

            // Validar que la casilla no esté ya marcada
            if (!marcadas[indice]) {
                // Comparar el número de la carta cantada con el valor en la posición seleccionada
                if (cartaActual != null && casillas[indice] == cartaActual.getNumCarta()) {
                    jugador.getTarjeta().marcarCasilla(cartaSeleccionada - 1);
                    controlVista.actualizarTarjetaJugadorPrincipal(jugador.getTarjeta().getMarcadas());
                    System.out.println("ModeloJuego.si");
                } else {
                    System.out.println("ModeloJuego.no - No coincide la carta cantada (" + cartaActual.getNumCarta() + ") con la casilla " + casillas[indice]);
                }
            } else {
                System.out.println("ModeloJuego.skip - Casilla " + cartaSeleccionada + " ya está marcada");
            }
        } else {
            System.out.println("ModeloJuego.error - Índice inválido: " + indice);
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

    private Jugador crearJugador() {
        // Crear un arreglo de 16 casillas con números de cartas del mazo
        int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};

        // Crear una tarjeta con las 16 casillas
        Tarjeta tarjetaPrueba1 = new Tarjeta(casillas1);

        // Crear y devolver el jugador
        Jugador jugador1 = new Jugador("Rodri", tarjetaPrueba1, 1);
        return jugador1;
    }

    private List<Jugador> crearJugadoresSecundarios() {
        // Crear tres arreglos de 16 casillas para simular las tarjetas de los demás jugadores
        int[] casillas2 = {29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 31, 30};
        int[] casillas3 = {41, 31, 16, 48, 26, 53, 28, 31, 42, 39, 34, 27, 6, 37, 39, 8};
        int[] casillas4 = {13, 32, 42, 43, 23, 48, 2, 15, 26, 39, 17, 49, 6, 18, 45, 46};
        // Crear las tarjetas
        Tarjeta tarjetaPrueba2 = new Tarjeta(casillas2);
        Tarjeta tarjetaPrueba3 = new Tarjeta(casillas3);
        Tarjeta tarjetaPrueba4 = new Tarjeta(casillas4);
        //Marcar algunas casillas
        tarjetaPrueba2.marcarCasilla(0);
        tarjetaPrueba2.marcarCasilla(5);
        tarjetaPrueba2.marcarCasilla(10);
        tarjetaPrueba2.marcarCasilla(15);
        
        tarjetaPrueba3.marcarCasilla(5);
        tarjetaPrueba3.marcarCasilla(6);
        tarjetaPrueba3.marcarCasilla(9);
        tarjetaPrueba3.marcarCasilla(10);
        
        tarjetaPrueba4.marcarCasilla(0);
        tarjetaPrueba4.marcarCasilla(3);
        tarjetaPrueba4.marcarCasilla(12);
        tarjetaPrueba4.marcarCasilla(15);
        //Crear los jugadores secundarios
        Jugador jugador2 = new Jugador("Isaac", tarjetaPrueba2, 2);
        Jugador jugador3 = new Jugador("Jorge", tarjetaPrueba3, 3);
        Jugador jugador4 = new Jugador("Abril", tarjetaPrueba4, 4);
        //Crear la lista de jugadores secundarios y añadir a los que creamos
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador2);
        jugadores.add(jugador3);
        jugadores.add(jugador4);
        
        return jugadores;
    }
}

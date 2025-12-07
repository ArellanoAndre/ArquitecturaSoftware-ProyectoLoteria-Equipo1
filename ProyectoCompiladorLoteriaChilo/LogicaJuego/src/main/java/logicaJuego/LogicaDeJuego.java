/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaJuego;

import cantador.Griton;
import interfacesLogica.ILogicaJuego;
import interfacesLogica.IModeloLogica;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import logicaJuego.entidades.Carta;
import logicaJuego.entidades.Jugador;
import logicaJuego.entidades.Tarjeta;

/**
 *
 * @author rodri
 */
public class LogicaDeJuego implements ILogicaJuego {
    
    private Griton griton;
    private List<Jugador> jugadores;
    private Carta cartaActual;
    private int contador = 0;
    private String dificultad = "";
    private Timer timer;
    private int punMax;
    private int numJugadores;

    private IModeloLogica modeloLogica;

    public void setModeloLogica(IModeloLogica modelo) {
        this.modeloLogica = modelo;
    }

    /**
     * Constructor que inicializa el modelo con la vista, el jugador principal y
     * la lista de jugadores secundarios.
     *
     * @param jugadores lista de jugadores secundarios.
     */
    public LogicaDeJuego() {
        this.griton = new Griton();
        this.jugadores = new ArrayList<>();

    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public Griton getGriton() {
        return griton;
    }

    public void setGriton(Griton griton) {
        this.griton = griton;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getPunMax() {
        return punMax;
    }

    public void setPunMax(int punMax) {
        this.punMax = punMax;
    }

    public void setModelo(IModeloLogica modelo) {
        this.modeloLogica = modelo;
    }

    @Override
public void iniciarRonda() {

    // 1) Convertir la dificultad textual a milisegundos
    int delay;

    switch (dificultad.toLowerCase()) {
        case "básico":
        case "basico":
            delay = 10000;   // 10 segundos
            break;

        case "intermedio":
            delay = 5000;    // 7 segundos
            break;

        case "avanzado":
            delay = 2500;    // 4 segundos
            break;

        default:
            System.err.println("[LogicaDeJuego] Dificultad desconocida: " + dificultad);
            delay = 5000;  // Valor por defecto razonable
    }

    System.out.println("[LogicaDeJuego] Iniciando ronda con dificultad '" 
        + dificultad + "' (" + delay + " ms)");

    // 2) Barajear y cantar primera carta
    griton.barajear();
    siguienteCarta();

    // 3) Crear timer con el tiempo según la dificultad
    timer = new Timer(delay, e -> siguienteCarta());
    timer.start();
}


    /**
     * Obtiene la siguiente carta del mazo y la envía a la vista.
     */
    @Override
    public void siguienteCarta() {
        cartaActual = griton.getMazo().get(contador);
        contador++;

        if (contador == 54) {
            contador = 0;
        }
        if (modeloLogica != null) {
            // Usamos tipos primitivos (int, String) como definimos en la interfaz
            modeloLogica.notificarCartaCantada(cartaActual.getNumCarta(), cartaActual.getNombreCarta());
        }
    }

    /**
     * Verifica si la carta seleccionada por el jugador coincide con la carta
     * actual.
     *
     * @param jugadorId número de jugador que hizo la jugada.
     * @param casillaSeleccionada número de casilla seleccionada (1–16).
     */
    @Override
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

                    modeloLogica.EnviarEventoCartaSeleccionada(casillaSeleccionada - 1, jugadorId);

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

        System.out.println("Comparando: CartaActual=" + cartaActual.getNumCarta()
                + " vs CasillaJugador=" + casillas[indice]);
    }

    /**
     * @return carta actual cantada.
     */
    public Carta getCartaActual() {
        return cartaActual;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    private void verificarGanador(Jugador jugador) {

        boolean victoria = true;
        for (boolean marcada : jugador.getTarjeta().getMarcadas()) {
            if (!marcada) {
                victoria = false;
            } else {
                if (victoria) {
                    timer.stop();
                    if (modeloLogica != null) {

                        modeloLogica.notificarGanador(jugador.getNombre());
                    }
                }
            }
        }
    }

    @Override
    public void agregarJugadores() {
        int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};
        String img1 = "/img/Tableros/Tablero01.png";
        Tarjeta tarjeta1 = new Tarjeta(casillas1, img1);
        Jugador jugador1 = new Jugador("Rodri", tarjeta1, 1);

        jugadores.add(jugador1);

        int[] casillas2 = {29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36};
        String img2 = "/img/Tableros/Tablero02.png";
        Tarjeta tarjeta2 = new Tarjeta(casillas2, img2);
        Jugador jugador2 = new Jugador("Isaac", tarjeta2, 2);

        jugadores.add(jugador2);

    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

   
    
    public void agregarJugador(String nombre, String avatar) {

    int id = jugadores.size() + 1;

    Jugador jugador = new Jugador(id, nombre, avatar);
    jugadores.add(jugador);

    System.out.println("[Logica] Jugador agregado:");
    System.out.println("        ID: " + id);
    System.out.println("        Nombre: " + nombre);
    System.out.println("        Avatar: " + avatar);

    notificarConfirmacionReglas();
}

    public void notificarConfirmacionReglas() {
    modeloLogica.enviarConfirmacionReglas(
        this.dificultad,
        this.punMax,
        this.jugadores
    );
}

}

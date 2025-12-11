/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logicaJuego;

import cantador.Griton;
import interfacesLogica.ILogicaJuego;
import interfacesLogica.IModeloLogica;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.Timer;
import logicaJuego.entidades.Carta;
import logicaJuego.entidades.Jugador;
import logicaJuego.entidades.Tarjeta;
import org.json.JSONObject;

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
    private List<String> imagenesTarjetas;
    private boolean partidaConfigurada = false;
    private Map<String, Integer> puntuaciones;
    private int rondaActual = 1;
    private int MAX_RONDAS;
    private List<Tarjeta> todasLasTarjetas;

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
        this.todasLasTarjetas = GenerarTarjetas();
        //this.MAX_RONDAS = 1; // por mientras prueba

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

        griton.barajear();

        if (timer != null) {
            timer.stop();
        }

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
        // griton.barajear(); lo comente pq se llama en iniciarSiguienteRonda y pasa una maniacada
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
        jugador1.setAvatar("avatar1.png");

        jugadores.add(jugador1);

        int[] casillas2 = {29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36};
        String img2 = "/img/Tableros/Tablero02.png";
        Tarjeta tarjeta2 = new Tarjeta(casillas2, img2);
        Jugador jugador2 = new Jugador("Isaac", tarjeta2, 2);
        jugador2.setAvatar("avatar2.png");
        jugadores.add(jugador2);

    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void settearAvatar(String nombre, String avatar) {

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
                this.MAX_RONDAS,
                this.jugadores,
                this.numJugadores,
                this.obtener3TarjetasAlAzar()
        );
    }

    public List<String> getImagenesTarjetas() {
        return imagenesTarjetas;
    }

    public void setImagenesTarjetas(List<String> imagenesTarjetas) {
        this.imagenesTarjetas = imagenesTarjetas;
    }

    public void configurarPartida(String dificultad,
            int numJugadores,
            int numRondas,
            Map<String, Integer> puntuaciones) {

        System.out.println("[Logica] Configurando partida...");

        this.dificultad = dificultad;
        this.numJugadores = numJugadores;
        this.MAX_RONDAS = numRondas;

        // Guardamos las puntuaciones
        this.puntuaciones = puntuaciones;

        this.partidaConfigurada = true;
        System.out.println("[Logica] Partida configurada correctamente.");

        // YA NO - enviar confirmación de reglas al Cliente
    }

    public List<Tarjeta> GenerarTarjetas() {
        // Creamos la lista completa con los 54 tableros (01 al 54)

        List<Tarjeta> todosLosTableros = new ArrayList<>();

        todosLosTableros.add(new Tarjeta(
                new int[]{46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26},
                "/img/Tableros/Tablero01.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36},
                "/img/Tableros/Tablero02.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{41, 51, 16, 48, 26, 53, 28, 31, 42, 33, 34, 27, 6, 37, 39, 8},
                "/img/Tableros/Tablero03.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{13, 32, 42, 43, 23, 48, 2, 15, 26, 33, 17, 49, 8, 18, 45, 46},
                "/img/Tableros/Tablero04.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{11, 13, 16, 48, 32, 17, 7, 52, 22, 45, 23, 27, 6, 47, 53, 29},
                "/img/Tableros/Tablero05.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{42, 21, 50, 14, 9, 5, 1, 3, 52, 12, 25, 54, 24, 41, 35, 43},
                "/img/Tableros/Tablero06.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{12, 2, 27, 32, 3, 31, 37, 24, 41, 44, 38, 47, 16, 21, 30, 29},
                "/img/Tableros/Tablero07.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{24, 10, 47, 44, 28, 40, 27, 32, 19, 31, 30, 39, 49, 26, 7, 33},
                "/img/Tableros/Tablero08.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{36, 22, 37, 35, 6, 46, 53, 52, 4, 31, 25, 45, 16, 50, 5, 2},
                "/img/Tableros/Tablero09.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{3, 51, 48, 40, 10, 28, 53, 34, 42, 38, 47, 52, 43, 54, 9, 44},
                "/img/Tableros/Tablero10.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{8, 17, 36, 32, 19, 20, 14, 31, 49, 37, 51, 39, 4, 46, 23, 22},
                "/img/Tableros/Tablero11.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{39, 23, 46, 51, 6, 53, 11, 45, 9, 54, 50, 18, 28, 33, 27, 49},
                "/img/Tableros/Tablero12.png"
        ));

        return todosLosTableros;
    }

    public List<String> obtener3TarjetasAlAzar() {

        List<Tarjeta> copia = new ArrayList<>(todasLasTarjetas);
        Collections.shuffle(copia);

        return copia.stream()
                .limit(3)
                .map(Tarjeta::getImg)
                .collect(Collectors.toList());
    }

    public void asignarTarjetaAJugador(int idJugador, String rutaTarjeta) {

        // 1) Buscar jugador por su número
        Jugador jugador = jugadores.stream()
                .filter(j -> j.getNumJugador() == idJugador)
                .findFirst()
                .orElse(null);

        if (jugador == null) {
            System.out.println("❌ ERROR: No existe un jugador con numJugador = " + idJugador);
            return;
        }

        // 2) Buscar la tarjeta completa según la ruta
        Tarjeta tarjeta = todasLasTarjetas.stream()
                .filter(t -> t.getImg().equals(rutaTarjeta))
                .findFirst()
                .orElse(null);

        if (tarjeta == null) {
            System.out.println("❌ ERROR: No existe una tarjeta con ruta " + rutaTarjeta);
            return;
        }

        // 3) Asignar la tarjeta al jugador
        jugador.setTarjeta(tarjeta);

        System.out.println("✔ Tarjeta asignada al jugador " + idJugador + ": " + rutaTarjeta);
        modeloLogica.enviarTarjetaAsignada(idJugador, rutaTarjeta);
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public void AgregarJugadorTest() {
        // 1. CREACIÓN DE JUGADORES REALES
        // ===============================
        int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};
        String img1 = "/img/Tableros/Tablero01.png";
        Tarjeta tarjeta1 = new Tarjeta(casillas1, img1);
        Jugador jugador = new Jugador("Rodri", tarjeta1, 1);
        jugador.setAvatar("avatar1.png");

        jugadores.add(jugador);
    }

    @Override
    public boolean estaConfiguradaPartida() {
        return partidaConfigurada;
    }

    @Override
    public void enviarAbrirPantallaConfig() {
        modeloLogica.enviarAbrirPantallaConfig();
    }

    @Override
    public void enviarAbrirPantallaSeleccionAvatar() {
        modeloLogica.enviarAbrirPantallaSeleccionAvatar();
    }

    public void procesarIntentoLoteria(int idJugador) {
        Jugador jugador = buscarJugador(idJugador);

        if (jugador != null) {

            if (jugador.getTarjeta().esLoteria()) {
                System.out.println("" + jugador.getNombre() + " ganó la ronda " + rondaActual);

                jugador.addPuntos(100);
                finalizarRonda(jugador.getNombre());
            }

        }
    }

    /**
     * Maneja la transición cuando alguien gana una ronda. Detiene el juego y
     * decide si sigue la siguiente ronda o se acaba la partida.
     */
    /**
     * Maneja la transición cuando alguien gana una ronda. CORREGIDO: Evita
     * enviar doble evento (FinRonda + FinPartida) al mismo tiempo.
     */
    private void finalizarRonda(String nombreGanador) {

        if (timer != null) {
            timer.stop();
        }

        System.out.println("===== RESULTADO DE LA RONDA " + rondaActual + " =====");

        if (rondaActual >= MAX_RONDAS) {

            finalizarPartida();

        } else {

            if (modeloLogica != null) {
                modeloLogica.notificarFinDeRonda(rondaActual, nombreGanador);
            }

            rondaActual++;
            reiniciarTableros();
        }
    }

    /**
     * Se llama cuando el Host presiona "Continuar" en el JOptionPane. Reactiva
     * el flujo del juego.
     */
    public void iniciarSiguienteRonda() {
        System.out.println("====== Iniciando ronda " + rondaActual + " ======");

        griton.barajear();
        contador = 0;
        iniciarRonda();
    }

    /**
     * Caso de Uso: Ganar Partida. Calcula el ranking final y lo envía para
     * mostrar la copa.
     */
    private void finalizarPartida() {
        System.out.println("=== PARTIDA TERMINADA ===");

        List<Jugador> ranking = getListaRanking();

        if (modeloLogica != null) {
            modeloLogica.notificarFinPartida(ranking);
        }
    }

    /**
     * Ordena los jugadores de mayor a menor puntaje para el podio.
     */
    public List<Jugador> getListaRanking() {

        List<Jugador> rankingOrdenado = new ArrayList<>(this.jugadores);

        rankingOrdenado.sort((j1, j2) -> Double.compare(j2.getPuntaje(), j1.getPuntaje()));

        return rankingOrdenado;
    }

    private void reiniciarTableros() {
        for (Jugador jugador : jugadores) {
            jugador.getTarjeta().limpiar();

        }
    }

    private Jugador buscarJugador(int id) {
        return jugadores.stream()
                .filter(jugador -> jugador.getNumJugador() == id)
                .findFirst()
                .orElse(null);
    }

    public void cambioMVC() {
        modeloLogica.enviarEventoCambiarMVC();
    }
}

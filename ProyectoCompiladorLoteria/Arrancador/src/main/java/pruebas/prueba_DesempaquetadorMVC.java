package pruebas;

import Controlador.ControlSeleccionarCarta;
import Controlador.ControlVista;
import Desempaquetador.Desempaquetador;
import Evento.Evento;
import Helper.HelperJSON;
import ModeloJuego.ModeloJuego;
import ModeloJuego.entidades.Jugador;
import ModeloJuego.entidades.Tarjeta;
import ModeloVista.ModeloVista;
import Presentacion.JPantallaJuego;
import colaGenerica.ColaDePrioridad;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.SwingUtilities;
import listener.EventListener;

public class prueba_DesempaquetadorMVC {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // ===============================
            // 1. CREACIÓN DE JUGADORES REALES
            // ===============================
            int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};
            String img1 = "/img/Tableros/Tablero01.png";
            Tarjeta tarjeta1 = new Tarjeta(casillas1, img1);
            Jugador jugadorPrincipal = new Jugador("Rodri", tarjeta1, 1);

            int[] casillas2 = {29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36};
            String img2 = "/img/Tableros/Tablero02.png";
            Tarjeta tarjeta2 = new Tarjeta(casillas2, img2);
            Jugador jugador2 = new Jugador("Isaac", tarjeta2, 2);

            List<Jugador> secundarios = new ArrayList<>();
            secundarios.add(jugador2);

            // ===============================
            // 2. CREACIÓN DEL MVC REAL
            // ===============================
            ModeloVista modeloVista = new ModeloVista();
            ControlVista controlVista = new ControlVista(modeloVista);

            ModeloJuego modeloJuego = new ModeloJuego(
                    controlVista,
                    jugadorPrincipal,
                    secundarios
            );

            modeloVista.setModeloJuego(modeloJuego);

            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);

            // ===============================
            // 3. CREAR LA PANTALLA DEL JUEGO
            // ===============================
            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVista, controlador);
            pantalla1.setTitle("Jugador 1");
            pantalla1.setVisible(true);

            // ===============================
            // 4. CREAR TODA LA CAPA DE RED MOCK
            // ===============================
            ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();

            // Listener (simula red)
            EventListener listener = new EventListener(colaEntrada);

            //Desempaquetador
            Desempaquetador desempaquetador
                    = new Desempaquetador(colaEntrada, modeloJuego);

            colaEntrada.addObserverEntrada(desempaquetador);

            // ===============================
            // 5. SIMULAR PRIMER EVENTO INICIAL
            // ===============================
            Evento ev = new Evento();
            ev.setTopico("Juego");
            ev.setEvento("CasillaSeleccionada");
            ev.setJSON("{ \"TipoEvento\": \"CasillaSeleccionadaValida\", \"Jugador\": 1, \"Casilla\": 5 }");
            ev.setIpLocal("127.0.0.1");
            ev.setIpDestino("127.0.0.1");
            ev.setPuertoLocal(5001);
            ev.setPuertoDestino(5002);

            String jsonFinal = HelperJSON.toJSON(ev);

            System.out.println("\n>>> Enviando JSON simulado desde la RED...\n");
            listener.recibirJSON(jsonFinal);

            // =======================================================
            // 6. DISPARAR EVENTOS ALEATORIOS CADA 5 SEGUNDOS (MOCK)
            // =======================================================
            Thread hiloEventos = new Thread(() -> {
                Random random = new Random();

                while (true) {
                    try {
                        Thread.sleep(5000); // Cada 5 segundos
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    int casillaRandom = random.nextInt(15) + 1;

                    Evento eRandom = new Evento();
                    eRandom.setTopico("Juego-in");
                    eRandom.setEvento("Juego");
                    eRandom.setJSON(
                            "{ \"TipoEvento\": \"CasillaSeleccionadaValida\", "
                            + "\"Jugador\": 2, \"Casilla\": " + casillaRandom + " }"
                    );
                    eRandom.setIpLocal("127.0.0.1");
                    eRandom.setIpDestino("127.0.0.1");
                    eRandom.setPuertoLocal(5001);
                    eRandom.setPuertoDestino(5002);

                    String jsonRandom = HelperJSON.toJSON(eRandom);

                    System.out.println("\n>>> EVENTO ALEATORIO GENERADO (cada 5s): " + jsonRandom);

                    listener.recibirJSON(jsonRandom);
                }
            });

            hiloEventos.setDaemon(true);
            hiloEventos.start();
        });
    }
}

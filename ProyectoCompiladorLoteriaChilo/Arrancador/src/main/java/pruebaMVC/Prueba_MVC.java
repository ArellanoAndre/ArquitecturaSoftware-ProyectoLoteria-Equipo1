package pruebaMVC;

import Controlador.ControlSeleccionarCarta;
import Controlador.ControlVista;
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import ModeloJuegoEntidades.Jugador;
import ModeloJuegoEntidades.Tarjeta;
import ModeloVista.ModeloVistaJuego;
import Presentacion.JPantallaJuego;

import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import interfacesEntidades.IJugador;
import interfacesRed.IReceptorJSON;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import listener.EventListener;
import modeloJuegoMVC.ModeloJuego;
import receptor.ReceptorModelo;

public class Prueba_MVC {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            //======================================================================
            //Ya está lista para que la prueben con Prueva_EventosYRed, la 1 no la 2
            //======================================================================
            String ipDestino = "127.0.0.1";
            int puertoLocal = 5001;
            int puertoDestino = 7000;

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

            List<IJugador> secundarios = new ArrayList<>();
            secundarios.add(jugador2);

            // ===============================
            // 2. CREACIÓN DEL MVC REAL
            // ===============================
            ModeloVistaJuego modeloVista = new ModeloVistaJuego();
            modeloVista.setHost(true);

            ControlVista controlVista = new ControlVista(modeloVista);

            ModeloJuego modeloJuego = new ModeloJuego(
                    controlVista,
                    jugadorPrincipal,
                    secundarios
            );

            ReceptorModelo receptorModelo = new ReceptorModelo(modeloJuego);

            modeloVista.setModeloJuego(modeloJuego);

            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);

            // ===============================
            // 3. CREAR LA PANTALLA DEL JUEGO
            // ===============================
            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVista, controlador);
            pantalla1.setControl(controlVista);
            pantalla1.setTitle("Jugador 1");
            pantalla1.setVisible(true);

            //-----------------------------------------------------------------------------------------------------------------------------
            EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocal);

            ColaDePrioridad colaSalida = new ColaDePrioridad();
            ColaDePrioridad colaEntrada = new ColaDePrioridad();

            Empaquetador empaquetador = new Empaquetador(colaSalida, ipDestino, puertoDestino, puertoLocal);

            EventSender eventSender = new EventSender(colaSalida);
            colaSalida.addObserverSalida(eventSender);

            Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, receptorModelo);
            colaEntrada.addObserverEntrada(desempaquetador);

            EventListener eventListener = new EventListener(colaEntrada);

            modeloJuego.setEmpaquetador(empaquetador);

            IReceptorJSON receptorJSON = eventListener;
            try {
                ensambladorRed.ensamblar(receptorJSON);
                Dispatcher dispatcher = ensambladorRed.getDispatcher();
                eventSender.setiDispatcher(dispatcher);
            } catch (IOException ex) {
                Logger.getLogger(Prueba_MVC.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            modeloJuego.suscribirseJuegoOut();
            
            modeloJuego.enviarEventoIniciarRonda();

        });
    }
}

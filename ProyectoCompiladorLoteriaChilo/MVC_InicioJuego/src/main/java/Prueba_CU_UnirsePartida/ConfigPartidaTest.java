/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prueba_CU_UnirsePartida;
import Controlador.ControlIModeloVista;
import Controlador.ControladorInicio;
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import ModeloJuegoEntidades.ConfiguracionPartida;
import ModeloVista.ModeloVistaInicio;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import Presentacion.JPantallaMenuPrincipal;
import Presentacion.JPantallaConfigurarPartida;
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import listener.EventListener;
import modeloJuegoMVC.ModeloJuego;
import receptor.ReceptorModelo;
import javax.swing.*;

/**
 *
 * @author Arell
 */
public class ConfigPartidaTest {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            System.out.println("=== PRUEBA MVC – CLIENTE INICIO ===");

            String ipBroker = "127.0.0.1";
            int puertoCliente = 5001;
            int puertoBroker = 7000;

            // -------------------------------
            // MODELOS
            // -------------------------------
            ModeloJuego modeloJuego = new ModeloJuego();
            ConfiguracionPartida config = new ConfiguracionPartida();
            ModeloVistaInicio modeloVista = new ModeloVistaInicio();

            // Control de inicio
            ControladorInicio controlVista = new ControladorInicio(modeloVista);
            modeloVista.setControlador(controlVista);

            ControlIModeloVista controlModeloVista =
                    new ControlIModeloVista(modeloVista, config);
            modeloJuego.setControlVistaInicio(controlModeloVista);
            modeloVista.setModeloJuego(modeloJuego);

            // -------------------------------
            // PANTALLAS (View)
            // -------------------------------
            JPantallaMenuPrincipal menu = new JPantallaMenuPrincipal(controlVista);
            modeloVista.setMenuPrincipal(menu);

            JPantallaConfigurarPartida pantallaConfig = new JPantallaConfigurarPartida(controlVista);
            modeloVista.setPantallaConfig(pantallaConfig);

            JFrameSeleccionAvatar avatar = new JFrameSeleccionAvatar(controlVista);
            modeloVista.setPantallaAvatar(avatar);

            JFrameLobby lobby = new JFrameLobby(controlVista);
            lobby.setModeloVista(modeloVista);

            modeloVista.setPantallaLobby(lobby);

            // Observers
            modeloVista.addObserver(lobby);
            modeloVista.addObserver(avatar);

            // Mostrar pantalla inicio
            menu.setVisible(true);

            // -------------------------------
            // RED
            // -------------------------------
            ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();
            ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();

            Empaquetador empaquetador =
                    new Empaquetador(colaSalida, ipBroker, puertoBroker, puertoCliente);

            EventSender sender = new EventSender(colaSalida);
            colaSalida.addObserverSalida(sender);

            ReceptorModelo receptorModelo = new ReceptorModelo(modeloJuego);
            Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, receptorModelo);
            colaEntrada.addObserverEntrada(desempaquetador);

            EventListener listener = new EventListener(colaEntrada);

            try {
                EnsambladorRed ensamblador = new EnsambladorRed(puertoCliente);
                ensamblador.ensamblar(listener);

                Dispatcher dispatcher = ensamblador.getDispatcher();
                sender.setiDispatcher(dispatcher);

            } catch (Exception e) {
                e.printStackTrace();
            }

            modeloJuego.setEmpaquetador(empaquetador);
            modeloJuego.suscribirseJuegoOut();

            System.out.println("[CLIENTE] Flujo iniciado. Usa el botón JUGAR.");
        });
    }
}

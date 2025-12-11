/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVCEnsamblador;

import Controlador.ControlIModeloVista;
import Controlador.ControlSeleccionarCarta;
import Controlador.ControlVista;
import Controlador.ControladorInicio;
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import ModeloJuegoEntidades.ConfiguracionPartida;
import ModeloJuegoEntidades.Jugador;
import ModeloJuegoEntidades.Tarjeta;
import ModeloVista.ModeloVistaInicio;
import ModeloVista.ModeloVistaJuego;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import Presentacion.JPantallaConfigurarPartida;
import Presentacion.JPantallaJuego;
import Presentacion.JPantallaMenuPrincipal;
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
import pruebaMVC.Prueba_MVC;
import receptor.ReceptorModelo;

/**
 *
 * @author rodri
 */
public class EnsambladorMVC {

    public void ensamblarMVC(String ipDestino, int puertoLocal, int puertoDestino) {
        SwingUtilities.invokeLater(() -> {

            System.out.println("=== PRUEBA MVC – CLIENTE INICIO ===");

            // -------------------------------
            // MODELO DE JUEGO
            // -------------------------------
            ModeloJuego modeloJuego = new ModeloJuego();
            ReceptorModelo receptorModelo = new ReceptorModelo(modeloJuego);

            // -------------------------------
            // Infraestructura MVC Inicio
            // -------------------------------
            ConfiguracionPartida config = new ConfiguracionPartida();
            ModeloVistaInicio modeloVistaInicio = new ModeloVistaInicio();

            // Control de inicio
            ControladorInicio controlVistaInicio = new ControladorInicio(modeloVistaInicio);
            modeloVistaInicio.setControlador(controlVistaInicio);

            ControlIModeloVista controlModeloVista = new ControlIModeloVista(modeloVistaInicio, config);

            modeloJuego.setControlVistaInicio(controlModeloVista);
            modeloVistaInicio.setModeloJuego(modeloJuego);

            // -------------------------------
            // PANTALLAS MVC_Inicio(View)
            // -------------------------------
            JPantallaMenuPrincipal menu = new JPantallaMenuPrincipal(controlVistaInicio);
            modeloVistaInicio.setMenuPrincipal(menu);

            JPantallaConfigurarPartida pantallaConfig = new JPantallaConfigurarPartida(controlVistaInicio);
            modeloVistaInicio.setPantallaConfig(pantallaConfig);

            JFrameSeleccionAvatar avatar = new JFrameSeleccionAvatar(controlVistaInicio);
            modeloVistaInicio.setPantallaAvatar(avatar);

            JFrameLobby lobby = new JFrameLobby(controlVistaInicio);
            lobby.setModeloVista(modeloVistaInicio);

            modeloVistaInicio.setPantallaLobby(lobby);

            // Observers
            modeloVistaInicio.addObserver(lobby);
            modeloVistaInicio.addObserver(avatar);

            // Mostrar pantalla inicio
            //menu.setVisible(true);
            // ===============================
            // Infraestructura MVC Juego
            // ===============================
            ModeloVistaJuego modeloVistaJuego = new ModeloVistaJuego();
            ControlVista controlVistaJuego = new ControlVista(modeloVistaJuego);

            modeloJuego.setControlVistaJuego(controlVistaJuego);
            modeloVistaJuego.setModeloJuego(modeloJuego);

            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVistaJuego);

            // ===============================
            // 3. CREAR LA PANTALLA DEL JUEGO
            // ===============================
            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVistaJuego, controlador);
            pantalla1.setControl(controlVistaJuego);
            pantalla1.setTitle("Jugador 1");
            

            // -------------------------------
            // RED
            // -------------------------------
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

            System.out.println("[CLIENTE] Flujo iniciado. Usa el botón JUGAR.");
            modeloJuego.suscribirseJuegoOut();
            modeloJuego.AsignarID();
           
        });
    }

}

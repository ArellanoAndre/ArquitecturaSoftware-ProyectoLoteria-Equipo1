package MVCEnsamblador;

import Controlador.ControlIModeloVista;
import Controlador.ControlSeleccionarCarta;
import Controlador.ControlVista;
import Controlador.ControladorInicio;
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import ModeloJuegoEntidades.ConfiguracionPartida;
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
import interfacesRed.IReceptorJSON;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import listener.EventListener;
import modeloJuegoMVC.ModeloJuego;
import receptor.ReceptorModelo;
import java.util.Arrays; // Necesario para el hack

/**
 * Ensamblador especial para PRUEBAS DE FLUJO.
 * Inicia el cliente y llena el tablero automáticamente después de unos segundos.
 */
public class EnsambladorMVC_Ganador {

    public void ensamblarMVC(String ipDestino, int puertoLocal, int puertoDestino) {
        SwingUtilities.invokeLater(() -> {

            System.out.println("=== CLIENTE MODO PRUEBAS (TABLERO AUTO-LLENO) ===");

            // -------------------------------
            // 1. MODELO DE JUEGO Y RECEPTOR
            // -------------------------------
            ModeloJuego modeloJuego = new ModeloJuego();
            ReceptorModelo receptorModelo = new ReceptorModelo(modeloJuego);

            // -------------------------------
            // 2. INFRAESTRUCTURA MVC INICIO (Login, Lobby, etc.)
            // -------------------------------
            ConfiguracionPartida config = new ConfiguracionPartida();
            ModeloVistaInicio modeloVistaInicio = new ModeloVistaInicio();

            ControladorInicio controlVistaInicio = new ControladorInicio(modeloVistaInicio);
            modeloVistaInicio.setControlador(controlVistaInicio);

            ControlIModeloVista controlModeloVista = new ControlIModeloVista(modeloVistaInicio, config);

            modeloJuego.setControlVistaInicio(controlModeloVista);
            modeloVistaInicio.setModeloJuego(modeloJuego);

            // Pantallas de Inicio
            JPantallaMenuPrincipal menu = new JPantallaMenuPrincipal(controlVistaInicio);
            modeloVistaInicio.setMenuPrincipal(menu);

            JPantallaConfigurarPartida pantallaConfig = new JPantallaConfigurarPartida(controlVistaInicio);
            modeloVistaInicio.setPantallaConfig(pantallaConfig);

            JFrameSeleccionAvatar avatar = new JFrameSeleccionAvatar(controlVistaInicio);
            modeloVistaInicio.setPantallaAvatar(avatar);

            JFrameLobby lobby = new JFrameLobby(controlVistaInicio);
            lobby.setModeloVista(modeloVistaInicio);
            modeloVistaInicio.setPantallaLobby(lobby);

            // Observers Inicio
            modeloVistaInicio.addObserver(lobby);
            modeloVistaInicio.addObserver(avatar);

            // -------------------------------
            // 3. INFRAESTRUCTURA MVC JUEGO (Tablero)
            // -------------------------------
            ModeloVistaJuego modeloVistaJuego = new ModeloVistaJuego();
            
            // Aquí usamos los DOS controladores como definimos en la arquitectura nueva
            ControlVista controlVistaJuego = new ControlVista(modeloVistaJuego);
            ControlSeleccionarCarta controlSeleccionar = new ControlSeleccionarCarta(modeloVistaJuego);

            modeloJuego.setControlVistaJuego(controlVistaJuego);
            modeloVistaJuego.setModeloJuego(modeloJuego);

            // -------------------------------
            // 4. CREAR PANTALLA DE JUEGO
            // -------------------------------
            // Inyectamos el controlador de selección (INPUTS)
            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVistaJuego, controlSeleccionar);
            
            // >>> IMPORTANTE: Inyectamos el controlador de vista (OUTPUTS/GENERAL) <<<
            pantalla1.setControl(controlVistaJuego); 
            
            pantalla1.setTitle("JUGADOR TEST (Auto-Win)");

            // -------------------------------
            // 5. RED (Networking)
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
                Logger.getLogger(EnsambladorMVC_Ganador.class.getName()).log(Level.SEVERE, null, ex);
            }

            // ==========================================================
            //  HACK: HILO PARA LLENAR EL TABLERO AUTOMÁTICAMENTE
            // ==========================================================
            new Thread(() -> {
                try {
                    System.out.println("[HACK] Esperando 15 segundos para que inicies la partida...");
                    Thread.sleep(15000); // 15 seg de tolerancia para elegir avatar y dar jugar
                    
                    System.out.println("[HACK] Intentando llenar tablero...");
                    
                    if (modeloJuego.getJugadorPrincipal() != null && modeloJuego.getJugadorPrincipal().getTarjeta() != null) {
                        
                        // 1. Crear arreglo lleno de TRUE
                        boolean[] tableroLleno = new boolean[16];
                        Arrays.fill(tableroLleno, true);
                        
                        // 2. Hackear el Modelo Local (Entidad)
                        modeloJuego.getJugadorPrincipal().getTarjeta().setMarcadas(tableroLleno);
                        
                        // 3. Hackear la Vista (Refrescar UI)
                        // Usamos invokeLater porque Swing no es thread-safe
                        SwingUtilities.invokeLater(() -> {
                            controlVistaJuego.actualizarTarjetaJugadorPrincipal(tableroLleno);
                            System.out.println("[HACK] ¡ÉXITO! Tablero visual lleno. Presiona LOTERÍA.");
                        });
                        
                    } else {
                        System.out.println("[HACK] Falló: No se encontró jugador o tarjeta (¿Aún no inicia la partida?)");
                    }
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            // ==========================================================

            System.out.println("[CLIENTE] Flujo iniciado. Espera el Hack...");
            modeloJuego.suscribirseJuegoOut();
            modeloJuego.AsignarID();
        });
    }
}
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
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import interfacesComunicacionModelo.IModeloVistaInicio;
import javax.swing.SwingUtilities;
import modeloJuegoMVC.ModeloJuego;
import listener.EventListener;
import receptor.ReceptorModelo;
public class UnirsePartidaTest {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            String ipBroker = "127.0.0.1";
            int puertoLocalCliente = 5001;
            int puertoBroker = 7000;

            // 1) INSTANCIAS PRINCIPALES
            ModeloJuego modeloJuego = new ModeloJuego();
            ConfiguracionPartida config = new ConfiguracionPartida();

            // ESTE es el ÚNICO ModeloVistaInicio
            ModeloVistaInicio modeloVista = new ModeloVistaInicio();

            // Controlador de la UI de inicio
            ControladorInicio controlVista = new ControladorInicio(modeloVista);
            modeloVista.setControlador(controlVista);

            // Control puente MODELO↔VISTA (lo usa ModeloJuego)
            ControlIModeloVista controlModeloVista =
                    new ControlIModeloVista(modeloVista, config); // ← LE PASAMOS modeloVista
            controlModeloVista.setConfig(config);

            // ModeloJuego conoce a su "control de vista"
            modeloJuego.setControlVistaInicio(controlModeloVista);

            // ModeloVista conoce al modelo de juego
            modeloVista.setModeloJuego(modeloJuego);

            // 2) JFRAMES Y OBSERVERS
            JFrameLobby lobby = new JFrameLobby(controlVista);
            lobby.setModeloVista(modeloVista);      // dentro hace addObserver(this)
            modeloVista.setPantallaLobby(lobby);

            JFrameSeleccionAvatar pantallaAvatar = new JFrameSeleccionAvatar(controlVista);
            pantallaAvatar.setModeloVista(modeloVista); // dentro puede addObserver(this) si quieres
            modeloVista.setPantallaAvatar(pantallaAvatar);

            // Mostrar primero selección de avatar
            pantallaAvatar.setVisible(true);

            // 3) RED (como ya la tenías)
            ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();
            ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();

            Empaquetador empaquetador =
                    new Empaquetador(colaSalida, ipBroker, puertoBroker, puertoLocalCliente);

            EventSender sender = new EventSender(colaSalida);
            colaSalida.addObserverSalida(sender);

            ReceptorModelo receptorModelo = new ReceptorModelo(modeloJuego);
            Desempaquetador desempaquetador =
                    new Desempaquetador(colaEntrada, receptorModelo);
            colaEntrada.addObserverEntrada(desempaquetador);

            EventListener eventListener = new EventListener(colaEntrada);

            try {
                EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocalCliente);
                ensambladorRed.ensamblar(eventListener);
                Dispatcher dispatcher = ensambladorRed.getDispatcher();
                sender.setiDispatcher(dispatcher);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            modeloJuego.setEmpaquetador(empaquetador);

            // Suscripción
            modeloJuego.suscribirseJuegoOut();

            System.out.println("[CLIENTE] Todo listo. Esperando selección de avatar...");
        });
    }
}

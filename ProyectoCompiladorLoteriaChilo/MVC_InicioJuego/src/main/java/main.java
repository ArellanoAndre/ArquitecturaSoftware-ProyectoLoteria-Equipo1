

import Controlador.ControlIModeloVista;
import Controlador.ControladorInicio;
import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import Interfaces.IControladorInicio;
import ModeloVista.ConfiguracionPartida;
import ModeloVista.ModeloVista;
import Presentacion.JFrameSeleccionAvatar;
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import interfacesComunicacionModelo.IControlIModeloVista;
import interfacesComunicacionModelo.IModeloVista;
import interfacesEntidades.IConfiguracionPartida;
import java.io.IOException;
import javax.swing.SwingUtilities;
import modeloJuegoMVC.ModeloJuego;
import listener.EventListener;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jorge
 */
public class main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            String ipBroker = "127.0.0.1";
            int puertoLocalCliente = 5001;
            int puertoBroker = 7000;

            // ======================================================
            // 1. MVC Inicio
            // ======================================================
            ModeloVista modeloVista = new ModeloVista();
            IControladorInicio controlVista = new ControladorInicio(modeloVista);
            ModeloJuego modeloJuego = new ModeloJuego();
            IConfiguracionPartida config = new ConfiguracionPartida();
            IControlIModeloVista controlModeloVista = new ControlIModeloVista(config);

            modeloVista.setModeloJuego(modeloJuego);

            JFrameSeleccionAvatar pantalla = new JFrameSeleccionAvatar(controlVista);
            pantalla.setVisible(true);

            // ======================================================
            // 2. COLAS PROPIAS (NO LAS DEL ENSAMBLADOR)
            // ======================================================
            ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();
            ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();

            // ======================================================
            // 3. Empaquetador → envía al broker
            // ======================================================
            Empaquetador empaquetador =
                    new Empaquetador(colaSalida, ipBroker, puertoBroker, puertoLocalCliente);

            EventSender sender = new EventSender(colaSalida);
            colaSalida.addObserverSalida(sender);

            // ======================================================
            // 4. Desempaquetador → recibe del broker
            // ======================================================
            Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modeloJuego);
            colaEntrada.addObserverEntrada(desempaquetador);

            EventListener eventListener = new EventListener(colaEntrada);

            // ======================================================
            // 5. ENSAMBLADOR RED (solo UNA VEZ)
            // ======================================================
            try {
                EnsambladorRed ensambladorRed = new EnsambladorRed(puertoLocalCliente);

                ensambladorRed.ensamblar(eventListener);

                Dispatcher dispatcher = ensambladorRed.getDispatcher();
                sender.setiDispatcher(dispatcher);

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // ======================================================
            // 6. ModeloJuego debe tener el empaquetador
            // ======================================================
            modeloJuego.setEmpaquetador(empaquetador);
            // Pasar estos a ModeloJuego
            modeloJuego.setControlmodelovista(controlModeloVista);

            // También pasar config a ModeloVista si lo necesitas:
            controlModeloVista.setConfig(config);

            // ======================================================
            // 7. Cliente se SUSCRIBE A Juego-out
            // ======================================================
            modeloJuego.suscribirseJuegoOut();

            System.out.println("[CLIENTE] Listo. Esperando selección de avatar...");

        });
    }
}

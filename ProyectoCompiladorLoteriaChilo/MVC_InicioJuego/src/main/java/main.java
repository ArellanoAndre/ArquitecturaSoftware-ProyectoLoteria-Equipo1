

import Controlador.ControladorInicio;
import Empaquetador.Empaquetador;
import ModeloJuego.ModeloJuegoInicio;
import ModeloVista.ModeloVista;
import Presentacion.JPantallaMenuPrincipal;
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;

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
        // Configuración de red (puede ajustarse según el entorno de pruebas)
        String ipDestino = "127.0.0.1";
        int puertoDestino = 5000;
        int puertoLocal = 6000;

        // Cola de prioridad que usará el empaquetador y el sender
        ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad<>();

        // Componentes de eventos
        Empaquetador empaquetador = new Empaquetador(colaSalida, ipDestino, puertoDestino, puertoLocal);

        // Opcional: activar envío real mediante el observador de la cola
        EventSender eventSender = new EventSender(colaSalida);
        colaSalida.addObserverSalida(eventSender);

        // Modelo de juego con sus dependencias listas para crear y empaquetar eventos
        ModeloJuegoInicio modeloJuego = new ModeloJuegoInicio();
        modeloJuego.setEmpaquetador(empaquetador);
        // modeloVista
         ModeloVista modeloVista = new ModeloVista();
        //controlador 
        ControladorInicio ci = new ControladorInicio(modeloVista, modeloJuego);
        if (ci != null) {
        // Lanzar el flujo de pantallas con las dependencias ensambladas
        JPantallaMenuPrincipal menu = new JPantallaMenuPrincipal(ci);
        menu.setVisible(true);
        }
    }
}

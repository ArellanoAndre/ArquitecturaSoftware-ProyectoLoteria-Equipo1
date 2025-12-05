/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloJuegoMVC;

import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import ModeloJuegoEntidades.Jugador;

import java.util.List;
import InterfacesEventClient.IReceptorEvento;
import ModeloJuegoEntidades.Carta;
import cadenaModeloJuego.IModeloChain;
import cadenaModeloJuego.ProcesadorCartaCantada;
import cadenaModeloJuego.ProcesadorCasillaSeleccionada;
import interfacesComunicacionModelo.IControlVista;
import interfacesEntidades.IJugador;
import interfacesComunicacionModelo.IModeloJuego;
import org.json.JSONObject;

public class ModeloJuego implements IModeloJuego, IReceptorEvento {

    private IControlVista controlVista;
    private Jugador jugadorPrincipal;
    private List<IJugador> jugadoresSecundarios;
    private IEnvioEvento empaquetador;
    
    private IModeloChain cadenaProcesamiento;
    
    //Banderita
    private boolean host = false;

    public ModeloJuego(IControlVista controlVista, Jugador jugadorPrincipal, List<IJugador> jugadoresSecundarios) {
        this.controlVista = controlVista;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        controlVista.setJugadorPrincipal(this.jugadorPrincipal);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
        
        armarFiltros();
    }

    public void armarFiltros(){
        IModeloChain cartaCantada = new ProcesadorCartaCantada();
        IModeloChain casillaSeleccionada = new ProcesadorCasillaSeleccionada();
        
        cartaCantada.setSiguiente(casillaSeleccionada);
        
        this.cadenaProcesamiento = cartaCantada;
    }
    
    public void setEmpaquetador(IEnvioEvento empaquetador) {
        this.empaquetador = empaquetador;
    }

    @Override
    public void manejar(IEvento evento) {

        System.out.println("[ModeloJuegoMock] Evento recibido COMPLETO:");
        System.out.println(evento);

        try {
            
            // Extraer el JSON interno del EVENTO
            String payloadJSON = evento.getJSON();

            if (payloadJSON == null) {
                System.err.println("[ModeloJuegoMock] JSON interno es null");
                return;
            }
            
            JSONObject obj = new JSONObject(payloadJSON);

            // Identificar el tipo de evento interno
            String tipo = obj.getString("TipoEvento");

            System.out.println("[ModeloJuegoMock] TipoEvento = " + tipo);
            cadenaProcesamiento.procesar(tipo, obj, controlVista, this);

//            // ================================
//            //   CHAIN OF RESPONSIBILITY
//            // ================================
//            // 1. Inicio Partida
//            if (tipo.equals("InicioPartida")) {
//                //  manejarInicioPartida(obj);
//                System.out.println("[ModeloJuegoMock] manejarInicioPartida");
//                return;
//            } // 4. Evento UnirseAlLobby
//            else if (tipo.equals("UnirseLobby")) {
//                //   manejarUnirseLobby(obj);
//                System.out.println("[ModeloJuegoMock]  manejarUnirseLobby");
//                return;
//            } // 5. Eventos no reconocidos
//            else {
//                System.out.println("[ModeloJuegoMock] TipoEvento NO RECONOCIDO: " + tipo);
//            }

        } catch (Exception e) {
            System.err.println("[ModeloJuegoMock] ERROR manejando evento: " + e.getMessage());
        }
    }

    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int jugador) {
        IEvento eventoCarta = empaquetador.crearEvento();
        eventoCarta.setTopico("Juego-in");
        eventoCarta.setEvento("Juego");
        eventoCarta.setJSON(
                "{ \"TipoEvento\": \"CASILLA_SELECCIONADA\", \"Jugador\": " + jugadorPrincipal.getNumJugador() + ", \"Casilla\": " + pos + " }"
        );
        empaquetador.enviarEvento(eventoCarta);
    }

    @Override
    public void EnviarEventoConfigurarPartida() {
        host = true;
    }

    @Override
    public void EnviarEventoIniciarRonda() {
        IEvento eventoInicioRonda = empaquetador.crearEvento();
        eventoInicioRonda.setTopico("Juego-in");
        eventoInicioRonda.setEvento("Juego");
        eventoInicioRonda.setJSON(
                "{ \"TipoEvento\": \"INICIAR_RONDA\" }"
        );
        empaquetador.enviarEvento(eventoInicioRonda);
    }

    public Jugador getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public List<IJugador> getJugadoresSecundarios() {
        return jugadoresSecundarios;
    }
    
    

}

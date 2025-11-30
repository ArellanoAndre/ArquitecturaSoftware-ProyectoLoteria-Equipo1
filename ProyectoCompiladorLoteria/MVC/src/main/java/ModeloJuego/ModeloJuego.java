/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

import Empaquetador.Empaquetador;
import Interfaces.IControlVista;
import Interfaces.IModeloJuego;
import InterfacesEventClient.IEvento;
import ModeloJuego.entidades.Jugador;

import java.util.List;
import org.json.JSONObject;
import InterfacesEventClient.IReceptorEvento;

public class ModeloJuego implements IModeloJuego, IReceptorEvento {

    private IControlVista controlVista;
    private Jugador jugadorPrincipal;
    private List<Jugador> jugadoresSecundarios;
    private boolean[] casillasMarcadas = new boolean[16];
    private Empaquetador empaquetador;


    public ModeloJuego(IControlVista controlVista, Jugador jugadorPrincipal, List<Jugador> jugadoresSecundarios) {
        this.controlVista = controlVista;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        controlVista.setJugadorPrincipal(this.jugadorPrincipal);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
    }
    
    public void setEmpaquetador(Empaquetador empaquetador){
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

        // ================================
        //   CHAIN OF RESPONSIBILITY
        // ================================

        // 1. Casilla Seleccionada
        if (tipo.equals("CasillaSeleccionadaValida")) {
          //  manejarCasillaSeleccionada(obj);
          System.out.println("[ModeloJuegoMock] manejarCasillaSeleccionada");
            return;
        }

        // 2. Evento Inicio de partida
        if (tipo.equals("InicioPartida")) {
          //  manejarInicioPartida(obj);
          System.out.println("[ModeloJuegoMock] manejarInicioPartida");
            return;
        }

        // 3. Evento UnirseAlLobby
        if (tipo.equals("UnirseLobby")) {
         //   manejarUnirseLobby(obj);
            System.out.println("[ModeloJuegoMock]  manejarUnirseLobby");
            return;
        }

        // 4. Eventos no reconocidos
        System.out.println("[ModeloJuegoMock] TipoEvento NO RECONOCIDO: " + tipo);

    } catch (Exception e) {
        System.err.println("[ModeloJuegoMock] ERROR manejando evento: " + e.getMessage());
    }
}



    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int jugador) {
        IEvento eRandom = empaquetador.crearEvento();
        eRandom.setTopico("Juego-in");
            eRandom.setEvento("Juego");
            eRandom.setJSON(
                            "{ \"TipoEvento\": \"CasillaSeleccionadaValida\", \"Jugador\": " + jugador + ", \"Casilla\": " + pos + " }"
                    );
            empaquetador.enviarEvento(eRandom);
    }

    @Override
    public void setControlVista(IControlVista controlVista) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


 
}
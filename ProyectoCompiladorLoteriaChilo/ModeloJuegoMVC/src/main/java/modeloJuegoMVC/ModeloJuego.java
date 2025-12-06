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
import cadenaModeloJuego.IModeloChain;
import cadenaModeloJuego.ProcesadorCartaCantada;
import cadenaModeloJuego.ProcesadorCasillaSeleccionada;
import cadenaModeloJuego.ProcesadorUnirsePartida;
import interfacesComunicacionModelo.IModeloVista;
import interfacesComunicacionModelo.IControlIModeloVista;
import interfacesComunicacionModelo.IControlVista;
import interfacesEntidades.IJugador;
import interfacesComunicacionModelo.IModeloJuego;
import org.json.JSONObject;

public class ModeloJuego implements IModeloJuego, IReceptorEvento {

    private IControlVista controlVista;
    private Jugador jugadorPrincipal;
    private List<IJugador> jugadoresSecundarios;
    private IEnvioEvento empaquetador;
    private IModeloVista modelovista;
    private IControlIModeloVista controlmodelovista;    
    private IModeloChain cadenaProcesamiento;
    
    //Banderita
    private boolean host = false;

    public ModeloJuego() {
        armarFiltros();
    }

    public ModeloJuego(IControlVista controlVista, Jugador jugadorPrincipal, List<IJugador> jugadoresSecundarios,IControlIModeloVista controlmodelovista) {
        this.controlVista = controlVista;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        this.controlmodelovista = controlmodelovista;
        controlVista.setJugadorPrincipal(this.jugadorPrincipal);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
        
        armarFiltros();
    }
    
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
        IModeloChain unirsepartida = new ProcesadorUnirsePartida();
        cartaCantada.setSiguiente(casillaSeleccionada);
        casillaSeleccionada.setSiguiente(unirsepartida);
        
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
            cadenaProcesamiento.procesar(tipo, obj, controlVista, this,controlmodelovista);

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
    
   @Override
public void EnviarNombreAvatarConfirmado(String nombre, String avatar) {

    System.out.println("[ModeloJuego] → Enviando evento UNIRSE_PARTIDA");

    try {
        // 1) Construir JSON del evento
        JSONObject json = new JSONObject();
        json.put("TipoEvento", "UNIRSE_PARTIDA");
        json.put("Nombre", nombre);
        json.put("Avatar", avatar);

        // 2) Usar el método general de envío
        enviarEventoBroadcast(json.toString(), "Juego-in");

        System.out.println("[ModeloJuego] JSON enviado: " + json.toString());

    } catch (Exception e) {
        System.err.println("[ModeloJuego] Error enviando UnirsePartida: " + e.getMessage());
    }
}

    @Override
    public void enviarEventoBroadcast(String jsonPayload, String topico) { // para no repetir codigo

       if (empaquetador == null) {
           return;
       }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico(topico);
        evento.setEvento("ActualizacionJuego");
        evento.setJSON(jsonPayload);

        empaquetador.enviarEvento(evento);
        System.out.println("[ModeloLogica-Host] Enviado evento: " + jsonPayload);

    }
    
    @Override
    public void suscribirseJuegoOut() {

    JSONObject json = new JSONObject();
    json.put("TipoEvento", "suscripcion");

    // Usamos el mismo método que ya centraliza el envío
    enviarEventoBroadcast(json.toString(), "Juego-out");

    System.out.println("[Cliente] Suscripción enviada a Juego-out");
}

    public void setModelovista(IModeloVista modelovista) {
        this.modelovista = modelovista;
    }

    public void setControlmodelovista(IControlIModeloVista controlmodelovista) {
        this.controlmodelovista = controlmodelovista;
    }

    


}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloJuegoMVC;

import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import ModeloJuegoEntidades.Carta;
import ModeloJuegoEntidades.Jugador;
import java.util.List;
import interfacesEntidades.IJugador;
import interfacesComunicacionModelo.IModeloJuego;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import interfacesComunicacionModelo.IControlVistaMVC_Juego;
import interfacesComunicacionModelo.IControlVistaMVC_Inicio;

public class ModeloJuego implements IModeloJuego {
    
    private IControlVistaMVC_Inicio controlVistaInicio;
    private IControlVistaMVC_Juego controlVistaJuego;
    private Jugador jugadorPrincipal;
    private List<IJugador> jugadoresSecundarios;
    private IEnvioEvento empaquetador;
    
    //Banderita
    private boolean host = false;

    public ModeloJuego() {
    }

    public ModeloJuego(IControlVistaMVC_Juego controlVistaJuego, Jugador jugadorPrincipal, List<IJugador> jugadoresSecundarios, IControlVistaMVC_Inicio controlVistaInicio) {
        this.controlVistaInicio = controlVistaInicio;
        this.controlVistaJuego = controlVistaJuego;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        controlVistaJuego.setJugadorPrincipal(this.jugadorPrincipal);
        controlVistaJuego.setJugadoresSecundarios(jugadoresSecundarios);

    }

    public ModeloJuego(IControlVistaMVC_Juego controlVistaJuego, Jugador jugadorPrincipal, List<IJugador> jugadoresSecundarios) {
        this.controlVistaJuego = controlVistaJuego;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        controlVistaJuego.setJugadorPrincipal(this.jugadorPrincipal);
        controlVistaJuego.setJugadoresSecundarios(jugadoresSecundarios);

    }

    public void setEmpaquetador(IEnvioEvento empaquetador) {
        this.empaquetador = empaquetador;
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

    
    //-------------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void actualizarCartaCantada(JSONObject datos){
        int numCarta = datos.getInt("IdCarta");
            String nombreCarta = datos.getString("Nombre");

            Carta cartaActual = new Carta(numCarta, nombreCarta);
            controlVistaJuego.actualizarCartaCantada(cartaActual);
    }
    
    @Override
    public void actualizarCasillaSeleccionada(JSONObject datos){
        int jugador = datos.getInt("Jugador");
            int casilla = datos.getInt("Casilla");

            if (jugadorPrincipal.getNumJugador() == jugador) {
                jugadorPrincipal.getTarjeta().marcarCasilla(casilla);

                controlVistaJuego.actualizarTarjetaJugadorPrincipal(jugadorPrincipal.getTarjeta().getMarcadas());
            } else {
                for (IJugador jugadoresSecundario : jugadoresSecundarios) {
                    if (jugadoresSecundario.getNumJugador() == jugador) {
                        jugadoresSecundario.getTarjeta().marcarCasilla(casilla);
                        controlVistaJuego.setJugadoresSecundarios(jugadoresSecundarios);
                    }
                }
            }
    }
    
    @Override
    public void actualizarConfiguracion(JSONObject datos){
        System.out.println("[FiltroUnirsePartida] → Evento ConfirmacionReglas recibido");

            // === 1) Leer datos del JSON ===
            String dificultad = datos.optString("Dificultad", null);
            int puntuacionMax = datos.optInt("PuntuacionMaxima", 0);

            // === 2) Convertir jugadores JSON -> Lista<IJugador> ===
            List<IJugador> listaJugadores = new ArrayList<>();

            if (datos.has("Jugadores")) {
                JSONArray arr = datos.getJSONArray("Jugadores");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    int id = obj.getInt("IdJugador");
                    String nombre = obj.getString("Nombre");
                    String avatar = obj.getString("Avatar");

                    listaJugadores.add(new Jugador(id, nombre, avatar));
                }
            }

            System.out.println("[FiltroUnirsePartida] Datos procesados:");
            System.out.println(" - Dificultad: " + dificultad);
            System.out.println(" - PuntMax: " + puntuacionMax);
            System.out.println(" - Jugadores: " + listaJugadores.size());

            // === 3) Llamar al ControlModeloVista según diagrama ===
            // controlVistaInicio.getControl() representa ControlModeloVista
            controlVistaInicio.actualizarPantalla(dificultad, listaJugadores, puntuacionMax);
    }

    @Override
    public void setControlVistaInicio(IControlVistaMVC_Inicio controlVistaInicio) {
        this.controlVistaInicio = controlVistaInicio;
    }

    @Override
    public void setControlVistaJuego(IControlVistaMVC_Juego controlVistaJuego) {
        this.controlVistaJuego = controlVistaJuego;
    }
    
}

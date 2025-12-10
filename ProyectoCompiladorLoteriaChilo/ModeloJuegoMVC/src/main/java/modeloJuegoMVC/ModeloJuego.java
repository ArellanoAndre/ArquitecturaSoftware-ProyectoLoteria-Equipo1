/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeloJuegoMVC;

import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import ModeloJuegoEntidades.Carta;
import ModeloJuegoEntidades.ConfiguracionPartida;
import ModeloJuegoEntidades.Jugador;
import java.util.List;
import interfacesEntidades.IJugador;
import interfacesComunicacionModelo.IModeloJuego;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import interfacesComunicacionModelo.IControlVistaMVC_Juego;
import interfacesComunicacionModelo.IControlVistaMVC_Inicio;
import java.util.Map;

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

    @Override
    public void enviarEventoJugar() {
        System.out.println("[ModeloJuego] → Enviando evento JUGAR");

        try {
            // Construir JSON
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "JUGAR");

            // Enviar usando el método centralizado
            enviarEventoBroadcast(json.toString(), "Juego-in");

            System.out.println("[ModeloJuego] JSON enviado: " + json.toString());

        } catch (Exception e) {
            System.err.println("[ModeloJuego] Error enviando JUGAR: " + e.getMessage());
        }
    }

    @Override
    public void EnviarEventoConfigurarPartida(String dificultad, int numJugadores,
            int puntuacionMax,
            Map<String, Integer> puntuaciones) {

        System.out.println("[ModeloJuego] → Enviando CONFIGURAR_PARTIDA al Host...");
        System.out.println(numJugadores);
        try {
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "CONFIGURAR_PARTIDA");
            json.put("Dificultad", dificultad);
            json.put("NumeroJugadores", numJugadores);
            json.put("PuntuacionMaxima", puntuacionMax);

            // Puntuaciones (Chorro, Cruz, Llena, etc.)
            JSONObject jsonPunts = new JSONObject();
            for (Map.Entry<String, Integer> entry : puntuaciones.entrySet()) {
                jsonPunts.put(entry.getKey(), entry.getValue());
            }
            json.put("Puntuaciones", jsonPunts);

            // Enviar por Juego-in al Host
            enviarEventoBroadcast(json.toString(), "Juego-in");
            host = true;
        } catch (Exception e) {
            System.err.println("[ModeloJuego] ERROR enviando CONFIGURAR_PARTIDA: " + e.getMessage());
        }
    }

    // Control Vista Seccion ---------
    //-------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void actualizarCartaCantada(JSONObject datos) {

        int numCarta = datos.getInt("IdCarta");
        String nombreCarta = datos.getString("Nombre");

        Carta cartaActual = new Carta(numCarta, nombreCarta);
        controlVistaJuego.actualizarCartaCantada(cartaActual);
    }

    @Override
    public void actualizarCasillaSeleccionada(JSONObject datos) {
        int idJugadorEvento = datos.getInt("Jugador");
        int casilla = datos.getInt("Casilla");

        System.out.println("[ModeloJuego] ActualizarCasilla -> Jugador: " + idJugadorEvento + ", Casilla: " + casilla);

        // 1. CASO JUGADOR PRINCIPAL (TÚ)
        if (jugadorPrincipal.getNumJugador() == idJugadorEvento) {
            System.out.println("   -> Es el jugador PRINCIPAL. Actualizando...");
            jugadorPrincipal.getTarjeta().marcarCasilla(casilla);
            controlVistaJuego.actualizarTarjetaJugadorPrincipal(jugadorPrincipal.getTarjeta().getMarcadas());
        } // 2. CASO JUGADORES SECUNDARIOS (RIVALES)
        else {
            System.out.println("   -> Es un jugador SECUNDARIO. Buscando en lista de tamaño: " + (jugadoresSecundarios != null ? jugadoresSecundarios.size() : "null"));

            boolean encontrado = false;
            if (jugadoresSecundarios != null) {
                for (IJugador rival : jugadoresSecundarios) {

                    if (rival.getNumJugador() == idJugadorEvento) {
                        System.out.println("   -> Rival ENCONTRADO (ID " + rival.getNumJugador() + "). Marcando casilla...");

                        // A. Actualizamos la entidad
                        rival.getTarjeta().marcarCasilla(casilla);
                        encontrado = true;

                        // B. Avisamos al controlador para que regenere la vista
                        // IMPORTANTE: Asegúrate de que ControlVista genera objetos NUEVOS al recibir esta lista
                        controlVistaJuego.setJugadoresSecundarios(jugadoresSecundarios);
                        break; // Ya lo encontramos, salimos del for
                    }
                }
            }

            if (!encontrado) {
                System.err.println("   -> [ALERTA] No se encontró ningún jugador secundario con ID: " + idJugadorEvento);
            }
        }
    }

    @Override
    public void actualizarConfiguracion(JSONObject datos) {

        System.out.println("[FiltroUnirsePartida] → Evento ConfirmacionReglas recibido");

        // === 1) Leer datos generales ===
        String dificultad = datos.optString("Dificultad", null);
        int puntuacionMax = datos.optInt("PuntuacionMaxima", 0);
        int numJugadores = datos.optInt("NumeroJugadores", 0);

        // === 2) Convertir jugadores JSON -> List<IJugador> ===
        List<IJugador> listaJugadores = new ArrayList<>();

        if (datos.has("Jugadores")) {
            JSONArray arr = datos.getJSONArray("Jugadores");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                int id = obj.getInt("IdJugador");
                String nombre = obj.getString("Nombre");
                String avatar = obj.getString("Avatar"); // ← YA SE LEE AVATAR

                listaJugadores.add(new Jugador(id, nombre, avatar));
            }
        }

        // === 3) Leer Tarjetas ===
        List<String> tarjetas = new ArrayList<>();

        if (datos.has("Tarjetas")) {
            JSONArray arrTarjetas = datos.getJSONArray("Tarjetas");

            for (int i = 0; i < arrTarjetas.length(); i++) {
                tarjetas.add(arrTarjetas.getString(i)); // Rutas de imagen
            }
        }

        System.out.println("[FiltroUnirsePartida] Datos procesados:");
        System.out.println(" - Dificultad: " + dificultad);
        System.out.println(" - PuntMax: " + puntuacionMax);
        System.out.println(" - Jugadores: " + listaJugadores.size());
        System.out.println(" - NumJugadores: " + numJugadores);
        System.out.println(" - Tarjetas: " + tarjetas.size());

        // === 4) Crear objeto ConfiguracionPartida ===
        ConfiguracionPartida configuracion = new ConfiguracionPartida();
        configuracion.setDatos(
                dificultad,
                listaJugadores,
                puntuacionMax,
                tarjetas,
                numJugadores
        );

        // === 5) Notificar a ControlModeloVista para actualizar pantalla ===
        controlVistaInicio.actualizarPantalla(configuracion);
    }

    @Override
    public void setControlVistaInicio(IControlVistaMVC_Inicio controlVistaInicio) {
        this.controlVistaInicio = controlVistaInicio;
    }

    @Override
    public void setControlVistaJuego(IControlVistaMVC_Juego controlVistaJuego) {
        this.controlVistaJuego = controlVistaJuego;
    }

    @Override
    public void abrirPantallaConfig() {
        controlVistaInicio.abrirPantallaConfig();
    }

    @Override
    public void abrirPantallaAvatar() {
        controlVistaInicio.abrirPantallaAvatar();
    }

    @Override
    public void notificarFinRonda(int ronda, String ganador) {

        if (controlVistaJuego != null) {
            controlVistaJuego.mostrarMensajeFinRonda(ronda, ganador);

        }
    }

    @Override
    public void notificarFinPartida(List<IJugador> ranking) {

        if (controlVistaJuego != null) {
            controlVistaJuego.procesarFinPartida(ranking);
        }
    }

    @Override
    public void EnviarEventoCantarLoteria() {

        if (empaquetador == null) {
            System.err.println("[ModeloJuego] Error: Empaquetador no inicializado.");
            return;
        }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setEvento("Juego");

        // El JSON que espera el Host
        evento.setJSON("{ \"TipoEvento\": \"INTENTO_LOTERIA\", \"Jugador\": " + jugadorPrincipal.getNumJugador() + " }"
        );

        empaquetador.enviarEvento(evento);
        System.out.println("[ModeloJuego] Enviado: INTENTO_LOTERIA por jugador" + jugadorPrincipal.getNumJugador());

    }

    @Override
    public void EnviarEventoIniciarSiguienteRonda() {

        if (empaquetador == null) {
            return;
        }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setEvento("Juego");

        evento.setJSON("{ \"TipoEvento\": \"INICIAR_SIGUIENTE_RONDA\" }");

        empaquetador.enviarEvento(evento);
        System.out.println("[ModeloJuego] Enviado: INICIAR_SIGUIENTE_RONDA");
    }

    @Override
    public void limpiarEntidadesJuego() {
        System.out.println("[ModeloJuego] Limpiando entidades internas...");

        // principal
        if (jugadorPrincipal != null && jugadorPrincipal.getTarjeta() != null) {
            boolean[] marcadas = jugadorPrincipal.getTarjeta().getMarcadas();
            if (marcadas != null) {
                java.util.Arrays.fill(marcadas, false);
            }
        }

        // secundarios
        if (jugadoresSecundarios != null) {
            for (IJugador j : jugadoresSecundarios) {
                if (j.getTarjeta() != null) {
                    boolean[] marcadas = j.getTarjeta().getMarcadas();
                    if (marcadas != null) {
                        java.util.Arrays.fill(marcadas, false);
                    }
                }
            }
        }
    }
}

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
import ModeloJuegoEntidades.Tarjeta;
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
    private List<Tarjeta> todasLasTarjetas;

    //Banderita
    private boolean host = false;
    private boolean registrado = false;

    public ModeloJuego() {
        this.todasLasTarjetas = GenerarTarjetas();
    }

    public ModeloJuego(IControlVistaMVC_Juego controlVistaJuego, Jugador jugadorPrincipal, List<IJugador> jugadoresSecundarios) {
        this.controlVistaJuego = controlVistaJuego;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        this.todasLasTarjetas = GenerarTarjetas();
        controlVistaJuego.setJugadorPrincipal(this.jugadorPrincipal);
        controlVistaJuego.setJugadoresSecundarios(jugadoresSecundarios);
    }

    public void setEmpaquetador(IEnvioEvento empaquetador) {
        this.empaquetador = empaquetador;
    }

    @Override
    public int ObtenerIDJugadorPrincipal() {
        return jugadorPrincipal.getNumJugador();
    }

    public Jugador getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public List<IJugador> getJugadoresSecundarios() {
        return jugadoresSecundarios;
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
    public void suscribirseJuegoOut() {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "suscripcion");

        // Usamos el mismo método que ya centraliza el envío
        enviarEventoBroadcast(json.toString(), "Juego-out", "suscripcion");

        System.out.println("[Cliente] Suscripción enviada a Juego-out");
    }

    @Override
    public void AsignarID() {

        System.out.println("[ModeloJuego] → Solicitando ID al Host...");

        try {
            // Construir JSON del evento
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "ASIGNAR_ID");

            // Enviar al Host por Juego-in
            enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");

            System.out.println("[ModeloJuego] Evento ASIGNAR_ID enviado al Host.");

        } catch (Exception e) {
            System.err.println("[ModeloJuego] Error enviando ASIGNAR_ID: " + e.getMessage());
        }
    }

    @Override
    public void guardarIDAsignado(int idJugador) {

        // Si ya tenemos ID → NO reasignar
        if (jugadorPrincipal != null && jugadorPrincipal.getNumJugador() != 0) {
            System.out.println("[ModeloJuego] ❌ Ignorado: Ya tenía ID = "
                    + jugadorPrincipal.getNumJugador());
            return; // ← EVITA REASIGNACIÓN
        }

        System.out.println("[ModeloJuego] → Guardando ID asignado por el Host: " + idJugador);

        // Crear jugador si no existe
        if (jugadorPrincipal == null) {
            jugadorPrincipal = new Jugador();
        }

        // Asignar ID UNA sola vez
        jugadorPrincipal.setNumJugador(idJugador);

        // Abrir pantalla correspondiente
        controlVistaInicio.abrirPantallaMenu(idJugador);

        System.out.println("[ModeloJuego] ✔ ID FIJADO = " + jugadorPrincipal.getNumJugador());
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
    public void asignarTarjeta(JSONObject datos) {
        int numJugador = datos.optInt("idJugador");
        String rutaTarjeta = datos.getString("rutaTarjeta");

        Tarjeta tarjeta = todasLasTarjetas.stream()
                .filter(t -> t.getImg().equals(rutaTarjeta))
                .findFirst()
                .orElse(null);

        if (jugadorPrincipal.getNumJugador() == numJugador) {
            jugadorPrincipal.setTarjeta(tarjeta);
        } else {
            for (IJugador jugadorSecundario : jugadoresSecundarios) {
                if (jugadorSecundario.getNumJugador() == numJugador) {
                    jugadorSecundario.setTarjeta(tarjeta);
                }
            }
        }
    }

    @Override
    public void actualizarConfiguracion(JSONObject datos) {

        System.out.println("[FiltroUnirsePartida] → Evento ConfirmacionReglas recibido");

        // === 1) Leer datos generales ===
        String dificultad = datos.optString("Dificultad", null);
        int numRondas = datos.optInt("NumeroRondas", 0);
        int numJugadores = datos.optInt("NumeroJugadores", 0);

        // === 2) Convertir jugadores JSON -> List<IJugador> ===
        List<IJugador> listaJugadores = new ArrayList<>();

        if (datos.has("Jugadores")) {
            JSONArray arr = datos.getJSONArray("Jugadores");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);

                int id = obj.optInt("IdJugador", -1);
                String nombre = obj.optString("Nombre", "Jugador_" + id);
                String avatar = obj.optString("Avatar", "default.png");

                System.out.println("[DEBUG] Jugador recibido: id=" + id
                        + ", nombre=" + nombre
                        + ", avatar=" + avatar);

                listaJugadores.add(new Jugador(id, nombre, avatar));
            }
        }

        settearJugadores(listaJugadores);

        // === ⛔ VALIDAR SI ESTE EVENTO INCLUYE AL JUGADOR LOCAL ===
        boolean jugadorEncontrado = false;

        for (IJugador j : listaJugadores) {
            if (j.getNumJugador() == jugadorPrincipal.getNumJugador()) {
                jugadorEncontrado = true;
                break;
            }
        }

        if (!jugadorEncontrado) {
            System.out.println("[FiltroUnirsePartida] ⚠ Este evento NO es para mí. No cambio pantalla.");
            return; // ← IMPORTANTE: NO continuar
        }

        System.out.println("[FiltroUnirsePartida] ✔ Mi jugador SI está en la lista. Cambio de pantalla.");

        // === 3) Leer Tarjetas ===
        List<String> tarjetas = new ArrayList<>();

        if (datos.has("Tarjetas")) {
            JSONArray arrTarjetas = datos.getJSONArray("Tarjetas");

            for (int i = 0; i < arrTarjetas.length(); i++) {
                tarjetas.add(arrTarjetas.getString(i));
            }
        }

        // === 4) Crear objeto ConfiguracionPartida ===
        ConfiguracionPartida configuracion = new ConfiguracionPartida();
        configuracion.setDatos(
                dificultad,
                listaJugadores,
                numRondas,
                tarjetas,
                numJugadores
        );

        // === 5) Solo si coincide el jugador → actualizar pantalla ===
        controlVistaInicio.actualizarPantalla(configuracion);
    }

    @Override
    public void abrirPantallaConfig(int id) {
        controlVistaInicio.abrirPantallaConfig(id);
    }

    @Override
    public void abrirPantallaAvatar(int id) {
        if (!registrado) {
            controlVistaInicio.abrirPantallaAvatar(id);
            registrado = true;
        }
    }

    @Override
    public void notificarFinRonda(int ronda, String ganador) {

        if (controlVistaJuego != null) {
            controlVistaJuego.mostrarMensajeFinRonda(ronda, ganador);
        }
    }

    @Override
    public void notificarFinRondaBaraja() {
        if (controlVistaJuego != null) {
            controlVistaJuego.mostrarMensajeFinRondaBaraja();
        }
    }

    @Override
    public void notificarFinPartida(List<IJugador> ranking) {

        if (controlVistaJuego != null) {
            controlVistaJuego.procesarFinPartida(ranking);
        }
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

    @Override
    public void cambiarMVC() {
        System.out.println("Modelo de Juego llamando a Modelo Vista para actualizr MVC");
        controlVistaJuego.setJugadorPrincipal(jugadorPrincipal);
        controlVistaJuego.setJugadoresSecundarios(jugadoresSecundarios);
        controlVistaJuego.cambiarMVC();
        controlVistaInicio.cambiarMVC();

        if (host) {
            enviarEventoIniciarRonda();
        }

    }

    @Override
    public void enviarEventoCartaSeleccionada(int pos, int jugador) {
        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CASILLA_SELECCIONADA");
        json.put("Jugador", jugadorPrincipal.getNumJugador());
        json.put("Casilla", pos);

        enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");
    }

    @Override
    public void enviarEventoIniciarRonda() {
        JSONObject json = new JSONObject();
        json.put("TipoEvento", "INICIAR_RONDA");

        enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");
    }

    @Override
    public void enviarNombreAvatarConfirmado(int id, String nombre, String avatar) {

        System.out.println("[ModeloJuego] → Enviando evento UNIRSE_PARTIDA");

        Jugador jugadorP = new Jugador();
        jugadorP.setNombre(nombre);
        jugadorP.setAvatar(avatar);

        jugadorP.setNumJugador(id);

        try {
            // 1) Construir JSON del evento
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "UNIRSE_PARTIDA");
            json.put("Nombre", nombre);
            json.put("Avatar", avatar);

            // 2) Usar el método general de envío
            enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");

            System.out.println("[ModeloJuego] JSON enviado: " + json.toString());

        } catch (Exception e) {
            System.err.println("[ModeloJuego] Error enviando UnirsePartida: " + e.getMessage());
        }
    }

    @Override
    public void enviarTarjetaSeleccionada(String tarjetaRuta) {
        try {
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "SET_TARJETA");
            json.put("Jugador", jugadorPrincipal.getNumJugador());
            json.put("Tarjeta", tarjetaRuta);

            System.out.println("info enviada: " + jugadorPrincipal.getNumJugador() + tarjetaRuta);
            enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");

        } catch (Exception e) {
            System.err.println("[ModeloJuego] Error asignando tarjeta: " + e.getMessage());
        }
    }

    @Override
    public void enviarEventoJugar() {
        System.out.println("[ModeloJuego] → Enviando evento JUGAR");

        try {
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "JUGAR");
            json.put("IdJugador", jugadorPrincipal.getNumJugador()); // ← ID limpio

            enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");

            System.out.println("[ModeloJuego] JSON enviado: " + json.toString());

        } catch (Exception e) {
            System.err.println("[ModeloJuego] Error enviando JUGAR: " + e.getMessage());
        }
    }

    @Override
    public void enviarEventoConfigurarPartida(String dificultad, int numJugadores,
            int numRondas,
            Map<String, Integer> puntuaciones) {

        System.out.println("[ModeloJuego] → Enviando CONFIGURAR_PARTIDA al Host...");
        System.out.println(numJugadores);
        try {
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "CONFIGURAR_PARTIDA");
            json.put("Dificultad", dificultad);
            json.put("NumeroJugadores", numJugadores);
            json.put("NumeroRondas", numRondas);

            // Puntuaciones (Chorro, Cruz, Llena, etc.)
            JSONObject jsonPunts = new JSONObject();
            for (Map.Entry<String, Integer> entry : puntuaciones.entrySet()) {
                jsonPunts.put(entry.getKey(), entry.getValue());
            }
            json.put("Puntuaciones", jsonPunts);

            // Enviar por Juego-in al Host
            enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");
            host = true;
        } catch (Exception e) {
            System.err.println("[ModeloJuego] ERROR enviando CONFIGURAR_PARTIDA: " + e.getMessage());
        }
    }

    @Override
    public void enviarEventoCantarLoteria() {

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
    public void enviarEventoIniciarSiguienteRonda() {

        if (empaquetador == null) {
            return;
        }

        System.out.println(" ================================================================ Vamo a pedir otra ronda");
        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setEvento("Juego");

        evento.setJSON("{ \"TipoEvento\": \"INICIAR_SIGUIENTE_RONDA\" }");

        System.out.println("[ModeloJuego] Enviando: INICIAR_SIGUIENTE_RONDA");
        empaquetador.enviarEvento(evento);

    }

    @Override
    public void enviarEventoCambiarMVC() {
        try {
            JSONObject json = new JSONObject();
            json.put("TipoEvento", "CAMBIAR_MVC");

            enviarEventoBroadcast(json.toString(), "Juego-in", "ActualizacionJuego");

        } catch (Exception e) {
            System.err.println("[ModeloJuego] Error asignando tarjeta: " + e.getMessage());
        }
    }

    @Override
    public void enviarEventoBroadcast(String jsonPayload, String topico, String Evento) {

        if (empaquetador == null) {
            return;
        }

        try {
            // Convertimos el JSON original
            JSONObject json = new JSONObject(jsonPayload);

            // Agregamos el ID del jugador SI YA EXISTE
            if (jugadorPrincipal != null && jugadorPrincipal.getNumJugador() > 0) {
                json.put("ID", jugadorPrincipal.getNumJugador());
            } else {
                System.out.println("[ModeloJuego] Advertencia: el jugador aún NO tiene ID asignado.");
            }

            // Construimos el evento
            IEvento evento = empaquetador.crearEvento();
            evento.setTopico(topico);
            evento.setEvento(Evento);
            evento.setJSON(json.toString()); // JSON final con IdJugador agregado

            // Enviar al broker
            empaquetador.enviarEvento(evento);

            System.out.println("[ModeloJuego] Enviado evento con ID incluido: " + json.toString());

        } catch (Exception e) {
            System.err.println("[ModeloJuego] ERROR en enviarEventoBroadcast: " + e.getMessage());
        }
    }

    public void settearJugadores(List<IJugador> listaJugadores) {

        if (jugadoresSecundarios == null) {
            jugadoresSecundarios = new ArrayList<>();
        }

        System.out.println("[settearJugadores] Iniciando actualización...");

        // Limpia secundarios al inicio para evitar duplicados
        jugadoresSecundarios.clear();

        int idPrincipal = jugadorPrincipal.getNumJugador();

        System.out.println("[settearJugadores] ID jugador principal actual: " + idPrincipal);

        for (IJugador iJugador : listaJugadores) {

            System.out.println("  → Procesando jugador: ID=" + iJugador.getNumJugador()
                    + ", Nombre=" + iJugador.getNombre()
                    + ", Avatar=" + iJugador.getAvatar());

            if (iJugador.getNumJugador() == idPrincipal) {

                System.out.println("    ✔ Coincide con jugador PRINCIPAL → Actualizando");

                jugadorPrincipal.setNombre(iJugador.getNombre());
                jugadorPrincipal.setAvatar(iJugador.getAvatar());

            } else {

                System.out.println("    ➕ Agregado a secundarios");

                jugadoresSecundarios.add(iJugador);
            }
        }

        // Mostrar resultado final
        System.out.println("[settearJugadores] Jugadores secundarios (" + jugadoresSecundarios.size() + "):");
        for (IJugador sec : jugadoresSecundarios) {
            System.out.println("    - ID=" + sec.getNumJugador()
                    + ", Nombre=" + sec.getNombre()
                    + ", Avatar=" + sec.getAvatar());
        }

        System.out.println("[settearJugadores] Finalizado.");
    }

    public List<Tarjeta> GenerarTarjetas() {
        // Creamos la lista completa con los 54 tableros (01 al 54)

        List<Tarjeta> todosLosTableros = new ArrayList<>();

        todosLosTableros.add(new Tarjeta(
                new int[]{46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26},
                "/img/Tableros/Tablero01.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36},
                "/img/Tableros/Tablero02.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{41, 51, 16, 48, 26, 53, 28, 31, 42, 33, 34, 27, 6, 37, 39, 8},
                "/img/Tableros/Tablero03.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{13, 32, 42, 43, 23, 48, 2, 15, 26, 33, 17, 49, 8, 18, 45, 46},
                "/img/Tableros/Tablero04.png"
        ));

        todosLosTableros.add(new Tarjeta(
                new int[]{11, 13, 16, 48, 32, 17, 7, 52, 22, 45, 23, 27, 6, 47, 53, 29},
                "/img/Tableros/Tablero05.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{42, 21, 50, 14, 9, 5, 1, 3, 52, 12, 25, 54, 24, 41, 35, 43},
                "/img/Tableros/Tablero06.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{12, 2, 27, 32, 3, 31, 37, 24, 41, 44, 38, 47, 16, 21, 30, 29},
                "/img/Tableros/Tablero07.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{24, 10, 47, 44, 28, 40, 27, 32, 19, 31, 30, 39, 49, 26, 7, 33},
                "/img/Tableros/Tablero08.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{36, 22, 37, 35, 6, 46, 53, 52, 4, 31, 25, 45, 16, 50, 5, 2},
                "/img/Tableros/Tablero09.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{3, 51, 48, 40, 10, 28, 53, 34, 42, 38, 47, 52, 43, 54, 9, 44},
                "/img/Tableros/Tablero10.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{8, 17, 36, 32, 19, 20, 14, 31, 49, 37, 51, 39, 4, 46, 23, 22},
                "/img/Tableros/Tablero11.png"
        ));
        todosLosTableros.add(new Tarjeta(
                new int[]{39, 23, 46, 51, 6, 53, 11, 45, 9, 54, 50, 18, 28, 33, 27, 49},
                "/img/Tableros/Tablero12.png"
        ));

        return todosLosTableros;
    }

    @Override
    public boolean isRegistrado() {
        return registrado;
    }

    @Override
    public void setRegistrado(boolean registrado) {
        this.registrado = registrado;
    }

    @Override
    public boolean isHost() {
        return host;
    }

    @Override
    public void setHost(boolean host) {
        this.host = host;
    }

}

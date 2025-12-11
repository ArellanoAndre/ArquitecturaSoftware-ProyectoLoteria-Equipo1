package modeloJuego;

import Evento.Evento;
import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import interfacesEntidades.IJugador;
import interfacesLogica.IModeloLogica;
import java.util.List;
import logicaJuego.LogicaDeJuego;
import logicaJuego.entidades.Jugador;
import org.json.JSONArray;
import org.json.JSONObject;
import procesarCadena.IProcesadorEvento;
import procesarCadena.ProcesadorConfigurarPartida;
import procesarCadena.ProcesadorIniciar;
import procesarCadena.ProcesadorIniciarRonda;
import procesarCadena.ProcesadorInicioSiguienteRonda;
import procesarCadena.ProcesadorIntentoLoteria;
import procesarCadena.ProcesadorMarcar;
import procesarCadena.ProcesadorUnirse;
import procesarCadena.ProcesadorUnirsePartida;
import procesarCadena.ProcesarJugar;

public class ModeloLogica implements IModeloLogica, IReceptorEvento {

    private LogicaDeJuego logicaDeJuego;
    private IEnvioEvento empaquetador;

    private IProcesadorEvento cadenaProcesamiento;

    public ModeloLogica() {
        // armar cadena de responsabilidad aqui
        IProcesadorEvento iniciarRonda = new ProcesadorIniciarRonda();
        IProcesadorEvento marcar = new ProcesadorMarcar();
        IProcesadorEvento unirse = new ProcesadorUnirse();
        IProcesadorEvento iniciar = new ProcesadorIniciar();
        IProcesadorEvento unirsePartida = new ProcesadorUnirsePartida();
        IProcesadorEvento Jugar = new ProcesarJugar();
        IProcesadorEvento configurar = new ProcesadorConfigurarPartida();
        IProcesadorEvento intentoLoteria = new ProcesadorIntentoLoteria();
        IProcesadorEvento inicioSiguienteRonda = new ProcesadorInicioSiguienteRonda();

        // Conectamos los eslabones
        iniciarRonda.setSiguiente(marcar);
        marcar.setSiguiente(unirse);
        unirse.setSiguiente(iniciar);
        iniciar.setSiguiente(unirsePartida);
        unirsePartida.setSiguiente(Jugar);
        Jugar.setSiguiente(configurar);
        configurar.setSiguiente(intentoLoteria);
        intentoLoteria.setSiguiente(inicioSiguienteRonda);
        this.cadenaProcesamiento = iniciarRonda; // Guardamos la referencia al primero
    }

    public void setLogicaDeJuego(LogicaDeJuego logica) {
        this.logicaDeJuego = logica;
    }

    public void setEmpaquetador(IEnvioEvento empaquetador) {
        this.empaquetador = empaquetador;
    }

    public LogicaDeJuego getLogicaDeJuego() {
        return logicaDeJuego;
    }

    public IEnvioEvento getEmpaquetador() {
        return empaquetador;
    }

    //================================================== Suscripciones ==================================================
    
    public void suscribirseJuegoIn() {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "suscripcion");

        // Usamos el mismo método que ya centraliza el envío
        enviarEventoBroadcast(json.toString(), "Juego-in", "suscripcion");

        System.out.println("[Cliente] Suscripción enviada a Juego-out");
    }
    
    //================================================== Notificaciones ==================================================
    @Override
    public void notificarCartaCantada(int idCarta, String nombreCarta) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CARTA_CANTADA");
        json.put("IdCarta", idCarta);
        json.put("Nombre", nombreCarta);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

    }

    @Override
    public void notificarJugadaValida(int posicion, int idJugador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CASILLA_SELECCIONADA");
        json.put("Jugador", idJugador);
        json.put("Casilla", posicion);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

    }

    @Override
    public void notificarGanador(String nombreGanador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "GANADOR");
        json.put("Nombre", nombreGanador);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

    }

    @Override
    public void manejar(IEvento evento) {

        String jsonPayload = evento.getJSON();
        System.out.println("ModeloLogica: Evento recibido: " + jsonPayload);

        if (logicaDeJuego == null) {
            System.out.println("logicajuego nula");
            return;
        }

        try {
            JSONObject peticion = new JSONObject(jsonPayload);

            if (peticion.has("TipoEvento")) {
                String tipo = peticion.getString("TipoEvento");
                cadenaProcesamiento.procesar(tipo, peticion, logicaDeJuego);
            }
        } catch (Exception e) {
            System.err.println("Error procesando entrada" + e.getMessage());
        }
    }

    @Override
    public void notificarFinDeRonda(int ronda, String ganadorRonda) {

        JSONObject json = new JSONObject();

        json.put("TipoEvento", "FIN_RONDA");

        json.put("Ronda", ronda);
        json.put("Ganador", ganadorRonda);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

        System.out.println("[ModeloLogica] Enviado FIN_RONDA: " + json.toString());

    }

    @Override
    public void notificarFinPartida(List<Jugador> ranking) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "FIN_PARTIDA");

        JSONArray arrayRanking = new JSONArray();

        for (Jugador j : ranking) {
            JSONObject jugadorJson = new JSONObject();

            jugadorJson.put("Id", j.getNumJugador());
            jugadorJson.put("Nombre", j.getNombre());
            jugadorJson.put("Puntos", j.getPuntaje());

            String avatar = (j.getAvatar() != null) ? j.getAvatar() : "avatar1.png"; // Default si es null
            jugadorJson.put("Avatar", avatar); // por mientras pq si no tira error

            arrayRanking.put(jugadorJson);
        }

        json.put("Ranking", arrayRanking);
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

        System.out.println("[ModeloLogica] Enviado FIN_PARTIDA: " + json.toString());
    }

    //================================================== Envío de Eventos ==================================================
    
    @Override
    public void EnviarEventoCantarLoteria() {

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setJSON("{ \"TipoEvento\": \"INTENTO_LOTERIA\" }");

        empaquetador.enviarEvento(evento);

    }

    @Override
    public void EnviarEventoIniciarSiguienteRonda() {
        IEvento evento = empaquetador.crearEvento();
        evento.setTopico("Juego-in");
        evento.setJSON("{ \"TipoEvento\": \"INICIAR_SIGUIENTE_RONDA\" }");

        empaquetador.enviarEvento(evento);

    }

    @Override
    public void enviarAbrirPantallaSeleccionAvatar() {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "UNIRSE_PARTIDA");

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

        System.out.println("[Host] → Indicando al cliente que abra SELECCION DE AVATAR");
    }

    @Override
    public void enviarAbrirPantallaConfig() {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CONFIGURAR_PARTIDA");

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

        System.out.println("[Host] → Indicando al cliente que abra CONFIGURAR PARTIDA");
    }

    @Override
    public void enviarConfirmacionReglas(
            String dificultad,
            int numRondas,
            List<Jugador> jugadores,
            int numJugadores,
            List<String> tarjetas) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "Confirmacion_Reglas");
        json.put("Dificultad", dificultad);
        json.put("NumeroRondas", numRondas);
        json.put("NumeroJugadores", numJugadores);

        // -------------------------------------------------------
        // Construir arreglo JSON "Jugadores"
        // -------------------------------------------------------
        JSONArray arrJugadores = new JSONArray();

        for (Jugador j : jugadores) {
            JSONObject jugadorJson = new JSONObject();
            jugadorJson.put("IdJugador", j.getNumJugador());
            jugadorJson.put("Nombre", j.getNombre());
            jugadorJson.put("Avatar", j.getAvatar());
            arrJugadores.put(jugadorJson);
        }

        json.put("Jugadores", arrJugadores);

        // -------------------------------------------------------
        // Construir arreglo JSON "Tarjetas" (lista de imágenes)
        // -------------------------------------------------------
        JSONArray arrTarjetas = new JSONArray();

        if (tarjetas != null) {
            for (String ruta : tarjetas) {
                arrTarjetas.put(ruta);
            }
        }

        json.put("Tarjetas", arrTarjetas);

        // -------------------------------------------------------
        // Enviar evento a todos los clientes
        // -------------------------------------------------------
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego");

        System.out.println("[ModeloLogica] → Enviando ConfirmacionReglas: " + json.toString());
    }

    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {

        System.out.println("[ModeloLogica-Host] Reenviando confirmación de selección...");
        notificarJugadaValida(pos, idJugador);
    }

    private void enviarEventoBroadcast(String jsonPayload, String topico, String Evento) { // para no repetir codigo

        if (empaquetador == null) {
            return;
        }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico(topico);
        evento.setEvento(Evento);
        evento.setJSON(jsonPayload);

        empaquetador.enviarEvento(evento);
        System.out.println("[ModeloLogica-Host] Enviado evento: " + jsonPayload);

    }

}

package modeloJuego;

import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import interfacesLogica.IModeloLogica;
import java.util.List;
import logicaJuego.LogicaDeJuego;
import logicaJuego.entidades.Jugador;
import org.json.JSONArray;
import org.json.JSONObject;
import procesarCadena.IProcesadorEvento;
import procesarCadena.ProcesadorAsignarID;
import procesarCadena.ProcesadorConfigurarPartida;
import procesarCadena.ProcesadorIniciar;
import procesarCadena.ProcesadorIniciarPartida;
import procesarCadena.ProcesadorIniciarRonda;
import procesarCadena.ProcesadorInicioSiguienteRonda;
import procesarCadena.ProcesadorIntentoLoteria;
import procesarCadena.ProcesadorMarcar;
import procesarCadena.ProcesadorSetTarjeta;
import procesarCadena.ProcesadorUnirsePartida;
import procesarCadena.ProcesadorValidarJugada;
import procesarCadena.ProcesarJugar;

public class ModeloLogica implements IModeloLogica, IReceptorEvento {

    private LogicaDeJuego logicaDeJuego;
    private IEnvioEvento empaquetador;
    private IProcesadorEvento cadenaProcesamiento;
   int test = -1;

    public ModeloLogica() {
        // armar cadena de responsabilidad aqui
        IProcesadorEvento iniciarRonda = new ProcesadorIniciarRonda();
        IProcesadorEvento marcar = new ProcesadorMarcar();
        IProcesadorEvento iniciar = new ProcesadorIniciar();
        IProcesadorEvento unirsePartida = new ProcesadorUnirsePartida();
        IProcesadorEvento Jugar = new ProcesarJugar();
        IProcesadorEvento configurar = new ProcesadorConfigurarPartida();
        IProcesadorEvento intentoLoteria = new ProcesadorIntentoLoteria();
        IProcesadorEvento inicioSiguienteRonda = new ProcesadorInicioSiguienteRonda();
        IProcesadorEvento iniciarPartida = new ProcesadorIniciarPartida();
        IProcesadorEvento settearTarjeta = new ProcesadorSetTarjeta(); 
        IProcesadorEvento asignarid = new ProcesadorAsignarID();
        IProcesadorEvento validarJugada = new ProcesadorValidarJugada();
        

        // Conectamos los eslabones
        asignarid.setSiguiente(iniciarRonda);
        iniciarRonda.setSiguiente(marcar);
        marcar.setSiguiente(iniciar);
        iniciar.setSiguiente(unirsePartida);
        unirsePartida.setSiguiente(Jugar);
        Jugar.setSiguiente(configurar);
        configurar.setSiguiente(intentoLoteria);
        intentoLoteria.setSiguiente(inicioSiguienteRonda);
        inicioSiguienteRonda.setSiguiente(iniciarPartida);
        iniciarPartida.setSiguiente(settearTarjeta);
        iniciarPartida.setSiguiente(settearTarjeta);
        settearTarjeta.setSiguiente(validarJugada); 
        
        this.cadenaProcesamiento = asignarid; // Guardamos la referencia al primero
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
        enviarEventoBroadcast(json.toString(), "Juego-in", "suscripcion",test);

        System.out.println("[Cliente] Suscripción enviada a Juego-out");
    }
    
    //================================================== Notificaciones ==================================================
    @Override
    public void notificarCartaCantada(int idCarta, String nombreCarta) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CARTA_CANTADA");
        json.put("IdCarta", idCarta);
        json.put("Nombre", nombreCarta);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);

    }

    @Override
    public void notificarJugadaValida(int posicion, int idJugador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CASILLA_SELECCIONADA");
        json.put("Jugador", idJugador);
        json.put("Casilla", posicion);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);

    }

    @Override
    public void notificarGanador(String nombreGanador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "GANADOR");
        json.put("Nombre", nombreGanador);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);

    }

    @Override
    public void notificarFinDeRonda(int ronda, String ganadorRonda) {

        JSONObject json = new JSONObject();

        json.put("TipoEvento", "FIN_RONDA");

        json.put("Ronda", ronda);
        json.put("Ganador", ganadorRonda);

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);

        System.out.println("[ModeloLogica] Enviado FIN_RONDA: " + json.toString());

    }
    
    @Override
    public void notificarFinDeRondaPorBaraja(){
        JSONObject json = new JSONObject();
        
        json.put("TipoEvento", "FIN_RONDA_BARAJA");
        
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);
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
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);

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
    public void enviarAbrirPantallaSeleccionAvatar(int idJugador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "SELECCION_AVATAR");

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",idJugador);

        System.out.println("[Host] → Indicando al cliente que abra SELECCION DE AVATAR");
    }

    @Override
    public void enviarAbrirPantallaConfig(int idJugador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CONFIGURAR_PARTIDA");

        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego", idJugador);

        System.out.println("[Host] → Indicando al cliente que abra CONFIGURAR PARTIDA");
    }

    @Override
    public void enviarConfirmacionReglas(
            String dificultad,
            int numRondas,
            List<Jugador> jugadores,
            int numJugadores,
            List<String> tarjetas,int id) {

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
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",id);

        System.out.println("[ModeloLogica] → Enviando ConfirmacionReglas: " + json.toString());
    }

    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {

        System.out.println("[ModeloLogica-Host] Reenviando confirmación de selección...");
        notificarJugadaValida(pos, idJugador);
    }

    private void enviarEventoBroadcast(String jsonPayload, String topico, String Evento, int idJugador) {

    if (empaquetador == null) {
        System.err.println("[ModeloJuego] ERROR: empaquetador no inicializado.");
        return;
    }

    try {
        // Convertimos el JSON original para agregar el ID
        JSONObject json = new JSONObject(jsonPayload);
        json.put("ID", idJugador); // ← Insertamos el ID

        // Crear el evento real
        IEvento evento = empaquetador.crearEvento();
        evento.setTopico(topico);
        evento.setEvento(Evento);
        evento.setJSON(json.toString());

        // Enviar al broker
        empaquetador.enviarEvento(evento);

        System.out.println("[ModeloJuego] Enviado evento (con ID): " + json.toString());

    } catch (Exception e) {
        System.err.println("[ModeloJuego] ERROR en enviarEventoBroadcast: " + e.getMessage());
    }
}

    @Override
    public void enviarEventoCambiarMVC(){
        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CAMBIAR_MVC");
        
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);
    }
    

    
    @Override
    public void enviarTarjetaAsignada(int idJugador, String rutaTarjeta) {
        JSONObject json = new JSONObject();
        json.put("TipoEvento", "TARJETA_ASIGNADA");
        json.put("idJugador", idJugador);
        json.put("rutaTarjeta", rutaTarjeta);
        
        enviarEventoBroadcast(json.toString(), "Juego-out", "ActualizacionJuego",test);
        
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

    public void enviarIDAsignadoAlCliente(int idJugadorAsignado) {

    System.out.println("[LogicaDeJuego] → Enviando ID_ASIGNADO al cliente " + idJugadorAsignado);

    // JSON que se enviará al cliente
    JSONObject json = new JSONObject();
    json.put("TipoEvento", "ID_ASIGNADO");
    // NOTA: también agregaremos IdJugador automáticamente dentro del broadcast

    // Usamos tu método centralizado
    enviarEventoBroadcast(
        json.toString(),
        "Juego-out",            // tópico hacia los clientes
        "ActualizacionJuego",   // etiqueta del tipo de evento
        idJugadorAsignado       // ← este ID se añadirá al JSON automáticamente
    );
}

    @Override
    public void EnviarEventoResultadoValidacion(int idJugador, int puntuacion) {
        JSONObject json = new JSONObject();
        json.put("TipoEvento", "JUGADA_VALIDA");
        json.put("Jugador", idJugador);
        json.put("Puntuacion", puntuacion);

        enviarEventoBroadcast(
            json.toString(),
            "Juego-out",           
            "ActualizacionJuego",  
            idJugador              
        );
    }
    

}

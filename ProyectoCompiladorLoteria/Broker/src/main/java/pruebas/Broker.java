package pruebas;

import Objetos.Evento;
import Util.JSONHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONObject;
import red.RedComponent;

public class Broker {

    private static final int PUERTO_BROKER = 6000;
//    private static final String HOST_LOGICA = "localhost"; // o la IP donde corre la lógica
//    private static final int PUERTO_LOGICA = 7000;
//    private static RedComponent redALogica; // canal de salida hacia la logica

    public static void main(String[] args) {
        System.out.println("[Broker] Escuchando en puerto " + PUERTO_BROKER);
        // Configurar RedComponent hacia la lógica del juego
//        redALogica = new RedComponent(HOST_LOGICA, PUERTO_LOGICA, 7001); // 7001 es el puerto local del Broker
//        redALogica.iniciar();

        // Escuchar mensajes de la logica (respuestas) AGREGAR
//        redALogica.registrarListener(json -> {
//            System.out.println("[Broker] Respuesta desde lógica: " + json);
//            reenviarAlCliente(json);
//        });
        try (ServerSocket server = new ServerSocket(PUERTO_BROKER)) {
            while (true) {
                Socket socket = server.accept();
                new Thread(() -> manejarCliente(socket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void manejarCliente(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json = in.readLine();
            System.out.println("[Broker] Evento entrante: " + json);

            // Procesar el evento y generar respuesta tipo PONG
            String respuesta = procesarEvento(json);

            // Enviar respuesta al cliente (a su puerto de escucha)
            enviarRespuesta("localhost", 6001, respuesta);

        } catch (IOException e) {
            System.err.println("[Broker] Error al manejar cliente: " + e.getMessage());
        }
    }

    private static void enviarRespuesta(String host, int puerto, String json) {
        try (Socket socket = new Socket(host, puerto); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(json);
            System.out.println("[Broker] Enviado evento saliente: " + json);
        } catch (IOException e) {
            System.err.println("[Broker] Error al enviar respuesta: " + e.getMessage());
        }
    }

    // AQUI DEBERIA DE LLAMAR A LA LOGICA DE JUEGO PARA PROCESAR EL EVENTO ESTO ES TEMPORAL
    private static String procesarEvento(String json) {
    try {
        // 1. Convertir el JSON en un objeto Evento usando JSONHelper
        Evento evento = JSONHelper.jsonAEvento(json);

        if (evento == null) {
            return "{\"error\":\"Evento no válido\"}";
        }

        // 3. NOMAS CHECA QUE SI LA POSICION ES ENTRE 1 Y 4 ES VALIDA SI NO NO, AGREGAR MANDAR A LLAMAR LOGICA AQUI PARA QUE VERIFIQUE
        JSONObject respuesta = new JSONObject();
        respuesta.put("tipo", "RESPUESTA_VALIDACION");
        respuesta.put("idJugador", evento.getIdJugador());

        if (evento.getPosicion() >= 1 && evento.getPosicion() <= 4) {
            respuesta.put("mensaje", "Seleccion Valida");
        } else {
            respuesta.put("mensaje", "Seleccion Invalida");
        }

        // 4. Devolver el JSON de respuesta
        return respuesta.toString();

    } catch (Exception e) {
        System.err.println("[Broker] Error procesando evento: " + e.getMessage());
        return "{\"error\":\"Error interno en el broker\"}";
    }
}

    //    private static void reenviarAlCliente(String json) {
//        try {
//            JSONObject respuesta = new JSONObject(json);
//
//            String hostCliente = respuesta.optString("hostCliente", "localhost");
//            int puertoCliente = respuesta.optInt("puertoCliente", 6001); // puerto por defecto del cliente
//
//            try (Socket socket = new Socket(hostCliente, puertoCliente); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
//                out.println(json);
//                System.out.println("[Broker] Reenviado evento al cliente en " + hostCliente + ":" + puertoCliente);
//            }
//
//        } catch (Exception e) {
//            System.err.println("[Broker] Error reenviando al cliente: " + e.getMessage());
//        }
//    }
    // Enviar a la lógica de juego por red
//    private static String enviarALogica(String json) {
//        try (Socket socket = new Socket(HOST_LOGICA, PUERTO_LOGICA); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//
//            out.println(json); // mando evento al servidor de lógica
//            String respuesta = in.readLine(); // espero respuesta
//            System.out.println("[Broker] Respuesta de la lógica: " + respuesta);
//            return respuesta;
//
//        } catch (IOException e) {
//            System.err.println("[Broker] Error comunicando con lógica: " + e.getMessage());
//            return "{\"error\":\"No se pudo contactar la lógica\"}";
//        }
//    }
}


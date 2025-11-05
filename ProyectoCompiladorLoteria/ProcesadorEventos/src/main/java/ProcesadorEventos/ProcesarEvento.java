package ProcesadorEventos;





import Objetos.Evento;
import Util.JSONHelper;
import red.RedComponent;

public class ProcesarEvento {

    private String json;
    private final RedComponent red;
    private EventoListener listener; // referencia al ModeloJuego
    // Configuración de red
private static final String SERVIDOR = "localhost";
private static final int PUERTO_DESTINO = 6000;
private static final int PUERTO_ESCUCHA = 5000;
    
            

    public ProcesarEvento() {
        //poner aqui datos de servidor puerto destino y puerto donde escucha
        this.red = new RedComponent(SERVIDOR, PUERTO_DESTINO, PUERTO_ESCUCHA);
        red.iniciar(); 
        EscucharEvento();
    }


/**
 * Recibe un JSON con tipoEvento, idJugador, pos y topicoEvento,
 * crea un objeto Evento, agrega datos de red (servidor, puertos),
 * lo vuelve a convertir a JSON final y lo envía por la red al broker.
 *
 * Ejemplo de JSON de entrada:
 * {"tipoEvento":"SELECCIONAR_CARTA","idJugador":1,"pos":3,"topicoEvento":"juego_in"}
 */
public void procesarEvento(String jsonEntrada) {
    try {
        // 1️⃣ Extraer los campos del JSON recibido
        String tipoEventoStr = extraerCadena(jsonEntrada, "tipoEvento");
        int idJugador = extraerEntero(jsonEntrada, "idJugador");
        int pos = extraerEntero(jsonEntrada, "pos");
        String topico = extraerCadena(jsonEntrada, "topicoEvento");

        // 2️⃣ Convertir el tipoEvento de texto a Enum
        Objetos.EnumTipoEvento tipoEvento = Objetos.EnumTipoEvento.valueOf(tipoEventoStr);

        // 3️⃣ Construir el objeto Evento con los valores extraídos
        Evento evento = new Evento(tipoEvento, idJugador, pos, topico);

        // 4️⃣ Serializar el evento a JSON
        String jsonEvento = Util.JSONHelper.eventoAJson(evento);

        // 5️⃣ Envolver con la metadata de red (host/puertos)
        String jsonFinal = envolverConMetadataRed(
            jsonEvento,
            SERVIDOR,
            PUERTO_DESTINO,
            PUERTO_ESCUCHA
        );

        // 6️⃣ Enviar el mensaje final al broker
      //   red.enviarMensaje(jsonFinal);
        System.out.println("por el momento solo Simula: red.EnviarMSJ(Json) ");
        System.out.println("[ProcesarEvento] Evento procesado y enviado: " + jsonFinal);

    } catch (Exception e) {
        System.err.println("[ProcesarEvento] Error al procesar evento JSON: " + e.getMessage());
    }
}
    
    // el modelo de juego sera el listener para que se actualice igual que en el mvc
    public void setListener(EventoListener listener) {
        this.listener = listener;
    }

    
    private void EscucharEvento() {
              red.registrarListener(json -> {
            // Notifica al modelo si existe un listener registrado
            if (listener != null) {
                listener.onEventoRecibido(json);
            }
        });
               
    }
    
/**
 * Extrae un número entero de un JSON simple.
 */
private int extraerEntero(String json, String clave) {
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + clave + "\"\\s*:\\s*(\\d+)");
    java.util.regex.Matcher m = p.matcher(json);
    if (m.find()) {
        return Integer.parseInt(m.group(1));
    }
    throw new IllegalArgumentException("No se encontró la clave '" + clave + "' en: " + json);
}

/**
 * Extrae una cadena de texto (sin comillas externas) de un JSON simple.
 */
private String extraerCadena(String json, String clave) {
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + clave + "\"\\s*:\\s*\"([^\"]+)\"");
    java.util.regex.Matcher m = p.matcher(json);
    if (m.find()) {
        return m.group(1);
    }
    throw new IllegalArgumentException("No se encontró la clave '" + clave + "' en: " + json);
}

/**
 * Envuelve el JSON del Evento con la metadata de red (servidor, puertos).
 */
private String envolverConMetadataRed(String jsonEvento, String servidor, int puertoDestino, int puertoEscucha) {
    StringBuilder sb = new StringBuilder(256 + jsonEvento.length());
    sb.append('{')
      .append("\"evento\":").append(jsonEvento).append(',')
      .append("\"servidor\":\"").append(servidor).append("\",")
      .append("\"puertoDestino\":").append(puertoDestino).append(',')
      .append("\"puertoEscucha\":").append(puertoEscucha)
      .append('}');
    return sb.toString();
}
}

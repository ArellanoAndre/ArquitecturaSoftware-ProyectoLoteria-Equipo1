package Helper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycompany.eventos.Evento;

public class HelperJSON {
    public class JSONHelper {
    private static final Gson gson = new Gson();

    // Convierte un objeto Evento a su representación JSON
    public static String toJSON(Evento evento) {
        return gson.toJson(evento);
    }

    // Convierte una cadena JSON a un objeto Evento
    public static Evento toEvento(String json) {
        try {
            return gson.fromJson(json, Evento.class);
        } catch (JsonSyntaxException e) {
            System.err.println("[JsonHelper] Error al convertir JSON: " + e.getMessage());
            return null;
        }
    }
}
}

package Helper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import Evento.Evento;
import Interfaces.IEvento;

public class HelperJSON {
    private static final Gson gson = new Gson();

    // Convierte un objeto Evento a su representación JSON
    public static String toJSON(Evento evento) {
        return gson.toJson(evento);
    }
    
    // Convierte un objeto que implemente IEvento a su representación JSON
    public static String toJSON(IEvento evento) {
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

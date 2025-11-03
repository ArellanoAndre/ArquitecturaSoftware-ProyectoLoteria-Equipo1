package Util;

import Objetos.Evento;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JSONHelper {
    private static final Gson gson = new Gson();

    // Convierte un objeto Evento a su representación JSON
    public static String eventoAJson(Evento evento) {
        return gson.toJson(evento);
    }

    // Convierte una cadena JSON a un objeto Evento
    public static Evento jsonAEvento(String json) {
        try {
            return gson.fromJson(json, Evento.class);
        } catch (JsonSyntaxException e) {
            System.err.println("[JsonHelper] Error al convertir JSON: " + e.getMessage());
            return null;
        }
    }
}

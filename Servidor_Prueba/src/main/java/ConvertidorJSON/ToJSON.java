package ConvertidorJSON;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToJSON {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Convierte un objeto Evento a JSON
    public static String convertirAJson(Evento evento) {
        return gson.toJson(evento);
    }

    // Convierte un JSON a objeto Evento
    public static Evento convertirDesdeJson(String json) {
        return gson.fromJson(json, Evento.class);
    }
}




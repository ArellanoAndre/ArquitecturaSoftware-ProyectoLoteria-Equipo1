/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConvertidorJSON;

/**
 *
 * @author Arell
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToJSON {
    private static final Gson gson = new Gson();

    public static String convertirAJson(Evento e) {
        return gson.toJson(e);
    }

    public static Evento convertirDesdeJson(String json) {
        return gson.fromJson(json, Evento.class);
    }
}


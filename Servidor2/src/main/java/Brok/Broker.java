/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Brok;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import ConvertidorJSON.ToJSON;
import java.util.HashMap;
import java.util.Map;


/**
 * Broker central. Gestiona los tópicos y reenvía mensajes entre clientes y servidor.
 */
public class Broker {

    private static Broker instancia;
    private final Map<String, Topico> topicos = new HashMap<>();

    private Broker() {
        // Inicializa tópicos globales
        topicos.put("topico-clientes", new Topico("topico-clientes"));
        topicos.put("topico-servidor", new Topico("topico-servidor"));
        System.out.println("[Broker] ✅ Tópicos inicializados correctamente.");
    }

    public static synchronized Broker getInstancia() {
        if (instancia == null) {
            instancia = new Broker();
        }
        return instancia;
    }

    public void publicar(String nombreTopico, String mensajeJson) {
        Evento evento = ToJSON.convertirDesdeJson(mensajeJson);
        Topico topico = topicos.get(nombreTopico);

        if (topico != null) {
            System.out.println("[Broker] 📤 Publicando en " + nombreTopico + ": " + evento.getCartaSeleccionada());
            topico.publicar(evento);
        } else {
            System.err.println("[Broker] ⚠ Tópico no encontrado: " + nombreTopico);
        }
    }

    public void suscribir(String nombreTopico, SuscriptorCallback callback) {
        Topico topico = topicos.get(nombreTopico);
        if (topico != null) {
            topico.suscribir(callback);
            System.out.println("[Broker] 📩 Suscriptor agregado a " + nombreTopico);
        } else {
            System.err.println("[Broker] ⚠ No existe el tópico: " + nombreTopico);
        }
    }
}

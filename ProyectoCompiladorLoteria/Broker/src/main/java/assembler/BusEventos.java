/*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package assembler;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArrayList;
//import pruebas.Suscripcion;
//
///**
// *
// * @author isaac
// */
//public class BusEventos {
//
//    /**
//     * Singleton que gestiona la lógica de tópicos y suscripciones. Este es el
//     * "Bus de Eventos".
//     */
//    private static BusEventos instancia;
//
//    // El Bus de Eventos: un Mapa de Tópicos
//    // Clave: Nombre del Topico (juego-out y juego-in)
//    // Valor: Lista de suscriptores (host/puerto)
//    private final Map<String, List<Suscripcion>> topicos;
//
//    // El canal de comunicación del Broker hacia la Lógica del Juego
//    private RedComponent redALogica;
//
//    private BusEventos() {
//        this.topicos = new ConcurrentHashMap<>();
//        // Crear los tópicos que el juego necesita
//        topicos.put("juego-in", new CopyOnWriteArrayList<>());
//        topicos.put("juego-out", new CopyOnWriteArrayList<>());
//        System.out.println("[Bus de eventos] Topicos: 'juego-in' y 'juego-out' creados.");
//    }
//
//    public static synchronized BusEventos getInstancia() {
//        if (instancia == null) {
//            instancia = new BusEventos();
//        }
//        return instancia;
//    }
//
//    /**
//     * Define la conexión que el Broker usará para enviar eventos a la
//     * LogicaJuego.
//     */
//    public void setConexionLogica(RedComponent red) {
//        this.redALogica = red;
//    }
//
//    /**
//     * Suscribe un cliente (host/puerto) a un tópico.
//     */
//    public void suscribir(String topico, String host, int puerto) {
//        List<Suscripcion> suscriptores = topicos.get(topico);
//        if (suscriptores != null) {
//            suscriptores.add(new Suscripcion(host, puerto));
//            System.out.println("[BusEventos] " + host + ":" + puerto + " se suscribio a " + topico);
//        }
//    }
//
//    /**
//     * Publica un evento "juego-in" (de un Cliente) reenviándolo a la
//     * LogicaJuego.
//     */
//    public void publicarEnJuegoIn(String json) {
//        if (redALogica != null) {
//            System.out.println("[BusEventos] Reenviando a 'juego-in' (LogicaJuego)...");
//            redALogica.enviarMensaje(json);
//        } else {
//            System.err.println("[BusEventos] Error: No hay conexión a LogicaJuego.");
//        }
//    }
//
//    /**
//     * Publica un evento "juego-out" (de LogicaJuego) DEVOLVIENDO la lista de
//     * Clientes a notificar.
//     *
//     * * @return La lista de suscriptores del tópico "juego-out".
//     */
//    public List<Suscripcion> getSuscriptoresJuegoOut() {
//        System.out.println("[BusEventos] Obteniendo suscriptores de 'juego-out'.");
//        return topicos.get("juego-out");
//    }
//}

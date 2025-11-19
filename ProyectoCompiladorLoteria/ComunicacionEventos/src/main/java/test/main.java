/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import Ensamblador.EnsambladorComunicacionEventos;
import Evento.Evento;
import interfacesGlobales.IDispatcher;
import interfacesGlobales.IEvento;
import interfacesGlobales.IManejadorEvento;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class main {

    public static void main(String[] args) throws UnknownHostException {

        // 1. Manejador Superior Dummy (imita lógica del juego o broker)
        IManejadorEvento manejadorSuperior = new IManejadorEvento() {
            @Override
            public void manejar(IEvento evento) {
                System.out.println("[ManejadorSuperior] Evento recibido: " + evento);
            }
        };

        // 2. Ensamblador del sistema de comunicación
        EnsambladorComunicacionEventos ensamblador = new EnsambladorComunicacionEventos(manejadorSuperior);

        // 3. Dispatcher "dummy" para probar salida hacia red
        IDispatcher dispatcher = new IDispatcher() {
            @Override
            public void dispatch(String json) {
                System.out.println("[FakeDispatcher] Enviar por red: " + json);
            }
        };

        ensamblador.asignarDispatcher(dispatcher);

        // 4. Crear un evento de ejemplo
        Evento evento = new Evento(
                "chat/general",
                "MensajeEnviado",
                "{\"msg\":\"hola mundo\"}",
                InetAddress.getLocalHost(),
                "192.168.1.77",
                5000
        );

        String jsonPrueba2
                = "{"
                + "\"topico\":\"chat/general\","
                + "\"evento\":\"simon\","
                + "\"JSON\":\"{ \\\"msg\\\": \\\"Hola\\\" }\","
                + "\"ipLocal\":\"192.168.1.77\","
                + "\"ipDestino\":\"192.168.1.10\","
                + "\"puerto\":5000"
                + "}";

        System.out.println("\n=== PRUEBA 1: Enviar evento desde lógica ===");
        ensamblador.enviarEventoDesdeLogica(evento);

        // 5. Simular que llega JSON desde la red
        String jsonSimulado = "{\"tipo\":\"EVENTO_TEST\",\"mensaje\":\"Hola desde la red\"}";

        System.out.println("\n=== PRUEBA 2: Recibir JSON desde red ===");
        ensamblador.recibirJSONDesdeRed(jsonPrueba2);
    }
}

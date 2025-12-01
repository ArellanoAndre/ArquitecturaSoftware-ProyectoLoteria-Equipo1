/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas.Integracion;

/**
 *
 * @author Arell
 */
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import dispatcher.Dispatcher;

public class ClienteEmisor {
//
//    public static void main(String[] args) {
//
//        System.out.println("====== CLIENTE EMISOR (ENVÍA EVENTOS) ======\n");
//
//        try {
//            // 1. Ensamblador para salida del emisor en puerto 7002
//            EnsambladorRed ensamblador = new EnsambladorRed(7002);
//            ensamblador.ensamblar(json -> {
//                // No recibimos nada aquí, este solo manda
//            });
//
//            Dispatcher dispatcher = ensamblador.getDispatcher();
//            Empaquetador empaquetador = new Empaquetador(ensamblador.getColaSalida());
//
//            // 4. Crear evento *USANDO EventBuilder*
//        EventBuilder builder = new EventBuilder("127.0.0.1", 7001, 9001);
//
//            IEvento evento = builder.crearEvento();
//            evento.setTopico("prueba.red");
//            evento.setEvento("EVENTO_PRUEBA");
//            evento.setJSON("{\"TipoEvento\":\"PruebaRed\",\"Valor\": 999}");
//
//            System.out.println("[ClienteEmisor] Evento a enviar:");
//            System.out.println(evento + "\n");
//
//            // 3. Empaquetar → crea EventoRed dentro de colaSalida
//            empaquetador.empaquetar(evento);
//
//// 4. Sacar el EventoRed de la colaSalida
//            Object obj = ensamblador.getColaSalida().poll();
//
//            if (obj == null) {
//                System.err.println("[ERROR] No hay EventoRed en la colaSalida");
//                return;
//            }
//
//            EventoRed eventoRed = (EventoRed) obj;
//
//// 5. Dispatcher = enviar por socket
//            dispatcher.dispatch(eventoRed);
//
//            System.out.println("[ClienteEmisor] EventoRed enviado:");
//            System.out.println(eventoRed);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}


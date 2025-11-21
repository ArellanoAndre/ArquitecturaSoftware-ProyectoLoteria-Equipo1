//package pruebas;
//
//import Evento.Evento;
//import interfaces.IBroker;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//public class Broker implements IBroker {
//
//    private final Map<String, CopyOnWriteArrayList<Suscripcion>> suscripciones = new ConcurrentHashMap<>();
//    private Evento evento;
//
//    
//    //agregar al assembler HelperJSON helper, EventListener eventListener
//    //desempaquetador manda a broker el evento
//    public Broker() {
//    }
//
//    @Override
//    public void registrarSuscripcion(String topico, Suscripcion suscriptor) {
//
//        suscripciones
//                .computeIfAbsent(topico, t -> new CopyOnWriteArrayList<>())
//                .addIfAbsent(suscriptor);
//
//        System.out.println("[Broker] " + suscriptor.getHost() + " suscrito a: " + topico);
//    }
//
//    @Override
//    public void desuscribirCliente(String topico, Suscripcion suscriptor) {
//        CopyOnWriteArrayList<Suscripcion> lista = suscripciones.get(topico);
//        if (lista == null) {
//            return;
//        }
//
//        lista.remove(suscriptor);
//        System.out.println("[Broker] " + suscriptor + " desuscrito de: " + topico);
//
//        //borrar tópico vacío
////        if (lista.isEmpty()) {
////            suscripciones.remove(topico);
////            System.out.println("[Broker] tópico '" + topico + "' eliminado por quedar vacío.");
////        }
//    }
//
//    @Override
//    public List<Suscripcion> obtenerSuscriptores(String topico) {
//        return suscripciones.getOrDefault(topico, new CopyOnWriteArrayList<>());
//    }
//
//}

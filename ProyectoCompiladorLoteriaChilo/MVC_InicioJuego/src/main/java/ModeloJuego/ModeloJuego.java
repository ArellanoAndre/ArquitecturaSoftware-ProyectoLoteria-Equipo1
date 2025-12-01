//package ModeloJuego;
//
//import ConstructorEventos.EventBuilder;
//import Empaquetador.Empaquetador;
//import Interfaces.IModeloJuego;
//import interfacesGlobales.IEvento;
//
//import interfacesGlobales.IManejadorEvento;
//
//public class ModeloJuego implements IModeloJuego, IManejadorEvento<String> {
//
//    private EventBuilder eventBuilder;
//    private Empaquetador empaquetador;
//
//    public ModeloJuego() {
//    }
//    
//    public void  setEventBuilder() {
//       // this.eventBuilder = eventBuilder;
//    }
//
//    @Override
//    public void setEmpaquetador(Empaquetador empaquetador) {
//        this.empaquetador = empaquetador;
//    }
//
//    @Override
//    public void manejar(String payloadJSON) {
//        System.out.println("[ModeloJuego] Payload recibido: " + payloadJSON);
//    }
//
//    @Override
//    public void enviarEventoCartaSeleccionada(int pos, int jugador) {
//        if (eventBuilder == null || empaquetador == null) {
//            System.err.println("[ModeloJuego] Error: faltan dependencias (EventBuilder o Empaquetador).");
//            return;
//        }
//
//        IEvento evento = eventBuilder.crearEvento();
//        evento.setTopico("Juego-in");
//        evento.setEvento("Juego");
//        evento.setJSON("{ \"TipoEvento\": \"CasillaSeleccionadaValida\", \"Jugador\": " + jugador
//                + ", \"Casilla\": " + pos + " }");
//        empaquetador.empaquetar(evento);
//    }
//
//    @Override
//    public void enviarEventoInicioPartida(String jugador, String dificultad, Integer numeroJugadores, Integer puntuacionMaxima) {
//        if (eventBuilder == null || empaquetador == null) {
//            System.err.println("[ModeloJuego] Error: faltan dependencias (EventBuilder o Empaquetador).");
//            return;
//        }
//
//        IEvento evento = eventBuilder.crearEvento();
//        evento.setTopico("Juego-in");
//        evento.setEvento("Juego");
//        evento.setJSON("{ \"TipoEvento\": \"InicioPartida\", \"Jugador\": \"" + jugador
//                + "\", \"Dificultad\": \"" + dificultad + "\", \"Jugadores\": " + numeroJugadores
//                + ", \"PuntuacionMaxima\": " + puntuacionMaxima + " }");
//        empaquetador.empaquetar(evento);
//    }
//
////    @Override
////    public void setEventBuilder(Evento.EventBuilder eventBuilder) {
////        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
////    }
//
//
//
//}
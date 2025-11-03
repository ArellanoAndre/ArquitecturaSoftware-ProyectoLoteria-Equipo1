package ProcesadorEventos;


import Objetos.Evento;
import Util.JSONHelper;
import red.RedComponent;

public class ProcesarEvento {

    private String json;
    private final RedComponent red;
    private EventoListener listener; // referencia al ModeloJuego

    public ProcesarEvento() {
        //poner aqui datos de servidor puerto destino y puerto donde escucha
        this.red = new RedComponent("localhost", 6000, 6001);
        red.iniciar(); 
        EscucharEvento();
    }

    public void procesarEvento(Evento evento) {
        json = JSONHelper.eventoAJson(evento);
        enviarEvento(json);

    }

    private void enviarEvento(String json) {
        red.enviarMensaje(json);
    }
    
    // el modelo de juego sera el listener para que se actualice igual que en el mvc
    public void setListener(EventoListener listener) {
        this.listener = listener;
    }

    
    private void EscucharEvento() {
              red.registrarListener(json -> {
            // Notifica al modelo si existe un listener registrado
            if (listener != null) {
                listener.onEventoRecibido(json);
            }
        });
               
    }
    
}

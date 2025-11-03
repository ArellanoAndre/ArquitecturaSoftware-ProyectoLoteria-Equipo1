/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;





import Objetos.EnumTipoEvento;
import Objetos.Evento;
import Observer.IControlVista;
import Observer.IModeloJuego;
import ProcesadorEventos.EventoListener;
import ProcesadorEventos.ProcesarEvento;

public class ModeloJuego implements EventoListener,IModeloJuego {

    ProcesarEvento procesador;
    IControlVista controlVista;

    public ModeloJuego() {
        // Crear procesador y registrar este modelo como listener
        procesador = new ProcesarEvento();
        procesador.setListener(this);
    }

    @Override
    public void setControlVista (IControlVista controlVista){
        this.controlVista = controlVista;
    }
    
    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {
        new Thread(() -> {
            Evento evento = new Evento(EnumTipoEvento.SELECCIONAR_CARTA, idJugador, pos, "juego_in");
            procesador.procesarEvento(evento);
        }).start();
    }

    @Override
    public void onEventoRecibido(String json) {
        System.out.println("[ModeloJuego] Llego respuesta procesada por el broker: " + json);
    }


}

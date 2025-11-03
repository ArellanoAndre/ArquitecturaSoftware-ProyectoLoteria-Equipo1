package modeloJuego;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Objetos.EnumTipoEvento;
import Objetos.Evento;
import ProcesadorEventos.EventoListener;
import ProcesadorEventos.ProcesarEvento;

public class ModeloJuego implements EventoListener,IModeloJuego {

    ProcesarEvento procesador;

    public ModeloJuego() {
        // Crear procesador y registrar este modelo como listener
        procesador = new ProcesarEvento();
        procesador.setListener(this);
    }
    
    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {
        new Thread(() -> {
            Evento evento = new Evento(EnumTipoEvento.ACTUALIZAR_TABLERO, idJugador, pos, "juego_out");
            procesador.procesarEvento(evento);
        }).start();
    }

    @Override
    public void onEventoRecibido(String json) {
        System.out.println("[ModeloJuego] Llego respuesta procesada por el broker: " + json);
    }


}

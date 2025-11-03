/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;

import Objetos.Evento;
import ProcesadorEventos.EventoListener;
import ProcesadorEventos.ProcesarEvento;

public class ModeloJuego implements EventoListener {

    ProcesarEvento procesador;

    public ModeloJuego() {
        // Crear procesador y registrar este modelo como listener
        procesador = new ProcesarEvento();
        procesador.setListener(this);
    }

    public void EnviarEventoCartaSeleccionada(String nombreCarta, int pos, int idJugador) {
        new Thread(() -> {
            Evento evento = new Evento("Carta Seleccionada", idJugador, nombreCarta, pos);
            procesador.procesarEvento(evento);
        }).start();
    }

    @Override
    public void onEventoRecibido(String json) {
        System.out.println("[ModeloJuego] Llego respuesta procesada por el broker: " + json);
    }


}

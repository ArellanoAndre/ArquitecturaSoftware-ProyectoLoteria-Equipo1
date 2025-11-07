/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloJuego;





import Objetos.EnumTipoEvento;
import Objetos.Evento;
import Interfaces.IControlVista;
import Interfaces.IModeloJuego;
import ModeloJuego.entidades.Jugador;
import ProcesadorEventos.EventoListener;
import ProcesadorEventos.ProcesarEvento;
import java.util.List;

public class ModeloJuego implements EventoListener,IModeloJuego {

    ProcesarEvento procesador;
    IControlVista controlVista;
    Jugador jugadorPrincipal;
    List<Jugador> jugadoresSecundarios;

    public ModeloJuego(IControlVista controlVista, Jugador jugadorPrincipal,List<Jugador> jugadoresSecundarios) {
        // Crear procesador y registrar este modelo como listener
        this.controlVista = controlVista;
        this.jugadorPrincipal = jugadorPrincipal;
        this.jugadoresSecundarios = jugadoresSecundarios;
        
        
        controlVista.setJugadorPrincipal(this.jugadorPrincipal);
        controlVista.setJugadoresSecundarios(jugadoresSecundarios);
        procesador = new ProcesarEvento();
        procesador.setListener(this);   
    }

    @Override
    public void setControlVista (IControlVista controlVista){
    }
    
    /**
 * Envía un JSON con los datos del evento:
 * tipoEvento, idJugador, posicionCarta y topicoEvento.
 * Este método no crea objetos Evento, solo estructura un JSON.
 *
 * Ejemplo de salida:
 * {
 *   "tipoEvento": "SELECCIONAR_CARTA",
 *   "idJugador": 1,
 *   "pos": 3,
 *   "topicoEvento": "juego_in"
 * }
 */
@Override
public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {
    new Thread(() -> {
        // 1️⃣ Crear el JSON con todos los campos requeridos
        String jsonEvento = String.format(
            "{\"tipoEvento\":\"%s\",\"idJugador\":%d,\"pos\":%d,\"topicoEvento\":\"%s\"}",
            EnumTipoEvento.SELECCIONAR_CARTA.name(),
            idJugador,
            pos,
            "juego_in"
        );

        // 2️⃣ Enviar al procesador (quien construye el Evento real y lo envía)
        procesador.procesarEvento(jsonEvento);

        System.out.println("[ModeloJuego] JSON enviado al procesador: " + jsonEvento);
    }).start();
}


    @Override
    public void onEventoRecibido(String json) {
        System.out.println("[ModeloJuego] Llego respuesta procesada por el broker: " + json);
    }
    
    
}

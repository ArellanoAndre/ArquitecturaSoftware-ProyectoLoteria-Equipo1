package filtros;

import Evento.Evento;
import interfaces.IFiltro;

import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

/**
 *
 * @author abrilislas
 */
public class FiltroSuscripcion implements IFiltro {

    protected IFiltro succesor;
    public gestorDeSuscripciones gestorSuscripciones;

    @Override
    public void setNext(IFiltro succesor) {
        this.succesor = succesor;
    }

    @Override
    public void procesarEvento(Evento evento) {
        String accion = evento.getEvento();
        String topico = evento.getTopico(); // juego-in, juego-out

        // ⛑ Evita null pointers
        if (accion == null || topico == null) {
            if (succesor != null) {
                succesor.procesarEvento(evento);
            }
            return;
        }

        // 🟢 SUSCRIPCIÓN
        if (accion.equalsIgnoreCase("suscribir")) {

            Suscripcion suscriptor
                    = new Suscripcion(evento.getIpLocal(), evento.getPuertoLocal());

            gestorSuscripciones.agregarElemento(topico, suscriptor);

            System.out.println("[FiltroSuscripcion] Nuevo suscriptor a: "
                    + topico + " -> " + suscriptor);
        } else {
            succesor.procesarEvento(evento);
        }
    }

}

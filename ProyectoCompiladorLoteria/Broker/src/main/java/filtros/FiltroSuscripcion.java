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
    private gestorDeSuscripciones gestorSuscripciones;

    @Override
    public void setNext(IFiltro succesor) {
        this.succesor = succesor;
    }

    public void procesarEvento(Evento evento) {

        String accion = evento.getEvento();  // “suscripcion”
        String topico = evento.getTopico();  // “juego-in”

        // Validación segura
        if (accion != null && accion.equalsIgnoreCase("suscripcion")) {

            Suscripcion s = new Suscripcion(
                    evento.getIpLocal(),
                    evento.getPuertoLocal()
            );

            gestorSuscripciones.agregarElemento(topico, s);

            System.out.println("[FiltroSuscripcion] Nuevo suscriptor a: "
                    + topico + " -> " + s);

        } else {
            succesor.procesarEvento(evento);
        }
    }

    public void setGestorSuscripciones(gestorDeSuscripciones gestorSuscripciones) {
        this.gestorSuscripciones = gestorSuscripciones;
    }

}

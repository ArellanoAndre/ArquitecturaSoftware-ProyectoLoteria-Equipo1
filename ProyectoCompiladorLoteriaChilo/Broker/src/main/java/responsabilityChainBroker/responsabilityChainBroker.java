package responsabilityChainBroker;

import Evento.Evento;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import filtros.FiltroDesuscripcion;
import filtros.FiltroGenericoEvento;
import filtros.FiltroSuscripcion;

/**
 *
 * @author abrilislas
 */
public class responsabilityChainBroker implements IReceptorEvento {

    private FiltroSuscripcion filtroSuscripcion;
    private FiltroDesuscripcion filtroDesuscripcion;
    private FiltroGenericoEvento filtroEventoGenerico;

    public responsabilityChainBroker() {
        filtroSuscripcion = new FiltroSuscripcion();
        filtroDesuscripcion = new FiltroDesuscripcion();
        filtroEventoGenerico = new FiltroGenericoEvento();
        filtroSuscripcion.setNext(filtroDesuscripcion);
        filtroDesuscripcion.setNext(filtroEventoGenerico);
        filtroEventoGenerico.setNext(null);
    }

    @Override
    public void manejar(IEvento ievento) {
        Evento evento = (Evento) ievento;
        filtroSuscripcion.procesarEvento(evento);
    }

    public void setFiltroSuscripcion(FiltroSuscripcion filtroSuscripcion) {
        this.filtroSuscripcion = filtroSuscripcion;
    }

    public void setFiltroDesuscripcion(FiltroDesuscripcion filtroDesuscripcion) {
        this.filtroDesuscripcion = filtroDesuscripcion;
    }

    public void setFiltroEventoGenerico(FiltroGenericoEvento filtroEventoGenerico) {
        this.filtroEventoGenerico = filtroEventoGenerico;
    }
    
    
    
    
}

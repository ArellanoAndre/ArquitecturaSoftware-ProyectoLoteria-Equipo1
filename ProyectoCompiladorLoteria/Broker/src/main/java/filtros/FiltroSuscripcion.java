package filtros;

import Evento.Evento;
import interfaces.IFiltro;
 
import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

/**
 *
 * @author abrilislas
 */
public abstract class FiltroSuscripcion implements IFiltro{
    
    protected IFiltro succesor; 
    gestorDeSuscripciones gestorSuscripciones;
    
    @Override
    public void setNext(IFiltro succesor) {
        this.succesor=succesor; 
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        if(topico.equals("suscripcion")){
        String IP_SUSCRIPTOR= evento.getIpLocal();
        int PUERTO_SUSCRIPTOR = evento.getPuertoLocal();
        Suscripcion suscriptor = new Suscripcion(IP_SUSCRIPTOR,PUERTO_SUSCRIPTOR);
        gestorSuscripciones.agregarElemento(topico, suscriptor);
        }
        else{
            succesor.procesarEvento(evento);
        }
    }
    
}

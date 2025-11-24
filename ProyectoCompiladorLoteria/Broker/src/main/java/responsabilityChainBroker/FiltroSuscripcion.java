
package responsabilityChainBroker;

import Evento.Evento;
import interfaces.IBroker;
import Broker.Suscripcion;

/**
 *
 * @author abrilislas
 */
public class FiltroSuscripcion implements IFiltro{
    
    private IFiltro succesor; 
    private IBroker broker;
    
    @Override
    public void setBroker(IBroker broker) {
        this.broker = broker;
    }
    
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
        broker.registrarSuscripcion(topico, suscriptor);
        }
        else{
            succesor.procesarEvento(evento);
        }
    }
    
}

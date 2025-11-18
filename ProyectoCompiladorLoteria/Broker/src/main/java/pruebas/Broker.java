
import Evento.Evento;
import interfaces.IBroker;
import responsabilityChainBroker.ResponsabilityChain;


public class Broker implements IBroker{
    
    private static final int PUERTO_BROKER = 6000;
    private static final String IP_BROKER = "localhost";
    private Evento evento;
    ResponsabilityChain reponsabilityChain; 
    
    //lista bidimensional 
    //agregar al assembler HelperJSON helper, EventListener eventListener
    //desempaquetador manda a broker el evento
    
    public Broker() {
        
    }
    

    @Override
    public void registrarSuscripcion(String topico, String suscriptor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void obtenerSuscriptores(String topico) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    
    
    
    
    
}

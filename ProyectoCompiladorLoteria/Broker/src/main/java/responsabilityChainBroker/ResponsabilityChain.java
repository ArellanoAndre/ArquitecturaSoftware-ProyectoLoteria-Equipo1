/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package responsabilityChainBroker;

import Evento.Evento;

/**
 *
 * @author abrilislas
 */
public abstract class ResponsabilityChain {
    
    public void startChain(Evento evento){
        crearTopico(evento);
    }
    
    //1. Proceso 01 - Crear topico 
    private void crearTopico(Evento evento){
        if(evento==null){
        }else{
            registrarSuscriptor(evento);
        }
    }
    //2. Proceso 02 - Registrar Suscriptor
        private void registrarSuscriptor(Evento evento){
        if(evento==null){
            
            
        }else{
            desuscribirTopico(evento);
        }
    }
    //3. Proceso 03 - Desuscribir
        private void desuscribirTopico(Evento evento){
        if(evento==null){
            
            
        }else{
            notificarEvento(evento);
        }
    }
    //4.  Proceso 04 -Notificar Evento a suscriptores
        private void notificarEvento(Evento evento){
    }
}
        

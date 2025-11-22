/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Evento.Evento;
import interfacesGlobales.IManejadorEvento;
import java.util.List;
import pruebas.Suscripcion;

/**
 *
 * @author abrilislas
 */
public interface IBroker extends IManejadorEvento{
    
    void registrarSuscripcion(String topico, Suscripcion suscriptor);
    
    void eliminarSusripcion(String topico, Suscripcion suscriptor);
    
    List<Suscripcion> obtenerSuscriptores(String topico);
    
    void publicarEvento(Evento eventoNuevo);
    
    void procesarEvento(Evento evento);
    
    @Override
    public void manejar(String payloadJSON);
}

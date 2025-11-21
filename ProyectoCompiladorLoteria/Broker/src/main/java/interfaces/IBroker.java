/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Evento.Evento;
import java.util.List;
import pruebas.Suscripcion;

/**
 *
 * @author abrilislas
 */
public interface IBroker {
    
    void registrarSuscripcion(String topico, Suscripcion suscriptor);
    
    void eliminarSusripcion(String topico, Suscripcion sucriptor);
    
    List<Suscripcion> obtenerSuscriptores(String topico);
    
    void publicarEvento(String topico, Evento eventoNuevo);
    
    void procesarEvento();
}

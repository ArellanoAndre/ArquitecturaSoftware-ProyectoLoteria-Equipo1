/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import java.util.List;
import pruebas.Suscripcion;

/**
 *
 * @author abrilislas
 */
public interface IBroker {
    
    public void registrarSuscripcion(String topico, Suscripcion suscriptor);
    public void desuscribirCliente(String topico, Suscripcion sucriptor);
    public List<Suscripcion> obtenerSuscriptores(String topico);
}

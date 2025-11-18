/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

/**
 *
 * @author abrilislas
 */

/**
 * Factory responsable de crear instancias configurables del Dispatcher por medio de inyección
 */
public class DispatcherFactory{
    /**
     *
     */


    /**
     * Crea un Dispatcher con las colas default
     * Este metodo sera usado por el singleton y el ensamblador.
     */
    public Dispatcher createDispatcherDefault() {
        return  Dispatcher.getSingletonInstance();
    }
}
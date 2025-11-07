/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author abrilislas
 */

/**
 * Factory responsable de crear instancias configurables del Dispatcher.
 */
public class DispatcherFactory {
    
    private BlockingQueue<String> queue;
    private boolean autoStart = true;
    
        // DISPATCHER CON QUEUE
    public DispatcherFactory withQueue(BlockingQueue<String> queue) {
        this.queue = queue;
        return this;
    }

    public DispatcherFactory autoStart(boolean value) {
        
        this.autoStart = value;
        return this;
    }
    /**
     * CREAMOS UNA INSTANCIA DEL DISPATCHER Y PASAMOS COMO PARAMETROS LAS DEPENDENCIAS 
     * @return 
     */
    public Dispatcher createDispatcherDefault() {
        return new Dispatcher(queue, autoStart);
    }
    
}

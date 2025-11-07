/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import interfaces.IDispatcher;
import interfaces.IReceptor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author abrilislas
 */
public class Dispatcher implements IDispatcher{
    
/**
 * Dispatcher con inyección de dependencias
 * El singleton es gestionado ahora por el factory
 */
    
    private final BlockingQueue<String> messageQueue; //dependencia inyectable
    private volatile boolean activo; //BANDERA 
    //private final Thread workerThread;


    /**
     * CONSTRUCTOR INYECTABLE CON DEPENDENCIAS PERSONALIZADAS
     * EL FACTORY DECIDIRA CON QUE TRABAJA EL DISPATCHER 
     */
    public Dispatcher(BlockingQueue<String> messageQueue, boolean autoStart) {
        
        //SI EL LISTENER ES NULL INICIALIZAMOS CON VALORES POR DEFECTO
        this.messageQueue = (messageQueue != null) ? messageQueue : new LinkedBlockingQueue<>();
        this.activo = autoStart;

        // Hilo de procesamiento
       // this.workerThread = new Thread(this::processMessages, "DispatcherThread");
        if (autoStart) {
            //this.workerThread.start();
        }
    }

    @Override
    public void dispatch(String json) {
        if (activo) {
            messageQueue.offer(json);
        }
    }

    public void detener() {
        activo = false;
        //workerThread.interrupt();
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public void registerListener(IReceptor receptor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

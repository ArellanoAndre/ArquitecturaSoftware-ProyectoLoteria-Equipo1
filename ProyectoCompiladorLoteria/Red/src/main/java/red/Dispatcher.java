/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Dispatcher con inyección de dependencias
 * El singleton es gestionado ahora por el factory
 */
public class Dispatcher {

    private final List<NetworkListener> listeners; //dependencia inyectable
    private final BlockingQueue<String> messageQueue; //dependencia inyectable
    private volatile boolean activo; //BANDERA 
    private final Thread workerThread;


    /**
     * CONSTRUCTOR INYECTABLE CON DEPENDENCIAS PERSONALIZADAS
     * EL FACTORY DECIDIRA CON QUE TRABAJA EL DISPATCHER 
     */
    public Dispatcher(List<NetworkListener> listeners, BlockingQueue<String> messageQueue, boolean autoStart) {
        
        //SI EL LISTENER ES NULL INICIALIZAMOS CON VALORES POR DEFECTO
        this.listeners = (listeners != null) ? listeners : new ArrayList<>();
        this.messageQueue = (messageQueue != null) ? messageQueue : new LinkedBlockingQueue<>();
        this.activo = autoStart;

        // Hilo de procesamiento
        this.workerThread = new Thread(this::processMessages, "DispatcherThread");
        if (autoStart) {
            this.workerThread.start();
        }
    }

    private void processMessages() {
        while (activo) {
            try {
                String json = messageQueue.take();
                synchronized (listeners) {
                    for (NetworkListener listener : listeners) {
                        listener.onMessageReceived(json);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    //REGISTRAMOS EL LISTENER POR MEDIO DEL SINGLETON
    public void registrarListener(NetworkListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void dispatch(String json) {
        if (activo) {
            messageQueue.offer(json);
        }
    }

    public void detener() {
        activo = false;
        workerThread.interrupt();
    }

    public boolean isActivo() {
        return activo;
    }
}

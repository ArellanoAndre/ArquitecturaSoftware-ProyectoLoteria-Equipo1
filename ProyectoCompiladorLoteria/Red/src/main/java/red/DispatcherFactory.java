package red;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Factory responsable de crear instancias configurables del Dispatcher.
 */
public class DispatcherFactory {

    private boolean autoStart = true;
    private List<NetworkListener> listeners;
    private BlockingQueue<String> queue;

    // Método para construir configuraciones
    public DispatcherFactory withListeners(List<NetworkListener> listeners) {
        this.listeners = listeners;
        return this;
    }
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
        return new Dispatcher(listeners, queue, autoStart);
    }

    /**
     * Singleton manejado por la factory.
     */
    private static Dispatcher singleton;

    public static synchronized Dispatcher getSingletonInstance() {
        if (singleton == null) {
            singleton = new DispatcherFactory().createDispatcherDefault();
        }
        return singleton;
    }
}


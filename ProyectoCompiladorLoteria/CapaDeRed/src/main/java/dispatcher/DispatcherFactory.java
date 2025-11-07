/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;

import Send.ColaGenerica;
import interfaces.IReceptor;
import java.util.List;
import dispatcher.Dispatcher;

/**
 *
 * @author abrilislas
 */

/**
 * Factory responsable de crear instancias configurables del Dispatcher por medio de inyección
 */
public class DispatcherFactory {
    /**
     *
     */
    private boolean autoStart = true;
    List<IReceptor> receptores;
    private ColaGenerica<String> cola;

    /**
     * 
     * @param listeners
     * @return 
     */
     public DispatcherFactory withReceptores(List<IReceptor> receptores) {
        this.receptores = receptores;
        return this;
    }

    public DispatcherFactory withQueue(ColaGenerica<String> cola) {
        this.cola = cola;
        return this;
    }

    public DispatcherFactory autoStart(boolean value) {
        this.autoStart = value;
        return this;
    }

    public Dispatcher createDispatcherDefault() {
        Dispatcher dispatcher = new Dispatcher(receptores,cola, autoStart);

        if (receptores != null) {
            for (IReceptor rc : receptores) {
                dispatcher.registrarReceptor(rc);
            }
        }

        return dispatcher;
    }

    // Singleton
    private static Dispatcher singleton;

    public static synchronized Dispatcher getSingletonInstance() {
        if (singleton == null) {
            singleton = new DispatcherFactory().createDispatcherDefault();
        }
        return singleton;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;
import Send.ColaGenerica;
import interfaces.IReceptor;
import java.util.ArrayList;
import java.util.List;


/**
 * Dispatcher con inyección de dependencias con singleton
 * Este dispatcher utiliza con cola genérica con observer
 * Está comentada la versión con hilo, en caso de ser necesaria
 */
public class Dispatcher implements ColaGenerica.Observer<String>{

    private final List<IReceptor> receptores; //dependencia inyectable
    private final ColaGenerica<String> cola; //dependencia inyectable
    private volatile boolean activo; //BANDERA 
    //private final Thread workerThread;


    /**
     * CONSTRUCTOR INYECTABLE CON DEPENDENCIAS PERSONALIZADAS
     * EL FACTORY DECIDIRA CON QUE TRABAJA EL DISPATCHER (network Listeners y cola)
     */
    public Dispatcher(List<IReceptor> receptores, ColaGenerica<String> cola, boolean autoStart) {
        
        //SI EL LISTENER ES NULL INICIALIZAMOS CON VALORES POR DEFECTO
        this.receptores = (receptores != null) ? receptores : new ArrayList<>();
        this.cola = (cola != null) ? cola : new ColaGenerica<>();
        
        //AGREGAMOS AL DISPATCHER COMO OBSERVER
        this.cola.addObserver(this);
        this.activo = autoStart;

        // Hilo de procesamiento
        /*this.workerThread = new Thread(this::processMessages, "DispatcherThread");
        if (autoStart) {
            this.workerThread.start();
        }*/
    }
    
    
    @Override
    public void onElementoAgregado(String elemento) {
        if (activo) {
            dispatch(elemento);
        }
    }
    
        //REGISTRAMOS EL RECEPTOR
    public void registrarReceptor(IReceptor receptor) {
        receptores.add(receptor);
    }

    public void dispatch(String json) {
        synchronized (receptores) {
            for (IReceptor receptor : receptores) {
                receptor.recibir(json);
            }
        }
    }
    
    
    public void detener() {
        activo = false;
    }

    public boolean isActivo() {
        return activo;
    }
    /*
    
   
    private void processMessages() {
        while (activo) {
            try {
                String json = cola.take();
                synchronized (receptores) {
                    for (IReceptor receptor : receptores) {
                        receptor.recibir(json);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    */
    
 }






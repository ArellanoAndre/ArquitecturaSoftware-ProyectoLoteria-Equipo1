/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;
import Send.ColaGenerica;
import interfaces.IReceptor;
import interfaces.ObserverColaEntrada;
import java.util.ArrayList;
import java.util.List;
import utilidades.TipoAddCola;


/**
 * Dispatcher con inyección de dependencias con singleton
 * Este dispatcher utiliza con cola genérica con observer
 * Está comentada la versión con hilo, en caso de ser necesaria
 */
public class Dispatcher implements ObserverColaEntrada{

    //private final List<IReceptor> receptores; //dependencia inyectable
    private final ColaGenerica<String> colaEntrada; //dependencia inyectable
    private final ColaGenerica<String> colaSalida; //dependencia inyectable
    private volatile boolean activo = true; //BANDERA 
    private String mensajeError = "[Dispatcher] Error al procesar el mensaje:";
    //SINGLETON
    private static Dispatcher singleton;

    /**
     * Metodo singleton, regresa una sola instancia de la clase
     * @return 
     */
    public static synchronized Dispatcher getSingletonInstance() {
        if (singleton == null) {
            singleton = new DispatcherFactory().createDispatcherDefault();
        }
        return singleton;
    }
    /**
     * CONSTRUCTOR INYECTABLE CON DEPENDENCIAS PERSONALIZADAS
     * EL FACTORY DECIDIRA CON QUE TRABAJA EL DISPATCHER (network Listeners y cola)
     */
    /**
     * 
     * @param receptores
     * @param colaEntrada
     * @param autoStart 
     */
    public Dispatcher(List<IReceptor> receptores, ColaGenerica<String> colaEntrada, ColaGenerica<String> colaSalida ) {
        
        this.colaEntrada = colaEntrada;
        this.colaSalida = colaSalida;

        //Se vuelve observer de la cola de entrada
        this.colaEntrada.addObserverEntrada(this);
        System.out.println("Se ha registrado como observador de la cola de entrada.");
    }

        @Override
        public void updateEntrada() {
            if (!activo) return;
            try {
                String mensaje = colaEntrada.take();
                System.out.println("Dispatcher: mensaje recibido desde la cola de entrada: " + mensaje);
                colaSalida.add(mensaje, TipoAddCola.Salida);
                System.out.println("[Dispatcher] Mensaje reenviado a la cola de salida.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Dispatcher] Interrumpido mientras esperaba mensajes.");
            } catch (Exception e) {
                System.err.println("[Dispatcher] Error procesando mensaje: " + e.getMessage());
            }
        }
//    
//        //REGISTRAMOS EL RECEPTOR
//    public void registrarReceptor(IReceptor receptor) {
//        receptores.add(receptor);
//    }
//
//    public void dispatch(String json) {
//        synchronized (receptores) {
//            for (IReceptor receptor : receptores) {
//                receptor.recibir(json);
//            }
//        }
//    }
    /** Detiene el procesamiento del Dispatcher. */
    public void detener() {
        activo = false;
        System.out.println("[Dispatcher] Detenido.");
    }
    
 }






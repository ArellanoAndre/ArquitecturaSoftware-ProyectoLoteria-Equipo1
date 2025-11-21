/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dispatcher;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import eventoRed.EventoRed;
import interfaces.IDispatcher;


/**
 * Dispatcher con inyección de dependencias con singleton
 * Este dispatcher utiliza con cola genérica con observer
 * Está comentada la versión con hilo, en caso de ser necesaria
 */
public class Dispatcher implements IDispatcher{

    //private final List<IReceptor> receptores; //dependencia inyectable
    private ColaDePrioridad<EventoRed> colaSalida; //dependencia inyectable
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
            singleton = new Dispatcher();
        }
        return singleton;
    }
    /**
     */
    private Dispatcher() {}

    /** Detiene el procesamiento del Dispatcher. */
    public void detener() {
        activo = false;
        System.out.println("[Dispatcher] Detenido.");
    }
    
    /**
     * Setea la cola de salida
     * @param colaSalida 
     */
    public void setColaSalida( ColaDePrioridad<EventoRed> colaSalida){
        this.colaSalida = colaSalida;
    }

    @Override
    public void dispatch(EventoRed eventoRed) {
        if (!activo) return;
            try {
                System.out.println("[Dispatcher] Mensaje reenviado a la cola de salida.");
                colaSalida.add(eventoRed, TipoAdd.Salida);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Dispatcher] Interrumpido mientras esperaba mensajes.");
            } catch (Exception e) {
                System.err.println(mensajeError + e.getMessage());
            }    
        }
 }






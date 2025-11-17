/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mecanismoRecepcion;

import ColaGenerica.ColaGenerica;
import colaGenerica.ColaDePrioridad;
import colaGenerica.ObserverEntrada;
import interfaces.IReceptor;
import interfaces.ObserverColaEntrada;

/**
 *
 * @author abrilislas
 */
public class MecanismoRecepcion implements ObserverEntrada{

    private final ColaDePrioridad<String> colaEntrada; //dependencia inyectable
    private IReceptor receptor;
    
    
    public MecanismoRecepcion(ColaDePrioridad<String> colaEntrada, IReceptor receptor){
        
        this.colaEntrada = colaEntrada;
        this.colaEntrada.addObserverEntrada(this);
        this.receptor=receptor;
        System.out.println("Se ha registrado como observador de la cola de entrada.");
    
    }

    @Override
    public void updateEntrada() {
            try {
                String json = colaEntrada.take();
                        receptor.mandarMensaje(json);
                System.out.println("[MecanismoRecepcion] : mensaje recibido desde la cola de entrada: "+json);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[MecanismoRecepcion] Interrumpido mientras esperaba mensajes.");
            } catch (Exception e) {
                System.err.println("[MecanismoRecepcion] Error procesando mensaje: " + e.getMessage());
            }
    }
    
    public void mandarMensaje(String json) {
        receptor.mandarMensaje(json); 
    }
    
}
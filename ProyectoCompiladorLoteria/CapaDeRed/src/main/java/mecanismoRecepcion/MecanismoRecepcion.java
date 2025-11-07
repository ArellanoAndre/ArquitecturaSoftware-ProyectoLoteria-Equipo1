/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mecanismoRecepcion;

import Send.ColaGenerica;

/**
 *
 * @author abrilislas
 */
public class MecanismoRecepcion {
    
    
    
    private final ColaGenerica<String> colaEntrada; //dependencia inyectable
    
    
    public MecanismoRecepcion(ColaGenerica<String> colaEntrada){
        
        this.colaEntrada = colaEntrada;
        //Se vuelve observer de la cola de entrada
        this.colaEntrada.addObserverEntrada(this);
        System.out.println("Se ha registrado como observador de la cola de entrada.");
    
    
    
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Desempaquetador.Desempaquetador;
import Server.Servidor;
import colaGenerica.ColaDePrioridad;
import java.util.logging.Handler;

/**
 *
 * @author abrilislas
 */
public class AssemblerBroker {
    
    private Handler handler;
    private Desempaquetador desempaquetador;
    private ColaDePrioridad colaEntrada = new ColaDePrioridad(); 
    private Servidor servidor = new Servidor();
    
    public AssemblerBroker(){
    
        this.desempaquetador = new Desempaquetador();
        desempaquetador.setColaEntrada(colaEntrada);
    
    }
    
    
}

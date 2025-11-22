/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assembler;

import Server.Servidor;
import colaGenerica.ColaDePrioridad;
import interfaces.IBroker;
import interfacesGlobales.IReceptorJSON;
import java.io.IOException;
import pruebas.LaSecuelaDelBroker;

/**
 *
 * @author abrilislas
 */
public class AssemblerBroker {
    
    private ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();
    private IBroker broker = new LaSecuelaDelBroker();
    private Servidor servidor;

    public AssemblerBroker(IReceptorJSON receptor) {
        try {
            servidor = new Servidor(receptor, colaEntrada, broker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

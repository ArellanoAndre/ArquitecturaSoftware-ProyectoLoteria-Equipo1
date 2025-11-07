/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interfaces;

import Ensamblador.EnsambladorRed;
import Send.ColaGenerica;

/**
 *
 * @author rodri
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
           // Crear el ensamblador con los parámetros de red
        EnsambladorRed ensamblador = new EnsambladorRed("localhost", 6000);

        // Ensamblar todos los componentes
        ensamblador.ensamblar();

        // Obtener la cola de salida
        ColaGenerica<String> cola = ensamblador.getColaSalida();

        // Agregar mensajes a la cola (el Sender los enviará automáticamente)
        cola.add("{\"tipoEvento\":\"PING\",\"mensaje\":\"Hola Broker\"}");
        cola.add("{\"tipoEvento\":\"INFO\",\"mensaje\":\"Segundo mensaje desde ColaGenerica\"}");

        Thread.sleep(3000);
        ensamblador.getSender().close();
    }
    }
    


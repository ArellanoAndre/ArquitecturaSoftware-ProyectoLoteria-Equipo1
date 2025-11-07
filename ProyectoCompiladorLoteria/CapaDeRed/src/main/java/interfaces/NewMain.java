/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interfaces;

import Ensamblador.EnsambladorRed;
import Send.ColaGenerica;
import utilidades.TipoAddCola;

/**
 *
 * @author rodri
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
           // Crear el ensamblador con los parámetros de red
        EnsambladorRed ensamblador = new EnsambladorRed("localhost", 6000);

        // Ensamblar todos los componentes
        ensamblador.ensamblar();

        // Obtener la cola de salida
        ColaGenerica<String> cola = ensamblador.getColaEntrada();

        // Agregar mensajes a la cola (el Sender los enviará automáticamente)
        cola.add("{\"tipoEvento\":\"PING\",\"mensaje\":\"Hola Broker\"}",TipoAddCola.Salida);
        cola.add("{\"tipoEvento\":\"INFO\",\"mensaje\":\"Segundo mensaje desde ColaGenerica\"}",TipoAddCola.Entrada);

        Thread.sleep(3000);
        ensamblador.getSender().close();
    }
    }
    


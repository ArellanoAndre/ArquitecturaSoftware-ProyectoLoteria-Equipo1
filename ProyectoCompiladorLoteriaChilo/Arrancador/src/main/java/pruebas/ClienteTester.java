/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas;

import Ensamblador.EnsambladorRed;
import dispatcher.Dispatcher;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author isaac
 */
 
/**
 * Cliente simple para probar la conexión con el Main (Servidor).
 * Escucha en puerto 5001 y envía al 5000.
 */
public class ClienteTester {

    public static void main(String[] args) {
//        try {
//            System.out.println("--- INICIANDO CLIENTE TESTER ---");
//            Scanner scanner = new Scanner(System.in);
//
//            // 1. Levantamos la red del cliente en puerto 5001
//            // (Para no chocar con el servidor que usa el 5000)
//            EnsambladorRed redCliente = new EnsambladorRed(5001);
//
//            // 2. Definimos qué hacer cuando el Servidor nos responda
//            redCliente.ensamblar(new IReceptorJSON() {
//                @Override
//                public void recibirJSON(String json) {
//                    System.out.println("\n[CLIENTE] !!! RESPUESTA DEL SERVER: " + json);
//                    System.out.print("Escribe JSON a enviar: "); // Volver a mostrar prompt
//                }
//            });
//
//            Dispatcher dispatcher = redCliente.getDispatcher();
//            
//            // 3. Ciclo de envío de mensajes
//            System.out.println("Escribe el JSON exacto para enviar al servidor (localhost:5000)");
//            System.out.println("Ejemplos:");
//            System.out.println("UNIRSE: {\"Tipo\": \"UNIRSE\", \"Nombre\": \"Pepe\"}");
//            System.out.println("MARCAR: {\"Tipo\": \"MARCAR\", \"JugadorID\": 1, \"Casilla\": 0}");
//            System.out.print("\nEscribe JSON a enviar: ");
//
//            while (scanner.hasNextLine()) {
//                String jsonInput = scanner.nextLine();
//                
//                // Creamos el evento de red dirigido al puerto 5000 (Servidor)
//                EventoRed evento = new EventoRed(jsonInput, "127.0.0.1", 5000);
//                
//                dispatcher.dispatch(evento);
//                System.out.println("[CLIENTE] Enviado...");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import ConvertidorJSON.ToJSON;

public class Dispatcher implements Runnable {
    private final ColaGenerica<String> cola;
    private final Sender sender;

    public Dispatcher(ColaGenerica<String> cola, Sender sender) {
        this.cola = cola;
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = cola.tomar();
                sender.enviarMensaje(msg);
            }
        } catch (Exception e) {
            System.err.println("[Dispatcher] Error: " + e.getMessage());
        }
    }
}


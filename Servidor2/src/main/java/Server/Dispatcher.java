/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import ConvertidorJSON.ToJSON;

public class Dispatcher implements Runnable {

    private final ColaGenerica<Evento> colaEnvio;
    private final Sender sender;

    public Dispatcher(ColaGenerica<Evento> colaEnvio, Sender sender) {
        this.colaEnvio = colaEnvio;
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Evento evento = colaEnvio.take();
                String json = ToJSON.convertirAJson(evento);
                sender.enviar(json);
            }
        } catch (InterruptedException e) {
            System.out.println("Dispatcher interrumpido");
        }
    }
}


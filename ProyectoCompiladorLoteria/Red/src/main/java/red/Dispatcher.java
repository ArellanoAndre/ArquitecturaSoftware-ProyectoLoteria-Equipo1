/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Dispatcher {
    //singleton
    private static Dispatcher instance;
    private final List<NetworkListener> listeners = new ArrayList<>();
    private final BlockingQueue<String> colaMensajes = new LinkedBlockingQueue<>();
    private boolean activo = true;

    private Dispatcher() {
        // Hilo que procesa los mensajes de la cola
        new Thread(() -> {
            while (activo) {
                try {
                    String json = colaMensajes.take();
                    for (NetworkListener listener : listeners) {
                        listener.onMessageReceived(json);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "DispatcherThread").start();
    }

    public static synchronized Dispatcher getInstance() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    public void registrarListener(NetworkListener listener) {
        listeners.add(listener);
    }

    public void dispatch(String json) {
        colaMensajes.offer(json);
    }

    public void detener() {
        activo = false;
    }
}

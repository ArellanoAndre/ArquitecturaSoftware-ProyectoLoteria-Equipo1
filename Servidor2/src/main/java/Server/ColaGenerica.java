/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ColaGenerica<T> {
    private final BlockingQueue<T> cola = new LinkedBlockingQueue<>();

    public void put(T item) throws InterruptedException {
        cola.put(item);
    }

    public T take() throws InterruptedException {
        return cola.take();
    }

    public boolean isEmpty() {
        return cola.isEmpty();
    }
}


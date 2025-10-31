/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Red;

/**
 *
 * @author Arell
 */

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ColaGenerica<T> {
    private final BlockingQueue<T> cola = new LinkedBlockingQueue<>();

    public void agregar(T mensaje) throws InterruptedException {
        cola.put(mensaje);
    }

    public T tomar() throws InterruptedException {
        return cola.take();
    }
}


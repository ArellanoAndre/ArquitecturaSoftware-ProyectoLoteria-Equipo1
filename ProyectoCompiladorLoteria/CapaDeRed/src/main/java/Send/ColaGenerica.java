/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Send;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * @author Arell
 * Clase genérica ColaGenerica<T>
 * 
 * Representa una estructura de cola genérica que puede ser usada
 * tanto para la ColaEntrada como para la ColaSalida.
 * 
 * Por el momento solo define la estructura y los métodos vacíos.
 * 
 * @param <T> Tipo de los elementos que manejará la cola.
 */

public class ColaGenerica<T> {

    /** Interfaz interna para los observadores de la cola. */
    public interface Observer<T> {
        void onElementoAgregado(T elemento);
    }

    private final BlockingQueue<T> cola = new LinkedBlockingQueue<>();
    private final List<Observer<T>> observers = new ArrayList<>();

    /** Registra un nuevo observador (por ejemplo, el Sender). */
    public void addObserver(Observer<T> obs) {
        observers.add(obs);
    }

    /** Elimina un observador. */
    public void removeObserver(Observer<T> obs) {
        observers.remove(obs);
    }

    /** Agrega un elemento a la cola y notifica a todos los observadores. */
    public void add(T elemento) {
        cola.offer(elemento);
        notificarObservers(elemento);
    }

    /** Obtiene y elimina el primer elemento sin bloquear. */
    public T poll() {
        return cola.poll();
    }

    /** Obtiene y elimina el primer elemento bloqueando si está vacía. */
    public T take() throws InterruptedException {
        return cola.take();
    }

    /** Devuelve true si la cola está vacía. */
    public boolean isEmpty() {
        return cola.isEmpty();
    }

    /** Devuelve el número de elementos en la cola. */
    public int size() {
        return cola.size();
    }

    /** Limpia toda la cola. */
    public void clear() {
        cola.clear();
    }

    /** Notifica a todos los observadores del nuevo elemento. */
    private void notificarObservers(T elemento) {
        for (Observer<T> obs : observers) {
            obs.onElementoAgregado(elemento);
        }
    }
}

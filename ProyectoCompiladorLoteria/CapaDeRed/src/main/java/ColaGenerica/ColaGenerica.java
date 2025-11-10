/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ColaGenerica;

import interfaces.ObserverColaEntrada;
import interfaces.ObserverColaSalida;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import utilidades.TipoAddCola;

/**
 * @author Arell Clase genérica ColaGenerica<T>
 *
 * Representa una estructura de cola genérica que puede ser usada tanto para la
 * ColaEntrada como para la ColaSalida.
 *
 * Por el momento solo define la estructura y los métodos vacíos.
 *
 * @param <T> Tipo de los elementos que manejará la cola.
 */

public class ColaGenerica<T> {

    private final BlockingQueue<T> cola = new LinkedBlockingQueue<>();
    private final List<ObserverColaSalida> observersSalida = new ArrayList<>();
    private final List<ObserverColaEntrada> observersEntrada = new ArrayList<>();

    /**
     * Registra un nuevo observadorSalida (por ejemplo, el Sender).
     */
    public void addObserverSalida(ObserverColaSalida o) {
        observersSalida.add(o);
    }

    /**
     * Elimina un observadorSalida.
     */
    public void removeObserverSalida(ObserverColaSalida o) {
        observersSalida.remove(o);
    }

    /**
     * Notifica a un observer de Salida
     */
    public void notificarColaSalida() {
        for (ObserverColaSalida o : observersSalida) {
            o.updateSalida();
        }
    }

    /**
     * Registra un nuevo observador (por ejemplo, el Sender).
     */
    public void addObserverEntrada(ObserverColaEntrada o) {
        observersEntrada.add(o);
    }

    /**
     * Elimina un observador.
     */
    public void removeObserverEntrada(ObserverColaEntrada o) {
        observersEntrada.remove(o);
    }

    /**
     * Notifica a un observer de Salida
     */
    public void notificarColaEntrada() {
        for (ObserverColaEntrada o : observersEntrada) {
            o.updateEntrada();
        }
    }

    /**
     * Agrega un elemento a la cola y notifica a todos los observadores.
     */
    public void add(T elemento, TipoAddCola tipo) throws InterruptedException {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento de la cola no puede ser null");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de alta en la cola no puede ser null");
        }

        cola.put(elemento);
        System.out.println("Mensajes en cola: " + cola.size());
        switch (tipo) {
            case Entrada:
                notificarColaEntrada();
                break;
            case Salida:
                notificarColaSalida();
                break;
        }
    }

    /**
     * Obtiene y elimina el primer elemento sin bloquear.
     */
    public T poll() {
        return cola.poll();
    }

    /**
     * Obtiene y elimina el primer elemento bloqueando si está vacía.
     */
    public T take() throws InterruptedException {
        return cola.take();
    }

    /**
     * Devuelve true si la cola está vacía.
     */
    public boolean isEmpty() {
        return cola.isEmpty();
    }

    /**
     * Devuelve el número de elementos en la cola.
     */
    public int size() {
        return cola.size();
    }

    /**
     * Limpia toda la cola.
     */
    public void clear() {
        cola.clear();
    }
}

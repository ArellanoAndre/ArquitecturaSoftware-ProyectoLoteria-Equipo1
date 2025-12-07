/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModeloVista;

import Interfaces.IObserver;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arell
 */
public abstract class Subject {

    protected List<IObserver> observers = new ArrayList<>();

    public void addObserver(IObserver o) {
        observers.add(o);
    }

    public void notifyObservers() {
    System.out.println("[Subject] Notificando a " + observers.size() + " observers.");

    for (IObserver o : observers) {
        System.out.println("[Subject] Notificando a → " + o.getClass().getSimpleName());
        o.update();
    }
}

}


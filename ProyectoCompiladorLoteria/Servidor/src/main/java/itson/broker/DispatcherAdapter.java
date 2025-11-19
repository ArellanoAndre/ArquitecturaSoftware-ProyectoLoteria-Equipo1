/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.broker;

/**
 *
 * @author Arell
 */

import interfacesGlobales.IDispatcher;
// si el import choca, usa nombre completo

public class DispatcherAdapter implements IDispatcher {

    private final interfaces.IDispatcher dispatcherRed;

public DispatcherAdapter(interfaces.IDispatcher dispatcherRed) {
    this.dispatcherRed = dispatcherRed;
}

    @Override
    public void dispatch(String json) {
        dispatcherRed.dispatch(json);
    }
}


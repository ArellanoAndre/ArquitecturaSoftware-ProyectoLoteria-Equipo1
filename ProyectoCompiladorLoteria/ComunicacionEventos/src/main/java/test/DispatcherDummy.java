/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

/**
 *
 * @author Arell
 */

import interfacesGlobales.IDispatcher;

public class DispatcherDummy implements IDispatcher {
    @Override
    public void dispatch(String json) {
        System.out.println("[DispatcherDummy] JSON enviado: " + json);
    }
}


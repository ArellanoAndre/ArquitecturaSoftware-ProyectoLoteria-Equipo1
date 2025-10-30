/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Brok;

import ConvertidorJSON.Evento;

/**
 *
 * @author Arell
 */
/**
 * Interfaz funcional para manejar los callbacks del broker.
 * Se usa cuando un suscriptor recibe un evento de un tópico.
 */
@FunctionalInterface
public interface SuscriptorCallback {
    void onEvent(Evento evento);
}


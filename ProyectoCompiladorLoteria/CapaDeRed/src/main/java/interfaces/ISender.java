/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 * @author Arell
 * Interfaz ISender
 * Define el comportamiento básico de cualquier componente que envíe datos por red.
 */
public interface ISender {
    /**
     * Envía un mensaje JSON a través de la red.
     * @param json Mensaje serializado en formato JSON.
     */
    void send(String json);

    /**
     * Cierra la conexión de red de forma segura.
     */
    void close();
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author Arell
 */

import java.io.IOException;
import java.io.OutputStream;

public class Sender {
    private final OutputStream out;

    public Sender(OutputStream out) {
        this.out = out;
    }

    public void enviar(String mensaje) {
        try {
            out.write((mensaje + "\n").getBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println("Error al enviar: " + e.getMessage());
        }
    }
}


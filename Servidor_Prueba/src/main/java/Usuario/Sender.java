/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuario;

// ----------------- Sender (Singleton) -----------------

import java.io.IOException;
import java.io.OutputStream;

class Sender {

    private static Sender instance;

    private Sender() {
    }

    public static synchronized Sender getInstance() {
        if (instance == null) {
            instance = new Sender();
        }
        return instance;
    }

    public void enviarMensaje(OutputStream out, String mensaje) throws IOException {
        out.write((mensaje + "\n").getBytes());
        out.flush();
    }
}
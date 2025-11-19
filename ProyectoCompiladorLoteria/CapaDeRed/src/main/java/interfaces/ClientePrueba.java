/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;


import Ensamblador.EnsambladorRed;
import dispatcher.Dispatcher;
import java.io.IOException;
import java.net.Socket;




public class ClientePrueba {
   public static void main(String[] args) throws IOException {
        String host = "localhost";
        int puerto = 5000;
        Socket socket = new Socket(host,puerto);

        // Implementación sencilla del receptor
        IReceptor receptor = new IReceptor() {
            @Override
            public void mandarMensaje(String json) {
                System.out.println("[ReceptorCliente] Mensaje recibido del broker: " + json);
            }
        };

        try {
            // Ensamblar la red del cliente
            EnsambladorRed ensamblador = new EnsambladorRed(socket);
            ensamblador.ensamblar(receptor);

            Dispatcher dispatcher = ensamblador.getDispatcher();

            // Esperar un poco a que la conexión se establezca
            Thread.sleep(1500);

            // Enviar algunos mensajes hacia el broker
            System.out.println("[Cliente] Enviando mensajes al broker...");
            dispatcher.dispatch("{\"tipo\":\"saludo\",\"msg\":\"Hola Broker, soy el cliente!\"}");
            Thread.sleep(1000);

            // Esperar los mensajes que el broker enviará al cliente
            Thread.sleep(5000);

            System.out.println("[Cliente] Prueba finalizada.");
            ensamblador.getSender().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

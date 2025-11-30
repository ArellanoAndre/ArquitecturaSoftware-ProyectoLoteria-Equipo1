
package pruebas;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class pruebaClienteSimulado {

    public static void main(String[] args) {
        int puertoEscucha = 6001; // ESTE DEBE COINCIDIR CON EL SUSCRIPTOR

        try (ServerSocket server = new ServerSocket(puertoEscucha)) {
            System.out.println("[CLIENTE] Esperando mensajes del broker en puerto " + puertoEscucha + "...");

            while (true) {
                Socket socket = server.accept();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                String linea;
                StringBuilder json = new StringBuilder();

                while ((linea = reader.readLine()) != null) {
                    json.append(linea);
                }

                System.out.println("\n==== CLIENTE RECIBIÓ EVENTO ====");
                System.out.println(json.toString());

                socket.close();
            }

        } catch (Exception e) {
            System.err.println("[CLIENTE] Error: " + e.getMessage());
        }
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Prueba;

/**
 *
 * @author Arell
 */
import Clientes.SimuladorCartas;
import Servidor.ServidorTCP;

public class MainSimulacion {

    public static void main(String[] args) {
        // 🔹 Inicia el servidor dentro de la misma JVM
        new Thread(() -> {
            try {
                ServidorTCP.main(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 🔹 Inicia los dos clientes (comparten el mismo Broker)
        javax.swing.SwingUtilities.invokeLater(() -> new SimuladorCartas(1).setVisible(true));
        javax.swing.SwingUtilities.invokeLater(() -> new SimuladorCartas(2).setVisible(true));
    }
}

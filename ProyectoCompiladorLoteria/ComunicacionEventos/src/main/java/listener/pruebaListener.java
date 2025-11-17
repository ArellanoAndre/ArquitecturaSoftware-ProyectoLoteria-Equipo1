/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package listener;

import colaGenerica.ColaDePrioridad;
import interfacesGlobales.IReceptor;

/**
 *
 * @author isaac
 */
public class pruebaListener {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();

        IReceptor listener = new EventListener(colaEntrada);

        String jsonPrueba = " {\"topico\":\"juego-123\", \"evento\":\"unirse\"}";
        System.out.println("Simulando recepción de: " + jsonPrueba);

        try {
            // Esto llama al método que queremos probar
            listener.recibir(jsonPrueba);

            System.out.println("Llamada a recibir() completada. Verificando la cola...");

            // 3. VERIFICACIÓN: Revisar si el JSON llegó a la cola
            if (colaEntrada.isEmpty()) {
                System.out.println("--- PRUEBA FALLIDA ---");
                System.out.println("Error: La cola está vacía. El EventListener no añadió el JSON.");
                return;
            }

            // Usamos take() que espera si la cola está vacía
            String jsonEnCola = colaEntrada.take();

            System.out.println("Elemento sacado de la cola: " + jsonEnCola);

            // Verificación final
            if (jsonPrueba.equals(jsonEnCola)) {
                System.out.println("--- ¡PRUEBA EXITOSA! ---");
                System.out.println("El JSON fue recibido y encolado correctamente.");
            } else {
                System.out.println("--- PRUEBA FALLIDA ---");
                System.out.println("Error: El JSON en la cola no coincide con el JSON enviado.");
            }

        } catch (InterruptedException e) {
            System.err.println("La prueba fue interrumpida.");
            Thread.currentThread().interrupt();
        }

    }

}

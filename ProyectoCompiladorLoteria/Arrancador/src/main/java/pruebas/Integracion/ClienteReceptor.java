/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pruebas.Integracion;

/**
 *
 * @author Arell
 */
import Desempaquetador.Desempaquetador;
import Ensamblador.EnsambladorRed;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IEvento;
import interfacesRed.IReceptorJSON;
import interfacesGlobales.IManejadorEvento;

public class ClienteReceptor {

    public static void main(String[] args) {

        System.out.println("====== CLIENTE RECEPTOR (ESCUCHANDO) ======\n");

        try {

            // 1. Crear colaEntrada para el Desempaquetador
            ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();

            // 2. Modelo Mock que recibe el IEvento completo
            IManejadorEvento<IEvento> modeloMock = new IManejadorEvento<IEvento>() {
                @Override
                public void manejar(IEvento evento) {
                    System.out.println("\n[ClienteReceptor] Evento Recibido COMPLETO");
                    System.out.println("  Topico: " + evento.getTopico());
                    System.out.println("  Tipo  : " + evento.getEvento());
                    System.out.println("  JSON  : " + evento.getJSON());
                }
            };

            // 3. Mecanismo JSON → colaEntrada → Desempaquetador
            IReceptorJSON receptorJSON = new IReceptorJSON() {
                @Override
                public void recibirJSON(String json) {
                    System.out.println("[ClienteReceptor] JSON crudo recibido: " + json);
                    try {
                    colaEntrada.add(json, TipoAdd.Entrada);
                    } catch (Exception e) {
                    }
                    
                }
            };

            // 4. Crear ensamblador que escucha en 7001
            EnsambladorRed ensamblador = new EnsambladorRed(7001);
            ensamblador.ensamblar(receptorJSON);

            // 5. Vincular Desempaquetador con colaEntrada
            Desempaquetador des = new Desempaquetador(colaEntrada, modeloMock);

            System.out.println("[ClienteReceptor] LISTO. Esperando mensajes...\n");

            // Hilo polling simple para el desempaquetador
            while (true) {
                if (!colaEntrada.isEmpty()) {
                    des.updateEntrada();
                }
                Thread.sleep(50);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

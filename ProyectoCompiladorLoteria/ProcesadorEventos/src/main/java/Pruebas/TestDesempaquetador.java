/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Pruebas;

import eventBuilder.EventBuilder;
import Desempaquetador.Desempaquetador;
import Helper.HelperJSON;
import InterfacesEventClient.IEvento;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import InterfacesEventClient.IReceptorEvento;

/**
 *
 * @author Arell
 */
public class TestDesempaquetador {
public static void main(String[] args) {

        System.out.println("===== TEST 1: RECEPCIÓN (Desempaquetador + EventBuilder) =====\n");

        // 1. Cola de entrada
        ColaDePrioridad<String> colaEntrada = new ColaDePrioridad<>();
        String ipDestino = "127.0.0.1";
        int puertoDestino = 5001;
        int puertoLocal = 5000;

        // 2. Componente superior mock (recibe IEvento completo)
        IReceptorEvento modeloMock = new IReceptorEvento() {
            @Override
            public void manejar(IEvento evento) {
                System.out.println("[ModeloMock] Evento recibido:");
                System.out.println("  Evento = " + evento);
                
            }
        };

        // 3. Crear Desempaquetador
        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, modeloMock);

        // 4. Crear evento *USANDO EventBuilder*
        EventBuilder builder = new EventBuilder(ipDestino, puertoDestino, puertoLocal);
        

        IEvento evento = builder.crearEvento();
        evento.setTopico("test.recepcion");
        evento.setEvento("EVENTO_TEST");
        evento.setJSON("{\"TipoEvento\":\"RecepcionPrueba\", \"Valor\": 77}");

        // Convertir evento a JSON simulando empacado desde red
        String jsonSimulado = HelperJSON.toJSON(evento);

        System.out.println("[Test] JSON recibido (simulado):\n" + jsonSimulado + "\n");

        try {
        // 5. Añadir a colaEntrada como si llegara de red
        colaEntrada.add(jsonSimulado, TipoAdd.Entrada);
    } catch (Exception e) {
            System.out.println("com.mycompany.procesadoreventos.ProcesadorEventos.main()");
    }
        

        // 6. Procesar la entrada
        System.out.println("[Test] Ejecutando updateEntrada()...\n");
        desempaquetador.updateEntrada();

        System.out.println("\n===== FIN TEST 1 =====");
    }
}


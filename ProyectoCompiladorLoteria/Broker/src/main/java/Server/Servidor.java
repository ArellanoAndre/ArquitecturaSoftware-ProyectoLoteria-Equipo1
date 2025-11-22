/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import Desempaquetador.Desempaquetador;
import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfaces.IBroker;
import interfacesGlobales.IReceptorJSON;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import mecanismoRecepcion.MecanismoRecepcion;
import pruebas.LaSecuelaDelBroker;


public class Servidor {
    
    private static final int PUERTO = 5000;
    IBroker broker = new LaSecuelaDelBroker();
    private final ColaDePrioridad<String> colaEntrada;
    private final MecanismoRecepcion mecanismoRecepcion;
    private final IReceptorJSON receptor;

    
    public Servidor(IReceptorJSON receptor,ColaDePrioridad<String> colaEntrada, IBroker broker ) throws IOException {
        this.receptor = receptor;
        this.colaEntrada = colaEntrada;
        this.mecanismoRecepcion = new MecanismoRecepcion(colaEntrada, receptor);
        this.broker=broker;
        Desempaquetador desempaquetador = new Desempaquetador(colaEntrada, broker);
        System.out.println("[ServidorTCP] Inicializado en puerto " + PUERTO);
    } 
      
    public void iniciarServer(){

        try (ServerSocket servidorBroker = new ServerSocket(PUERTO)) {
            System.out.println("[Servidor] Escuchando en puerto " + PUERTO);

            while (true) {
                Socket cliente = servidorBroker.accept();
                System.out.println("[Servidor] Nuevo cliente: " 
                        + cliente.getRemoteSocketAddress());
            Thread threadCliente = new Thread(() -> {
                    try {
                        manejarCliente(cliente);
                    } catch (InterruptedException ex) {
                        System.getLogger(Servidor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                });
                threadCliente.start();
            }

        } catch (IOException e) {
            System.err.println("[Servidor] Error en el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void manejarCliente(Socket socket) throws InterruptedException {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String json;
            while ((json = entrada.readLine()) != null) {
                System.out.println("[Servidor] Recibido JSON: " + json);

                // Se mete a la cola (notifica al MecanismoRecepcion)
                colaEntrada.add(json, TipoAdd.Entrada);

                System.out.println("[Servidor] JSON agregado a la cola para procesamiento.");
            }

        } catch (IOException e) {
            System.err.println("[Servidor] Error con cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("[Servidor] Cliente desconectado.");
            } catch (IOException ignore) {}
        }
    }
}
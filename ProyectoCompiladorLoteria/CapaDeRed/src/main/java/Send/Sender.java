/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Send;
import ColaGenerica.ColaGenerica;
import interfaces.ISender;
import interfaces.ObserverColaSalida;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Arell
 * Clase Sender
 * 
 * - Implementa ISender para enviar mensajes por TCP.
 * - Implementa ColaGenerica.Observer<String> para escuchar el flujo de salida.
 * - Mantiene una conexión persistente con el Broker.
 */
public class Sender implements ISender, ObserverColaSalida {

   

    private Socket socket;
    private DataOutputStream salida;
    private volatile boolean conectado;

    private final Object lock = new Object();
    
    private final ColaGenerica<String> colaSalida;

    /**
     * Constructor.
     * @param host Dirección IP o nombre del host del broker (por ejemplo "localhost").
     * @param puerto Puerto TCP del broker (por ejemplo 6000).
     */
    
   

    public Sender(Socket socket, ColaGenerica<String> colaSalida) {
        this.socket = socket;
        this.colaSalida = colaSalida;
        conectar();
    }

    /**
     * Intenta establecer una conexión persistente con el broker.
     * Si falla, reintenta cada 3 segundos.
     */
    private void conectar() {
        new Thread(() -> {
            while (!conectado) {
                try {
                    System.out.println("[Sender] Intentando conectar a "+ socket.getInetAddress() );
                    
                    
                    salida = new DataOutputStream(socket.getOutputStream());
                    conectado = true;
                    System.out.println("[Sender] Conectado al broker.");
                } catch (IOException e) {
                    System.err.println("[Sender] Error de conexión. Reintentando en 3 segundos...");
                    try { Thread.sleep(3000); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
                }
            }
        }, "Sender-Connection-Thread").start();
    }

    /**
     * Envía un mensaje JSON por el socket.
     * @param json Mensaje serializado.
     */
    @Override
    public void send(String json) {
        if (!conectado || socket == null || socket.isClosed()) {
            System.err.println("[Sender] No conectado. Mensaje no enviado.");
            return;
        }

        synchronized (lock) {
            try {
                salida.writeUTF(json);
                salida.flush();
                System.out.println("[Sender] Mensaje enviado: " + json);
            } catch (IOException e) {
                System.err.println("[Sender] Error al enviar mensaje. Intentando reconectar...");
                conectado = false;
                reconnect();
            }
        }
    }

    /** Reconecta en caso de error. */
    private void reconnect() {
        close();
        conectar();
    }

    /** Cierra la conexión TCP. */
    @Override
    public void close() {
        try {
            conectado = false;
            if (salida != null) salida.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("[Sender] Conexión cerrada correctamente.");
        } catch (IOException e) {
            System.err.println("[Sender] Error al cerrar conexión: " + e.getMessage());
        }
    }

    @Override
    public void updateSalida() {
        try {
            String mensaje = colaSalida.take();
            
            send(mensaje);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

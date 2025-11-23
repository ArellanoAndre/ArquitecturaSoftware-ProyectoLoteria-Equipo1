 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Broker;

/**
 *
 * @author isaac
 */
public class Suscripcion {
    
    private final String host;
    private final int puerto;
    
    public Suscripcion(String host, int puerto ){
        this.host = host;
        this.puerto = puerto;
    }

    public String getHost(){ 
        return host; 
    }
    
    public int getPuerto(){ 
        return puerto; 
    }

    @Override
    public String toString() {
        return "Suscriptor[" + host + ":" + puerto + "]";
    }
}

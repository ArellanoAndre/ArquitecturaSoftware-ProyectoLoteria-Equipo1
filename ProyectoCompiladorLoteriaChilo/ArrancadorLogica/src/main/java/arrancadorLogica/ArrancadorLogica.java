/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arrancadorLogica;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logicaEnsamblador.EnsambladorLogica;

/**
 *
 * @author rodri
 */
public class ArrancadorLogica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        EnsambladorLogica ensambladorLogica = new EnsambladorLogica();
        
        try {

            ensambladorLogica.ensamblar(5000, 7777, "127.0.0.1" );
            
        } catch (IOException ex) {
            Logger.getLogger(ArrancadorLogica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ArrancadorLogica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

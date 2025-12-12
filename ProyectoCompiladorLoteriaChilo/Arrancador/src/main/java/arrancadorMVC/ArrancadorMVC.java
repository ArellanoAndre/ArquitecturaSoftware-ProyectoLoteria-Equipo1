/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arrancadorMVC;

import MVCEnsamblador.EnsambladorMVC;

/**
 *
 * @author rodri
 */
public class ArrancadorMVC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EnsambladorMVC ensambladorMVC = new EnsambladorMVC();
        ensambladorMVC.ensamblarMVC("192.168.1.95", 5001, 7000);
    }
    
}

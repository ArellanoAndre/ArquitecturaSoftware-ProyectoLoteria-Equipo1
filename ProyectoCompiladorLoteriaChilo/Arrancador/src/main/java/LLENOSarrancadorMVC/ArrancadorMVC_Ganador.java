/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LLENOSarrancadorMVC;

import MVCEnsamblador.EnsambladorMVC_Ganador;

/**
 *
 * @author isaac
 */
public class ArrancadorMVC_Ganador {

    public static void main(String[] args) {
        EnsambladorMVC_Ganador ensamblador = new EnsambladorMVC_Ganador();
        // Asegúrate de usar la IP correcta del broker (127.0.0.1 si es local)
        ensamblador.ensamblarMVC("127.0.0.1", 5005, 7000);
    }

}

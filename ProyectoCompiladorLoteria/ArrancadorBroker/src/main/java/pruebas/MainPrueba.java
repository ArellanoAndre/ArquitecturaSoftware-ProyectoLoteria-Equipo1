package pruebas;

import Ensamblador.EnsambladorRed;
import Evento.Evento;
import arrancadorBroker.ArrancadorBroker;
import responsabilityChainBroker.responsabilityChainBroker;
import suscripciones.gestorDeSuscripciones;

public class MainPrueba {

    public static void main(String[] args) throws Exception {

        ArrancadorBroker arrancador = new ArrancadorBroker(7000);
        arrancador.ensamblar();

        responsabilityChainBroker broker = arrancador.getBroker();

        System.out.println("===== PRUEBA 1: SUSCRIPCIÓN =====");
        Evento evSub = new Evento();

        evSub.setTopico("Juego-in");
        evSub.setEvento("suscripcion");
        evSub.setIpLocal("127.0.0.1");
        evSub.setPuertoLocal(5000);

        broker.manejar(evSub);
        
        System.out.println("===== PRUEBA 2: SUSCRIPCIÓN =====");
        Evento evSub2 = new Evento();

        evSub2.setTopico("Juego-out");
        evSub2.setEvento("suscripcion");
        evSub2.setIpLocal("127.0.0.1");
        evSub2.setPuertoLocal(5001);

        broker.manejar(evSub2);

//        System.out.println("\n===== PRUEBA 2: EVENTO NORMAL =====");
//        Evento evNormal = new Evento();
//
//        evNormal.setTopico("juego-in");
//        evNormal.setEvento("jugadaRealizada");
//        evNormal.setIpLocal("127.0.0.1");
//        evNormal.setPuertoLocal(6001);
//
//        broker.manejar(evNormal);

    }
}

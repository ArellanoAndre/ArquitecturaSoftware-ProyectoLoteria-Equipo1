package pruebaBroker;

import Evento.Evento;
import InterfacesEventClient.IEvento;
import filtros.FiltroDesuscripcion;
import filtros.FiltroGenericoEvento;
import filtros.FiltroSuscripcion;
import responsabilityChainBroker.responsabilityChainBroker;
import suscripciones.gestorDeSuscripciones;

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // 1. CREAR GESTOR DE SUSCRIPCIONES
        // ==========================================
        gestorDeSuscripciones gestor = new gestorDeSuscripciones();

        // ==========================================
        // 2. CREAR LOS FILTROS
        // ==========================================
        FiltroSuscripcion filtroSus = new FiltroSuscripcion();
        filtroSus.gestorSuscripciones = gestor;

        FiltroDesuscripcion filtroDes = new FiltroDesuscripcion();
        filtroDes.gestorSuscripciones = gestor;

        FiltroGenericoEvento filtroGen = new FiltroGenericoEvento() {
            public void empaquetar(IEvento evento) {
                System.out.println("[FiltroGenericoEvento] Empaquetando y enviando evento: "
                        + evento.getTopico());
            }
        };
        filtroGen.gestorSuscripciones = gestor;

        // ==========================================
        // 3. ENLAZAR LA CADENA DE RESPONSABILIDAD
        // ==========================================
        filtroSus.setNext(filtroDes);
        filtroDes.setNext(filtroGen);
        filtroGen.setNext(null);

        // ==========================================
        // 4. CREAR EL BROKER Y ASIGNAR FILTROS
        // ==========================================
        responsabilityChainBroker broker = new responsabilityChainBroker();
        broker.filtroSuscripcion = filtroSus;
        broker.filtroDesuscripcion = filtroDes;
        broker.filtroEventoGenerico = filtroGen;

        // =======================================================
        // 5. PRUEBA 1: SUSCRIPCIÓN
        // =======================================================
        System.out.println("\n===== PRUEBA 1: SUSCRIPCION AL JUEGO-IN =====");

        Evento e1 = new Evento();
        e1.setEvento("suscribir");
        e1.setTopico("juego-in");
        e1.setIpLocal("127.0.0.1");
        e1.setPuertoLocal(6001);

        broker.manejar(e1);

        System.out.println("Suscriptores en 'juego-in': "
                + gestor.obtenerSuscriptores("juego-in"));

        // =======================================================
        // 6. PRUEBA 2: EVENTO NORMAL
        // =======================================================
        System.out.println("\n===== PRUEBA 2: EVENTO NORMAL =====");

        Evento eNormal = new Evento();
        eNormal.setTopico("juego-in");
        eNormal.setIpLocal("127.0.0.1");
        eNormal.setPuertoLocal(6001);
        eNormal.setJSON("{\"carta\":\"Luna\"}");

        broker.manejar(eNormal);
    }

}

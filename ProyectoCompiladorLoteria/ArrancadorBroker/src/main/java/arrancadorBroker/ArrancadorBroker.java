package arrancadorBroker;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import colaGenerica.ColaDePrioridad;
import filtros.FiltroDesuscripcion;
import filtros.FiltroGenericoEvento;
import filtros.FiltroSuscripcion;
import interfacesRed.IReceptorJSON;
import listener.EventListener;
import responsabilityChainBroker.responsabilityChainBroker;
import suscripciones.gestorDeSuscripciones;

public class ArrancadorBroker {

    private responsabilityChainBroker broker;
    private gestorDeSuscripciones gestor;
    private EnsambladorRed ensambladorRed;

    private Empaquetador empaquetador;
    private Desempaquetador desempaquetador;

    private int puerto;

    public ArrancadorBroker(int puertoRed) {
        this.puerto = puertoRed;
        this.ensambladorRed = new EnsambladorRed(puertoRed);
    }

    public void ensamblar() throws Exception {

        // 1) Crear ensamblador de red REAL
        ensambladorRed = new EnsambladorRed(puerto);

        // 2) Ensamblar red, pero todavía no tenemos receptorJSON real
        //    Usaremos null porque NO lo usas (EventListener no usa ese receptor).
        ensambladorRed.ensamblar(null);

        // 3) Obtener colas
        ColaDePrioridad<EventoRed> colaSalida = ensambladorRed.getColaSalida();
        ColaDePrioridad<String> colaEntrada = ensambladorRed.getColaEntrada();

        // 4) Gestor de suscripciones
        gestor = new gestorDeSuscripciones();

        // 5) Crear filtros normales
        FiltroSuscripcion filtroSus = new FiltroSuscripcion();
        filtroSus.setGestorSuscripciones(gestor);

        FiltroDesuscripcion filtroDes = new FiltroDesuscripcion();
        filtroDes.setGestorSuscripciones(gestor);

        FiltroGenericoEvento filtroGen = new FiltroGenericoEvento();
        filtroGen.setGestorSuscripciones(gestor);

        // 6) Empaquetador (hacia red)
        empaquetador = new Empaquetador(
                colaSalida,
                "127.0.0.1",
                9999,
                puerto
        );
        filtroGen.setEmpaquetador(empaquetador);

        // 7) Encadenar filtros
        filtroSus.setNext(filtroDes);
        filtroDes.setNext(filtroGen);
        filtroGen.setNext(null);

        // 8) Crear broker y setear filtros
        broker = new responsabilityChainBroker();
        broker.setFiltroSuscripcion(filtroSus);
        broker.setFiltroDesuscripcion(filtroDes);
        broker.setFiltroEventoGenerico(filtroGen);

        // 9) Crear desempaquetador COMO OBSERVER de la colaEntrada
        desempaquetador = new Desempaquetador(colaEntrada, broker);
        colaEntrada.addObserverEntrada(desempaquetador);

        System.out.println("[EnsambladorBroker] Broker ensamblado y conectado a la red.");
    }

    public responsabilityChainBroker getBroker() {
        return broker;
    }
}

package brokerEnsamblador;

import Desempaquetador.Desempaquetador;
import Empaquetador.Empaquetador;
import Ensamblador.EnsambladorRed;
import RedEventos.EventoRed;
import Sender.EventSender;
import colaGenerica.ColaDePrioridad;
import dispatcher.Dispatcher;
import filtros.FiltroDesuscripcion;
import filtros.FiltroGenericoEvento;
import filtros.FiltroSuscripcion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import listener.EventListener;
import responsabilityChainBroker.responsabilityChainBroker;
import suscripciones.gestorDeSuscripciones;

public class EnsambladorBroker {

    private responsabilityChainBroker broker;
    private gestorDeSuscripciones gestor;
    private EnsambladorRed ensambladorRed;

    private Empaquetador empaquetador;
    private Desempaquetador desempaquetador;

    private int puerto;

    public EnsambladorBroker(int puertoLocal) {
        this.puerto = puertoLocal;
    }

    public void ensamblar() {

        // 1) Crear ensamblador de red REAL
        ensambladorRed = new EnsambladorRed(puerto);

        // 2) Crear Colas
        ColaDePrioridad<EventoRed> colaSalida = new ColaDePrioridad();
        ColaDePrioridad<String> colaEntrada = new ColaDePrioridad();

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
        empaquetador = new Empaquetador(colaSalida, "127.0.0.1", 9999, puerto);
        filtroGen.setEmpaquetador(empaquetador);

        EventSender eventSender = new EventSender(colaSalida);
        colaSalida.addObserverSalida(eventSender);

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
        
        EventListener eventListener = new EventListener(colaEntrada);

        //----------------------------------------------------------------------
        try {
            ensambladorRed.ensamblar(eventListener);
            Dispatcher dispatcher = ensambladorRed.getDispatcher();
            eventSender.setiDispatcher(dispatcher);
        } catch (IOException ex) {
            Logger.getLogger(EnsambladorBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
            

        

        System.out.println("[EnsambladorBroker] Broker ensamblado y conectado a la red.");
    }

    public responsabilityChainBroker getBroker() {
        return broker;
    }
}

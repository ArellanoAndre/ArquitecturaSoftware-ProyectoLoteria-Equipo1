package Empaquetador;

import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IEmpaquetador;
import Helper.HelperJSON;
import Evento.EventoRed;
import Interfaces.IEvento;

/**
 * Clase encargada de convertir (empaquetar) objetos que implementan IEvento a
 * formato JSON utilizando HelperJSON, y enviarlos a la cola de salida.
 */
public class Empaquetador  {

    /**
     * Cola donde se publicarán los mensajes EventoRed
     */
    private ColaDePrioridad<EventoRed> colaSalida = null;

    /**
     * Constructor
     */
    public Empaquetador(ColaDePrioridad<EventoRed> colaSalida) {
        this.colaSalida = colaSalida;
    }

    /**
     * Convierte un objeto IEvento a una cadena JSON y usa sus datos para crear
     * un EventoRed. Posteriormente lo envía a la cola de salida.
     *
     * @param evento Objeto que implementa IEvento y será transformado a JSON.
     */
    public void empaquetar(IEvento evento) {
        try {

            // Se convierte el evento a JSON mediante HelperJSON
            String json = HelperJSON.toJSON(evento);
            EventoRed eventoRed = new EventoRed(json, evento.getIpDestino(), evento.getPuertoDestino());

            System.out.println("\n [Empaquetador] EventoRed creado: " + eventoRed);
            System.out.println("\n [Empaquetador] Metiendo a cola");

            // Se agrega a la cola de salida
            colaSalida.add(eventoRed, TipoAdd.Salida);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[Empaquetador] Hilo interrumpido al encolar evento.");
        } catch (Exception e) {
            System.err.println("[Empaquetador] Error al empaquetar evento: " + e.getMessage());
        }
    }

}

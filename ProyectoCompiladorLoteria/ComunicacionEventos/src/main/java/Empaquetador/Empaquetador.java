package Empaquetador;

import colaGenerica.ColaDePrioridad;
import colaGenerica.TipoAdd;
import interfacesGlobales.IEmpaquetador;
import interfacesGlobales.IEvento;
import Helper.HelperJSON;

/**
 * Clase encargada de convertir (empaquetar) objetos que implementan IEvento a
 * formato JSON utilizando HelperJSON, y enviarlos a la cola de salida.
 */
public class Empaquetador implements IEmpaquetador {

    /**
     * Cola donde se publicarán los mensajes ya empaquetados en JSON.
     */
    private ColaDePrioridad<String> colaSalida = null;
    private String json;

    /**
     * Constructor por defecto.
     */
    public Empaquetador() {
    }

    /**
     * Asigna la cola de salida donde se enviarán los mensajes JSON.
     *
     * @param colaSalida Cola de prioridad usada como canal de salida.
     */
    public void setColaSalida(ColaDePrioridad<String> colaSalida) {
        this.colaSalida = colaSalida;
    }

    /**
     * Convierte un objeto IEvento a una cadena JSON y lo envía a la cola de
     * salida.
     *
     * @param evento Objeto que implementa IEvento y será transformado a JSON.
     */
    @Override
    public void empaquetar(IEvento evento) {
        try {
            if (colaSalida == null) {
                throw new IllegalStateException("[Empaquetador] La cola de salida no ha sido inicializada.");
            }
            System.out.println("\n EMPAQUETADOR LO METI A LA COLA SALIDA!");
            // Se convierte el evento a JSON mediante HelperJSON
            String json = HelperJSON.toJSON(evento);

            // Se agrega a la cola de salida con prioridad indicada
            colaSalida.add(json, TipoAdd.Salida);
            
        } catch (Exception e) {
            System.err.println("[Empaquetador] Error al empaquetar evento: " + e.getMessage());
        }
    }

}

package suscripciones;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author abrilislas
 */
public class gestorDeSuscripciones {

    private final Map<String, CopyOnWriteArrayList<Suscripcion>> suscripciones = new ConcurrentHashMap<>();

    public void agregarElemento(String topico, Suscripcion suscriptor) {
        suscripciones.computeIfAbsent(topico, t -> new CopyOnWriteArrayList<>()).addIfAbsent(suscriptor);
    }

    public void eliminarElemento(String topico, Suscripcion suscripcion) {
        CopyOnWriteArrayList<Suscripcion> lista = suscripciones.get(topico);
        if (lista != null) {
            lista.remove(suscripcion);
        }
    }

    public List<Suscripcion> obtenerSuscriptores(String topico) {
        return suscripciones.getOrDefault(topico, new CopyOnWriteArrayList<>());

    }

}

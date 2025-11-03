package Observer;

/**
 * Interfaz que define el comportamiento de un observador en el patrón Observer.
 * Los objetos que implementen esta interfaz serán notificados cuando haya
 * cambios en el modelo y deberán actualizarse en consecuencia.
 */
public interface Observer {

    /**
     * Método que se llama cuando el objeto observado ha cambiado. Aquí se debe
     * definir la lógica para actualizar la vista o reaccionar al cambio.
     */
    void update();
}

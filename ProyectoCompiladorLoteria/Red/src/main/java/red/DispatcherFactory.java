package red;

public class DispatcherFactory {
    public static Dispatcher crearDispatcher() {
        // Retorna siempre la misma instancia singleton
        return Dispatcher.getInstance();
    }
}


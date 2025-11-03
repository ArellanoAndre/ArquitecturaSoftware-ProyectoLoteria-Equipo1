package red;

//clase principal del componente de red
public class RedComponent {
    private final Sender sender;
    private final Receiver receiver;
    private Thread hiloReceiver;

    public RedComponent(String hostDestino, int puertoDestino, int puertoEscucha) {
        this.sender = new Sender(hostDestino, puertoDestino);
        this.receiver = new Receiver(puertoEscucha);
    }

    public void iniciar() {
        hiloReceiver = new Thread(receiver);
        hiloReceiver.start();
    }

    public void enviarMensaje(String json) {
        sender.enviar(json);
    }

    public void registrarListener(NetworkListener listener) {
        DispatcherFactory.crearDispatcher().registrarListener(listener);
    }

    public void detener() {
        receiver.detener();
        hiloReceiver.interrupt();
    }
}

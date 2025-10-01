package ModeloVista;

// ====== MODELO VISTA (Puente entre ModeloJuego y Presentacion) ======
import ModeloJuego.Carta;
import java.util.ArrayList;
import java.util.List;
import ModeloJuego.ModeloJuego;
import Observer.Observer;
import Presentacion.PanelTarjeta;

public class ModeloVista {

    private Carta cartaCantada;
    private int marcador;
    private List<Observer> observers = new ArrayList<>();
    private ModeloJuego modeloJuego;

    public void setModeloJuego(ModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    //Metodo que usa el modeloJuego para enviarle una carta nueva, este despues usa notificar para que se muestre en pantalla la nueva carta
    public void setCartaCantada(Carta carta) {
        cartaCantada = carta;
        notificar();
    }

    // Metodo que usa la PRESENTACION para obtener la carta cantada ACTUAL. se usa despues de que se le notifica a la presentacion para que esta recupere informacion nueva
    public String getCartaCantada() {
        return cartaCantada.getNombreCarta();
    }
    // Metodo que usa la PRESENTACION para obtener el marcado ACTUAL. se usa despues de que se le notifica a la presentacion para que esta recupere informacion nueva
    public int getMarcador() {
        return marcador;
    }

    // Metodo que usa el MODELOJUEGO para enviar un nuevo marcador al ModeloVista. este despues usa notificar para que sea actualizado en presentacion.
    public void setMarcador(int marcador) {
        this.marcador = marcador;
        notificar();
    }


    public JugadorVista getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public void setJugadorPrincipal(JugadorVista jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }
    
    public void mostrarTarjetaJugador(int[] arregloCartas) {
    PanelTarjeta panelTarjeta = new PanelTarjeta(arregloCartas, actionListenerClickCarta);
    }

    public void actualizarTarjetaJugadorP (int[] casillas){
        jugadorPrincipal.getTarjeta().setMarcadas(casillas);
        notificar();
    }
    // Método llamado por el controlador cuando se selecciona una carta. este despues lo envia al modeloJuego donde maneja la logica de validacion.
    public void seleccionarCarta(int pos) {
        modeloJuego.verificarCarta(pos);
    }

    //Metodo usado para agregar observadores en este caso la PRESENTACION se agrega como observador
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //Metodo que notifica a los observadores (en este caso solamente la PRESENTACION). despues como la presentacion implementa metodos de observer, se ejecuta update() el cual repinta la pantalla despues de ser notificada.
    private void notificar() {
        for (Observer o : observers) {
            o.update();
        }
    }
}

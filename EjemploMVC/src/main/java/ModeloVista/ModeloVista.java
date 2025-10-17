package ModeloVista;

// ====== MODELO VISTA (Puente entre ModeloJuego y Presentacion) ======
import java.util.ArrayList;
import java.util.List;
import ModeloVista.entidadesVista.CartaVista;
import ModeloVista.entidadesVista.JugadorVista;
import Observer.IModeloJuego;
import Observer.IModeloVista;
import Observer.Observer;

public class ModeloVista implements IModeloVista{

    private CartaVista cartaCantada;
    private int marcador;
    private JugadorVista jugadorPrincipal;
    private List<JugadorVista> jugadoresSecundarios;
    private List<Observer> observers = new ArrayList<>();
    private IModeloJuego modeloJuego;

    public ModeloVista() {
    }

        
    @Override
    public void setModeloJuego(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    //Metodo que usa el modeloJuego para enviarle una carta nueva, este despues usa notificar para que se muestre en pantalla la nueva carta
    @Override
    public void setCartaCantada(CartaVista carta) {
        cartaCantada = carta;
        notificar();
    }

    // Metodo que usa la PRESENTACION para obtener la carta cantada ACTUAL. se usa despues de que se le notifica a la presentacion para que esta recupere informacion nueva
    @Override
    public CartaVista getCartaCantada() {
        return cartaCantada;
    }
    // Metodo que usa la PRESENTACION para obtener el marcado ACTUAL. se usa despues de que se le notifica a la presentacion para que esta recupere informacion nueva
    @Override
    public int getMarcador() {
        return marcador;
    }

    // Metodo que usa el MODELOJUEGO para enviar un nuevo marcador al ModeloVista. este despues usa notificar para que sea actualizado en presentacion.
    @Override
    public void setMarcador(int marcador) {
        this.marcador = marcador;
        notificar();
    }

    @Override
    public JugadorVista getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    @Override
    public void setJugadorPrincipal(JugadorVista jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    @Override
    public void actualizarTarjetaJugadorP (boolean[] casillas){
        jugadorPrincipal.getTarjeta().setMarcadas(casillas);
        notificar();
    }

    @Override
    public List<JugadorVista> getJugadoresSecundarios() {
        return jugadoresSecundarios;
    }

    @Override
    public void setJugadoresSecundarios(List<JugadorVista> jugadoresSecundarios) {
        this.jugadoresSecundarios = jugadoresSecundarios;
        notificar();
    }

    // Método llamado por el controlador cuando se selecciona una carta. este despues lo envia al modeloJuego donde maneja la logica de validacion.
    @Override
    public void seleccionarCarta(int pos) {
        modeloJuego.verificarCarta(pos);
    }

    //Metodo usado para agregar observadores en este caso la PRESENTACION se agrega como observador
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    //Metodo que notifica a los observadores (en este caso solamente la PRESENTACION). despues como la presentacion implementa metodos de observer, se ejecuta update() el cual repinta la pantalla despues de ser notificada.
    @Override
    public void notificar() {
        for (Observer o : observers) {
            o.update();
        }
    }
    
    
}

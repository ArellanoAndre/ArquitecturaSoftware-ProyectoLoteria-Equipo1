package ModeloJuego;

import ModeloVista.ModeloVista;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class ModeloJuego {

    private List<String> mazo;
    private String cartaActual;
    private int marcador;
    private Random random;
    private ModeloVista modeloVista;
    private Timer timer;

    public ModeloJuego(ModeloVista modeloVista) {
        this.modeloVista = modeloVista;
        random = new Random();
        //lista de cartas (selecciona una random con la variable random de arriba)
        mazo = Arrays.asList("El gallo", "La dama", "El catrín");
        marcador = 0;
    }

    //Inicia el juego enviandole una carta al modeloVista y despues lo vuelve a ejecutar repetidamente segun el tiempo indicado.
    public void iniciarJuego() {
        siguienteCarta();

        // Cada segundo cambia la carta cantada
        timer = new Timer(1000, e -> siguienteCarta());
        timer.start();
    }

    // Obtiene una nueva carta random del 1 al 3 y se la envia al modelo vista
    private void siguienteCarta() {
        cartaActual = mazo.get(random.nextInt(mazo.size()));
        modeloVista.setCartaCantada(cartaActual);
    }

    // Validación de la carta seleccionada y actualización del marcador
    public void verificarCarta(String cartaSeleccionada) {
        if (cartaSeleccionada.equals(cartaActual)) {
            marcador++;
        }
        // Actualiza ModeloVista con estado actual
        modeloVista.setMarcador(marcador);
    }

    //Regresa la carta actual del juego
    public String getCartaActual() {
        return cartaActual;
    }

    //Regresa el marcador actual del jugador
    public int getMarcador() {
        return marcador;
    }

    private List<Carta> crearMazo() {
        String[] nombres = {
            "El gallo", "El diablito", "La dama", "El catrín", "El paraguas", "La sirena",
            "La escalera", "La botella", "El barril", "El árbol", "El melón", "El valiente",
            "El gorrito", "La muerte", "La pera", "La bandera", "El bandolón", "El violoncello",
            "La garza", "El pájaro", "La mano", "La bota", "La luna", "El cotorro",
            "El borracho", "El negrito", "El corazón", "La sandía", "El tambor", "El camarón",
            "Las jaras", "El músico", "La araña", "El soldado", "La estrella", "El cazo",
            "El mundo", "El apache", "El nopal", "El alacrán", "La rosa", "La calavera",
            "La campana", "El cantarito", "El venado", "El sol", "La corona", "La chalupa",
            "El pino", "El pescado", "La palma", "La maceta", "El arpa", "La rana"
        };

        List<Carta> mazo = new ArrayList<>();
        for (int i = 0; i < nombres.length; i++) {
            mazo.add(new Carta(i + 1, nombres[i]));
        }
        return mazo;
    }

}

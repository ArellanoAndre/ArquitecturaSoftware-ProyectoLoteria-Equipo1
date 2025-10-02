
import Controlador.ControlSeleccionarCarta;
import ModeloJuego.ModeloJuego;
import ModeloJuego.entidades.Jugador;
import ModeloJuego.entidades.Tarjeta;
import ModeloVista.ControlVista;
import ModeloVista.ModeloVista;
import Presentacion.JPantallaJuego;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// ====== MAIN ======
public class main {

    public static void main(String[] args) {
        // Ejecutar jframe y creacion de objetos
        SwingUtilities.invokeLater(() -> {

            //Crear los jugadores y tarjetas
            int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};
            String img1 = "/img/Tableros/Tablero01.png";
            Tarjeta tarjetaPrueba1 = new Tarjeta(casillas1, img1);
            Jugador jugador1 = new Jugador("Rodri", tarjetaPrueba1, 1);

            int[] casillas2 = {29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36};
            String img2 = "/img/Tableros/Tablero02.png";
            Tarjeta tarjetaPrueba2 = new Tarjeta(casillas2, img2);
            Jugador jugador2 = new Jugador("Isaac", tarjetaPrueba2, 2);

            //Crear
            List<Jugador> jugadores1 = new ArrayList<>();
            jugadores1.add(jugador2);

            List<Jugador> jugadores2 = new ArrayList<>();
            jugadores2.add(jugador1);

            ModeloVista modeloVista = new ModeloVista();
            ControlVista controlVista = new ControlVista(modeloVista);

            ModeloJuego modeloJuego = new ModeloJuego(controlVista, jugador1, jugadores1);
            modeloVista.setModeloJuego(modeloJuego);
            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);

            ModeloVista modeloVista2 = new ModeloVista();
            ControlVista controlVista2 = new ControlVista(modeloVista2);
            ModeloJuego modeloJuego2 = new ModeloJuego(controlVista2, jugador2, jugadores2);
            modeloVista2.setModeloJuego(modeloJuego2);
            ControlSeleccionarCarta controlador2 = new ControlSeleccionarCarta(modeloVista2);

            modeloJuego.setModeloJuegoSecundario(modeloJuego2);
            modeloJuego2.setModeloJuegoSecundario(modeloJuego);

            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVista, controlador);
            JPantallaJuego pantalla2 = new JPantallaJuego(modeloVista2, controlador2);
            pantalla1.setTitle("Jugador1");
            pantalla2.setTitle("Jugador2");
            pantalla1.setVisible(true);
            pantalla2.setVisible(true);

            // Iniciar estado del juego
            modeloJuego.iniciarJuego();
            modeloJuego2.iniciarJuego();

        });
    }
}


import Controlador.ControlSeleccionarCarta;
import Controlador.ControlVista;
import ModeloVista.ModeloVistaJuego;
import Presentacion.JPantallaJuego;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// ====== MAIN ======
public class main {

    public static void main(String[] args) {
        // Ejecutar jframe y creacion de objetos
//        SwingUtilities.invokeLater(() -> {
//
//            //Crear los jugadores y tarjetas
//            int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};
//            String img1 = "/img/Tableros/Tablero01.png";
//            Tarjeta tarjetaPrueba1 = new Tarjeta(casillas1, img1);
//            Jugador jugador1 = new Jugador("Rodri", tarjetaPrueba1, 1);
//
//            int[] casillas2 = {29, 16, 3, 10, 14, 47, 40, 4, 53, 20, 35, 27, 15, 9, 51, 36};
//            String img2 = "/img/Tableros/Tablero02.png";
//            Tarjeta tarjetaPrueba2 = new Tarjeta(casillas2, img2);
//            Jugador jugador2 = new Jugador("Isaac", tarjetaPrueba2, 2);
//
//            //Crear
//            List<Jugador> jugadores = new ArrayList<>();
//            jugadores.add(jugador2);
//
//            
//            ModeloVistaJuego modeloVista = new ModeloVistaJuego();
//            ControlVista controlVista = new ControlVista(modeloVista);
//            
//            //Instancia de modeloJuego
//            ModeloJuego modeloJuego = new ModeloJuego(controlVista,jugador1, jugadores);
//            
//            modeloVista.setModeloJuego(modeloJuego);
//            
//            
//            
//            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);
//
//            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVista, controlador);
//            pantalla1.setTitle("Jugador1");
//            pantalla1.setVisible(true);


            // Crear al jugador 1 y su tarjeta
//            int[] casillas1 = {46, 6, 38, 3, 8, 11, 33, 35, 21, 54, 50, 29, 30, 40, 36, 26};
//            String img1 = "/img/Tableros/Tablero01.png";
//            Tarjeta tarjetaPrueba1 = new Tarjeta(casillas1, img1);
//            Jugador jugador1 = new Jugador("Rodri", tarjetaPrueba1, 1);
//
//            // Lista de jugadores (solo él mismo, o vacía si prefieres)
//            List<Jugador> jugadores = new ArrayList<>();
//            jugadores.add(jugador1);
//
//            // Crear MVC principal
//            ModeloVistaJuego modeloVista = new ModeloVistaJuego();
//            ControlVista controlVista = new ControlVista(modeloVista);
//            LogicaJuego modeloJuego = new LogicaJuego(controlVista, jugador1, jugadores);
//            modeloVista.setModeloJuego(modeloJuego);
//            ControlSeleccionarCarta controlador = new ControlSeleccionarCarta(modeloVista);
//
//            // Crear pantalla para el jugador 1
//            JPantallaJuego pantalla1 = new JPantallaJuego(modeloVista, controlador);
//            pantalla1.setTitle("Jugador 1 - Rodri");
//            pantalla1.setVisible(true);
//
//            // Iniciar estado del juego
//            modeloJuego.iniciarJuego();

//        });
    }
}

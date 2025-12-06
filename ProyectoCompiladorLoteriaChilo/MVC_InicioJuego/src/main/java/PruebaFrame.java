
import Controlador.ControladorInicio;
import Interfaces.IControladorInicio;
import ModeloVista.ModeloVista;
import Presentacion.JFrameLobby;
import Presentacion.JFrameSeleccionAvatar;
import modeloJuegoMVC.ModeloJuego;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Arell
 */
public class PruebaFrame {
       public static void main(String[] args) {
         // ======================================
            // 1. MVC Inicio (Modelo + Control + Pantalla)
            // ======================================
            ModeloVista modeloVista = new ModeloVista();

            IControladorInicio controlVista =
                    new ControladorInicio(modeloVista);

            // MODELO JUEGO del cliente
            ModeloJuego modeloJuego = new ModeloJuego();

            modeloVista.setModeloJuego(modeloJuego);

            // Crear pantalla inicial (selección avatar)
            JFrameLobby pantalla =
                    new JFrameLobby(controlVista);

            pantalla.setVisible(true);
       }
}

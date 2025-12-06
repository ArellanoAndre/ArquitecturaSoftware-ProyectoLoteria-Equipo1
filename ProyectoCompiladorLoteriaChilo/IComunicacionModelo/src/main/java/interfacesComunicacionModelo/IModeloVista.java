package interfacesComunicacionModelo;

import interfacesEntidades.IJugador;
import java.util.List;

public interface IModeloVista {
    
    void actualizarConfiguracion(String dificultad, List<IJugador> jugadores, int puntuacionMaxima);
     public void EnviarNombreAvatarConfirmado(String nombre, String avatar) ;
     public void setModeloJuego(IModeloJuego modeloJuego);
    
}

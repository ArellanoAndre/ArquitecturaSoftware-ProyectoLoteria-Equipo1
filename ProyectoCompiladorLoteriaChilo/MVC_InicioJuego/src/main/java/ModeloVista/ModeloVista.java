package ModeloVista;

    
import interfacesComunicacionModelo.IModeloJuego;
import interfacesComunicacionModelo.IModeloVista;
import interfacesEntidades.IJugador;
import java.util.List;
import modeloJuegoMVC.ModeloJuego;

public class ModeloVista implements IModeloVista {
    private IModeloJuego modeloJuego;

    public IModeloJuego getModeloJuego() {
        return modeloJuego;
    }

    public void setModeloJuego(IModeloJuego modeloJuego) {
        this.modeloJuego = modeloJuego;
    }

    
    public void EnviarNombreAvatarConfirmado(String nombre, String avatar) {
    System.out.println("[ModeloVista] → Solicitando UNIRSE_PARTIDA");

    if (modeloJuego != null) {
        modeloJuego.EnviarNombreAvatarConfirmado(nombre, avatar);
    }
}

    @Override
    public void actualizarConfiguracion(String dificultad, List<IJugador> jugadores, int puntuacionMaxima) {
        
    }
    

}
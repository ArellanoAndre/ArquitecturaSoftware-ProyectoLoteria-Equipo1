package ModeloVista;

import Interfaces.IModeloVista;
import java.util.HashMap;
import java.util.Map;

public class ModeloVista implements IModeloVista {

    private String dificultad;
    private Integer numeroJugadores;
    private Integer puntuacionMaxima;
    private Map<String, Integer> puntuaciones;
    private String nombreJugador;

    public ModeloVista() {
        puntuaciones = new HashMap<>();
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Integer getNumeroJugadores() {
        return numeroJugadores;
    }

    public void setNumeroJugadores(Integer numeroJugadores) {
        this.numeroJugadores = numeroJugadores;
    }

    public Integer getPuntuacionMaxima() {
        return puntuacionMaxima;
    }

    public void setPuntuacionMaxima(Integer puntuacionMaxima) {
        this.puntuacionMaxima = puntuacionMaxima;
    }

    public Map<String, Integer> getPuntuaciones() {
        return puntuaciones;
    }

    public void setPuntuaciones(Map<String, Integer> puntuaciones) {
        if (puntuaciones != null) {
            this.puntuaciones = new HashMap<>(puntuaciones);
        }
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }
}

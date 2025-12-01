package modeloJuego;

import Evento.Evento;
import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import InterfacesEventClient.IReceptorEvento;
import eventBuilder.EventBuilder;
import interfacesGlobales.IModeloLogica;
import logicaJuego.LogicaDeJuego;
import org.json.JSONObject;
import procesarCadena.IProcesadorEvento;
import procesarCadena.ProcesadorIniciar;
import procesarCadena.ProcesadorMarcar;
import procesarCadena.ProcesadorUnirse;

public class ModeloLogica implements IModeloLogica, IReceptorEvento {

    private LogicaDeJuego logicaDeJuego;
    private IEnvioEvento empaquetador;
    
    private IProcesadorEvento cadenaProcesamiento;

    public LogicaDeJuego getLogicaDeJuego() {
        return logicaDeJuego;
    }

    public IEnvioEvento getEmpaquetador() {
        return empaquetador;
    }

    public ModeloLogica() {
        // armar cadena de responsabilidad aqui
        IProcesadorEvento marcar = new ProcesadorMarcar();
        IProcesadorEvento unirse = new ProcesadorUnirse();
        IProcesadorEvento iniciar = new ProcesadorIniciar();

        // Conectamos los eslabones
        marcar.setSiguiente(unirse);
        unirse.setSiguiente(iniciar);

        this.cadenaProcesamiento = marcar; // Guardamos la referencia al primero
    }

    public void setLogicaDeJuego(LogicaDeJuego logica) {
        this.logicaDeJuego = logica;
    }

    public void setEmpaquetador(IEnvioEvento empaquetador) {
        this.empaquetador = empaquetador;
    }

    @Override
    public void EnviarEventoCartaSeleccionada(int pos, int idJugador) {

        System.out.println("[ModeloLogica-Host] Reenviando confirmación de selección...");
        notificarJugadaValida(pos, idJugador);
    }
    
    
    public void manejar(Evento payloadJSON) {

        System.out.println("[ModeloLogica-Host] Procesando payload: " + payloadJSON);

        if (logicaDeJuego == null) {
            System.err.println("ERROR: LogicaDeJuego es null");
            return;
        }

        try {
            JSONObject peticion = new JSONObject(payloadJSON);

            if (peticion.has("TipoEvento")) {
                String tipo = peticion.getString("TipoEvento");

                cadenaProcesamiento.procesar(tipo, peticion, logicaDeJuego);

            } else {
                System.err.println("JSON ignorado: No tiene TipoEvento");
            }

        } catch (Exception e) {
            System.err.println("Error interpretando JSON: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public void notificarCartaCantada(int idCarta, String nombreCarta) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CARTA_CANTADA");
        json.put("IdCarta", idCarta);
        json.put("Nombre", nombreCarta);

        enviarEventoBroadcast(json.toString(), "Juego-out");

    }

    @Override
    public void notificarJugadaValida(int posicion, int idJugador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "CasillaSeleccionadaValida");
        json.put("Jugador", idJugador);
        json.put("Casilla", posicion - 1);

        enviarEventoBroadcast(json.toString(), "Juego-out");

    }

    @Override
    public void notificarGanador(String nombreGanador) {

        JSONObject json = new JSONObject();
        json.put("TipoEvento", "GANADOR");
        json.put("Nombre", nombreGanador);

        enviarEventoBroadcast(json.toString(), "Juego-out");

    }

    private void enviarEventoBroadcast(String jsonPayload, String topico) { // para no repetir codigo

       if (empaquetador == null) {
           return;
       }

        IEvento evento = empaquetador.crearEvento();
        evento.setTopico(topico);
        evento.setEvento("ActualizacionJuego");
        evento.setJSON(jsonPayload);

        empaquetador.enviarEvento(evento);
        System.out.println("[ModeloLogica-Host] Enviado evento: " + jsonPayload);

    }

    @Override
    public void manejar(IEvento evento) {
         
        String jsonPayload = evento.getJSON();
        System.out.println("ModeloLogica: Evento recibido: " + jsonPayload);
        
        if (logicaDeJuego == null ) {
            System.out.println("logicajuego nula");
            return;
        }
        
        try { 
            JSONObject peticion = new JSONObject(jsonPayload);
            
            if ( peticion.has("TipoEvento")) {
                String tipo = peticion.getString("TipoEvento");
                cadenaProcesamiento.procesar(tipo, peticion, logicaDeJuego);
            }
        } catch (Exception e)  {
            System.err.println("Error procesando entrada" + e.getMessage());
        }
    }

    
}

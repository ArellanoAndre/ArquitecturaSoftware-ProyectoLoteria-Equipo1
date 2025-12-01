/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filtros;

import Evento.Evento;
import InterfacesEventClient.IEnvioEvento;
import InterfacesEventClient.IEvento;
import interfaces.IFiltro;
import java.util.List;
import suscripciones.Suscripcion;
import suscripciones.gestorDeSuscripciones;

/**
 *
 * @author abrilislas
 */
public class FiltroGenericoEvento implements IFiltro {

    protected IFiltro succesor;
    private gestorDeSuscripciones gestorSuscripciones;
    private IEnvioEvento empaquetador;

    @Override
    public void setNext(IFiltro succesor) {
        this.succesor = succesor;
    }

    @Override
    public void procesarEvento(Evento evento) {
        String topico = evento.getTopico();
        List<Suscripcion> lista = gestorSuscripciones.obtenerSuscriptores(topico);

        if (lista == null || lista.isEmpty()) {
            System.out.println("[FiltroGenericoEvento] No hay suscriptores para: " + topico);
            return;
        }

        System.out.println("[FiltroGenericoEvento] Notificando a suscriptores del topico: " + topico);

        for (Suscripcion s : lista) {

            System.out.println("  Enviando a " + s);

            // ⭐ Clonamos el evento
            Evento copia = new Evento();
            copia.setTopico(evento.getTopico());
            copia.setEvento(evento.getEvento());
            copia.setJSON(evento.getJSON());
            copia.setIpLocal(evento.getIpLocal());
            copia.setPuertoLocal(evento.getPuertoLocal());

            // ⭐ Datos destino
            copia.setIpDestino(s.getHost());
            copia.setPuertoDestino(s.getPuerto());

            enviarEvento(copia);
        }
    }

    public void enviarEvento(IEvento evento) {
        if (empaquetador != null) {
            empaquetador.enviarEvento(evento);
        }
        //System.out.println("[EnvioEvento] Enviando evento a suscriptores: " + evento.getTopico());
    }

    public void setEmpaquetador(IEnvioEvento empaquetador) {
        this.empaquetador = empaquetador;
    }

    public void setGestorSuscripciones(gestorDeSuscripciones gestorSuscripciones) {
        this.gestorSuscripciones = gestorSuscripciones;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Brok;

/**
 *
 * @author Arell
 */

import ConvertidorJSON.Evento;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un canal o tópico donde se publican eventos.
 */
public class Topico {

    private final String nombre;
    private final List<SuscriptorCallback> suscriptores = new ArrayList<>();

    public Topico(String nombre) {
        this.nombre = nombre;
    }

    public void publicar(Evento evento) {
        System.out.println("[Topico " + nombre + "] 🔄 Enviando evento a " + suscriptores.size() + " suscriptores");
        for (SuscriptorCallback s : suscriptores) {
            s.onEvent(evento);
        }
    }

    public void suscribir(SuscriptorCallback suscriptor) {
        suscriptores.add(suscriptor);
    }

    public String getNombre() {
        return nombre;
    }
}

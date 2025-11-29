/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Evento.Evento;

/**
 *
 * @author abrilislas
 */
public abstract interface IFiltro {
    
    void setNext(IFiltro succesor);
    void procesarEvento(Evento evento);
}

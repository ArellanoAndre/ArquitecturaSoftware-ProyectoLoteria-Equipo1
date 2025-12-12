/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesEntidades;

/**
 *
 * @author rodri
 */
public interface ITarjeta {

    public int getCasilla(int numCasilla);

    public void marcarCasilla(int numCasilla);

    public boolean[] getMarcadas();

    public int[] getCasillas();

    public String getImg();

    public void setImg(String img);

    public void setMarcadas(boolean[] marcadas);
   

}

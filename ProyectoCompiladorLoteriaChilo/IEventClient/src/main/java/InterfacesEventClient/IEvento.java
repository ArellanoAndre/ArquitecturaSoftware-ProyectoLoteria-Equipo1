/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package InterfacesEventClient;

/**
 *
 * @author rodri
 */
public interface IEvento {

    public String getTopico();

    public void setTopico(String topico);

    public String getEvento();

    public void setEvento(String evento);

    public String getJSON();

    public void setJSON(String JSON);

    public String getIpLocal();

    public void setIpLocal(String ipLocal);

    public String getIpDestino();

    public void setIpDestino(String ipDestino);

    public int getPuertoLocal();

    public void setPuertoLocal(int puerto);

    public int getPuertoDestino();

    public void setPuertoDestino(int puerto);
}

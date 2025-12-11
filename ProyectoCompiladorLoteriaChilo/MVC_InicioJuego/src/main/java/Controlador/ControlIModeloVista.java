/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import ModeloVista.ModeloVistaInicio;
import interfacesEntidades.IConfiguracionPartida;
import interfacesComunicacionModelo.IControlVistaMVC_Inicio;

/**
 *
 * @author Arell
 */
public class ControlIModeloVista implements IControlVistaMVC_Inicio {

    private final ModeloVistaInicio modeloVista;
    private IConfiguracionPartida config;

    public ControlIModeloVista(ModeloVistaInicio modeloVista, IConfiguracionPartida config) {
        this.modeloVista = modeloVista;
        this.config = config;
    }

    public void setConfig(IConfiguracionPartida config) {
        this.config = config;
    }

    @Override
    public void actualizarPantalla(IConfiguracionPartida nuevaConfig) {
        if (nuevaConfig != null) {
            this.config = nuevaConfig;
        }

        System.out.println("[ControlIModeloVista] Actualizando pantalla (delegando en ModeloVistaInicio)...");
        System.out.println("  -> ModeloVistaInicio instancia = " + modeloVista);
        System.out.println("  -> Observers registrados = " + modeloVista.getObservers().size());

        modeloVista.actualizarConfiguracion(this.config);
    }

    @Override
    public void abrirPantallaConfig(int id) {
        modeloVista.abrirPantallaConfig(id);
    }

    @Override
    public void abrirPantallaAvatar(int id) {
        modeloVista.abrirPantallaAvatar(id);
    }

    @Override
    public void cambiarMVC() {
        modeloVista.notificarCambioMVC();
    }
    
    @Override
    public void abrirPantallaMenu(int idJugador) {
        modeloVista.abrirPantallaMenu(idJugador);
        
    }

}

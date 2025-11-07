/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

/**
 *
 * @author abrilislas
 */
public interface IDispatcher {
    
    void dispatch(String json);
    void registerListener(IReceptor receptor);
}

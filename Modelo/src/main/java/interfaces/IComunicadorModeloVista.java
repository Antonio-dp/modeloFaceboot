/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Usuario;

/**
 * Intemediario para realizar la comunicación entre el modelo y la vista
 * @author jegav
 */
public interface IComunicadorModeloVista {
    public void notificarRegistroUsuario(Usuario usuario);
    public void notificarFalloOperacion(String error, int status);
}

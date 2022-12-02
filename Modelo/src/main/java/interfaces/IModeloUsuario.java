/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Usuario;
import excepciones.FacebootException;
import excepciones.PersistException;

/**
 *
 * @author tonyd
 */
public interface IModeloUsuario {
    public Usuario consultar(Integer idUsuario);
    public Usuario login(Usuario usuario);
    public Usuario loginFacebook(Usuario usuario);
    public Usuario actualizar(Usuario usuario);
    public Usuario eliminar(Integer idUsuario);
    public Usuario registrar(Usuario usuario) throws PersistException, FacebootException;
    public Usuario consultarUsuarioPorNombre(String nombre);
}

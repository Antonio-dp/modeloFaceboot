/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Usuario;
import excepciones.FacebootException;
import excepciones.PersistException;

/**
 * Interfaz que permite realizar el CRUD de usuarios en BD, Permite hacer un loggeo
 * de las acciones CRUD realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public interface IModeloUsuario {
    /**
     * Permite consultar un usuario en base de datos con el id del usuario dado
     * por el parametro
     * @param idUsuario id del usuario
     * @return usuario consultado
     */
    public Usuario consultar(Integer idUsuario);
    /**
     * Permite verificar las credenciales (Email y contraseña) de login en BD 
     * para encontrar si el usuario esta registrado.
     * @param usuario usuario a buscar
     * @return usuario registrado
     */
    public Usuario login(Usuario usuario);
    /**
     * Permite verificar las credenciales (idFacebook) de login por facebook 
     * en BD para encontrar si el usuario esta registrado.
     * @param usuario usuario a buscar
     * @return usuario registrado con facebook 
     */
    public Usuario loginFacebook(Usuario usuario);
    /**
     * Permite actualizar un usuario en base de datos con el usuario dado
     * por el parametro
     * @param usuario usuario a actualizar
     * @return usuario actualizado
     */
    public Usuario actualizar(Usuario usuario);
    /**
     * Permite eliminar un usuario en base de datos con el id del usuario dado
     * por el parametro
     * @param idUsuario id del usuario a eliminar
     * @return usuario eliminado 
     */
    public Usuario eliminar(Integer idUsuario);
    /**
     * Permite registrar a un usuario en base de datos con el usuario dado
     * por el parametro
     * @param usuario usuario a registrar
     * @return usuario registrado
     * @throws PersistException Lanza una excepcion si ocurre un error al
     * registrar el usuario
     * @throws FacebootException Lanza una excepcion si el email ya esta 
     * registrado
     */
    public Usuario registrar(Usuario usuario) throws PersistException, FacebootException;
    /**
     * Permite consultar usuarios por el nombre en la base de datos con el 
     * nombre dado por el parametro
     * @param nombre nombre del usuario
     * @return usuario consultado
     */
    public Usuario consultarUsuarioPorNombre(String nombre);
}

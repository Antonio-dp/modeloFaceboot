/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Notificacion;
import entidades.Usuario;
import excepciones.NotFoundException;
import excepciones.PersistException;
import java.util.List;

/**
 * Interfaz que permite realizar el registro y la consulta de notificaciones en BD.
 * Permite hacer un loggeo de las acciones realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public interface IModeloNotificacion {
    /**
     * Permite registrar en la base de datos una notificacion dada por el 
     * parametro, llama al notificador para notificar utilizando el
     * patron decorator
     * @param notificacion notificacion a registrar
     * @return notificacion registrada
     * @throws PersistException Lanza una excepcion si ocurre un error al 
     * registrar una notificacion
     */
    public Notificacion registrar(Notificacion notificacion) throws PersistException;
    /**
     * Permite consultar de la base de datos la notificacion con el id de la 
     * notificacion dada por el parametro
     * @param idNotificacion id de la notificacion
     * @return Notificacion consultada
     * @throws NotFoundException Lanza una ecepcion si no se pudo realizar la 
     * consulta
     */
    public Notificacion consultar(Integer idNotificacion) throws NotFoundException;
    /**
     * Permite consultar en la base de datos una lista de notificaciones hechas
     * por un determinado remitente
     * @param remitente usuario remitente
     * @return lista de notificaciones hechas por el usuario remitente
     * @throws NotFoundException Lanza una excepcion si ocurre un error al 
     * consultar las notificaciones del remitente 
     */
    public List<Notificacion> consultarNotificacionesPorRemitente(Usuario remitente) throws NotFoundException;
}

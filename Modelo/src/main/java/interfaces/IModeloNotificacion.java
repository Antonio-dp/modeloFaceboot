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
 *
 * @author tonyd
 */
public interface IModeloNotificacion {
    public Notificacion registrar(Notificacion notificacion) throws PersistException;
    public Notificacion consultar(Integer idNotificacion) throws NotFoundException;
    public List<Notificacion> consultarNotificacionesPorRemitente(Usuario remitente) throws NotFoundException;
}

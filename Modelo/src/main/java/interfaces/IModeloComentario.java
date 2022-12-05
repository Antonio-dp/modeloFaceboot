/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import entidades.Comentario;
import excepciones.NotFoundException;
import excepciones.PersistException;
import java.util.List;
/**
 *
 * @author tonyd
 */
public interface IModeloComentario {
    public Comentario consultar(Integer idComentario) throws NotFoundException;
    public Comentario eliminar(Comentario Comentario) throws PersistException;
    public Comentario registrar(Comentario comentario) throws PersistException;
    public List<Comentario> consultarComentarios(Integer idPublicacion) throws PersistException;
}

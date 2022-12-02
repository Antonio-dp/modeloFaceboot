/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;
import entidades.Publicacion;
import excepciones.NotFoundException;
import excepciones.PersistException;
import java.util.List;
/**
 *
 * @author tonyd
 */
public interface IModeloPublicacion {
    public Publicacion consultar(Integer idPublicacion) throws NotFoundException;
    public Publicacion eliminar(Integer idPublicacion) throws PersistException;
    public Publicacion registrar(Publicacion publicacion) throws PersistException;
    public List<Publicacion> consultarPublicaciones()throws NotFoundException;
    public List<Publicacion> consultarPublicacionesPorEtiqueta(String hashtag) throws Exception;
}

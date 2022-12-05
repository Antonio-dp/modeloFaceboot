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
 * Fachada que permite realizar el CRUD de publicaciones en BD, Permite hacer un
 * loggeo de las acciones CRUD realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public interface IModeloPublicacion {
    /**
     * Permite consultar en base de datos la publicacion con el id de la 
     * publicacion dada por el parametro
     * @param idPublicacion id de la publicacion
     * @return publicacion consultada
     * @throws NotFoundException Lanza una excepcion si ocurre un error al
     * realizar la consulta de la publicacion
     */
    public Publicacion consultar(Integer idPublicacion) throws NotFoundException;
    /**
     * Permite eliminar una publicacion en base de datos con el id de la 
     * publicacion dado por el parametro
     * @param idPublicacion id de la publicacion
     * @return publicacion eliminada
     * @throws PersistException Lanza una excepcion si ocurre un error al 
     * eliminar la publicacion
     */
    public Publicacion eliminar(Integer idPublicacion) throws PersistException;
    /**
     * Permite registrar una publicacion en base de datos con la publicacion 
     * dada por el parametro
     * @param publicacion publicacion a registrar
     * @return publicacion registrada
     * @throws PersistException lanza una excepcion si ocurre un error al
     * registrar la publicación
     */
    public Publicacion registrar(Publicacion publicacion) throws PersistException;
    /**
     * Permite consultar todas las publicaciones existentes en base de datos
     * @return lista de publicaciones consultadas
     * @throws NotFoundException Lanza una excepcion si ocurre un error al 
     * consultar todas las publicaciones
     */
    public List<Publicacion> consultarPublicaciones()throws NotFoundException;
    /**
     * Permite consultar las publicaciones por la etiqueta que existan en base 
     * de datos, utilizando como parametro una string con el tema del hashtag
     * @param hashtag tema del hashtag
     * @return lista de publicaciones con determinado hashtag
     * @throws Exception Lanza una excepcion si ocurre un error al consultar
     * las publicaciones con determinado hashtag
     */
    public List<Publicacion> consultarPublicacionesPorEtiqueta(String hashtag) throws Exception;
}

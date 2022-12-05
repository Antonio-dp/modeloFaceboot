/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import comunicacion.ComunicadorServidor;
import interfaces.IComunicadorServidor;
import entidades.Comentario;
import entidades.Publicacion;
import excepciones.NotFoundException;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloComentario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que permite realizar el CRUD de comentarios en BD, Permite hacer un
 * loggeo de las acciones CRUD realizadas
 *
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ModeloComentario implements IModeloComentario {

    /**
     * Objeto que permite conectar con BD
     */
    private final IConexionBD conexionBD;
    /**
     * Objeto que permite notificar al servidor en caso de ser necesario
     */
    private IComunicadorServidor comunicadorServidor;
    /**
     * Objeto que permite loggear las acciones CRUD y guardarlas en BD
     */
    private static Logger log = LogManager.getLogger(ModeloComentario.class);

    /**
     * Instancia la conexion a base de datos, al valor de su parametro,
     * instancia la comunicacion con el servidor a su instancia concreta
     * @param conexionBD conexion con BD
     */
    public ModeloComentario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.comunicadorServidor = new ComunicadorServidor();
    }

    /**
     * Permite consultar en la base de datos el comentario con el id dado por el
     * parametro.
     * @param idComentario id del comentario a buscar
     * @return Comentario consultado
     * @throws NotFoundException Lanza una excepcion si ocurre un error al
     * consultar del comentario
     */
    @Override
    public Comentario consultar(Integer idComentario) throws NotFoundException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            log.info("Consultar comentario"+idComentario);
            return em.find(Comentario.class, idComentario);
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo encontrar el comentario");
        }
    }

    /**
     * Permite eliminar de la base de datos el comentario con el comentario dado
     * por el parametro.
     * Notifica al servidor cuando se elimino el comentario
     * @param comentario comentario a eliminar
     * @return Comentario eliminado
     * @throws PersistException Lanza una excepcion si ocurre un error al
     * eliminar el comentario
     */
    @Override
    public Comentario eliminar(Comentario comentario) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Comentario e WHERE e.id = :idComentario");
            query.setParameter("idComentario", comentario.getId()).executeUpdate();
            comunicadorServidor.notificarEliminarComentario(comentario);
            em.getTransaction().commit();
            log.info("Eliminar comentario"+comentario.getId());
            return comentario;
        } catch (IllegalStateException e) {
            throw new PersistException("No se pudó eliminar el comentario");
        }
    }

    /**
     * Permite registrar en la base de datos el comentario dado por el parametro.
     * Notifica al servidor cuando se registro un comentario
     * @param comentario comentario a registrar
     * @return Comentario registrado
     * @throws PersistException Lanza una excepcion si ocurre un error al
     * registrar el comentario
     */
    @Override
    public Comentario registrar(Comentario comentario) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
            comunicadorServidor.notificarNuevoComentario(comentario);
            log.info("Registrar comentario"+comentario.getId());
            return comentario;
        } catch (IllegalStateException e) {
            throw new PersistException("Error al registrar comentarios");
        }
    }

    /**
     * Permite consultar de la base de datos todos los comentarios con el id de
     * una publicacion dado por el parametro
     * @param idPublicacion id de publicacion
     * @return lista de comentarios de una publicación
     * @throws PersistException Lanza una excepcion si ocurre un error al
     * consultar la lista de comentarios
     */
    @Override
    public List<Comentario> consultarComentarios(Integer idPublicacion) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            ModeloPublicacion modeloPublicacion = new ModeloPublicacion(conexionBD);
            Publicacion publicacion = modeloPublicacion.consultar(idPublicacion);
            Query query = em.createQuery("SELECT e FROM Comentario e WHERE e.publicacion = :idPublicacion ");
            query.setParameter("idPublicacion", publicacion);
            log.info("Consultar comentarios por publicacion"+idPublicacion);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistException("Error al consultar comentarios");
        }
    }
}

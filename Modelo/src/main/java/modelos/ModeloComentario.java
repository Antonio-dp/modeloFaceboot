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
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author tonyd
 */
public class ModeloComentario implements IModeloComentario {

    private final IConexionBD conexionBD;
    private IComunicadorServidor comunicadorServidor;
        private static Logger log = LogManager.getLogger(ModeloUsuario.class);

    public ModeloComentario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        this.comunicadorServidor = new ComunicadorServidor();
    }

    @Override
    public Comentario consultar(Integer idComentario) {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            return em.find(Comentario.class, idComentario);
        } catch (IllegalStateException e) {
            System.err.println("No se pudo consultar el comentario" + idComentario);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Comentario eliminar(Comentario comentario) throws PersistException{
        EntityManager em = this.conexionBD.crearConexion();
        try {
//            Query query = em.createQuery("DELETE e FROM Comentario e WHERE e.id= :idComentario ");
//            query.setParameter("idComentario", comentario.);
            em.getTransaction().begin();  
            Query query = em.createQuery("DELETE FROM Comentario e WHERE e.id = :idComentario");
            query.setParameter("idComentario", comentario.getId()).executeUpdate();
            comunicadorServidor.notificarEliminarComentario(comentario);
            em.getTransaction().commit();
            return comentario;
        } catch (IllegalStateException e) {
            throw new PersistException("No se pudó eliminar el comentario");
        }
    }

    @Override
    public Comentario registrar(Comentario comentario) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
            comunicadorServidor.notificarNuevoComentario(comentario);
            return comentario;
        } catch (IllegalStateException e) {
            throw new PersistException("Error al registrar comentarios");
        }
    }

    @Override
    public List<Comentario> consultarComentarios(Integer idPublicacion) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            ModeloPublicacion modeloPublicacion = new ModeloPublicacion(conexionBD);
            Publicacion publicacion = modeloPublicacion.consultar(idPublicacion);
            Query query = em.createQuery("SELECT e FROM Comentario e WHERE e.publicacion = :idPublicacion ");
            query.setParameter("idPublicacion", publicacion);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistException("Error al consultar comentarios");
        } 
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import comunicacion.ComunicadorServidor;
import interfaces.IComunicadorServidor;
import interfaces.IComunicadorServidor;
import entidades.Comentario;
import entidades.Hashtag;
import entidades.Publicacion;
import excepciones.NotFoundException;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloHashtag;
import interfaces.IModeloPublicacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author tonyd
 */
public class ModeloPublicacion implements IModeloPublicacion {

    private final IConexionBD conexionBD;
    private IComunicadorServidor comunicadorServidor;
    private static Logger log = LogManager.getLogger(ModeloPublicacion.class);

    public ModeloPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        comunicadorServidor = new ComunicadorServidor();
    }

    @Override
    public Publicacion consultar(Integer idPublicacion) throws NotFoundException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            return em.find(Publicacion.class, idPublicacion);
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo consultar la publicacion en la BD");
        }
    }

    @Override
    public List<Publicacion> consultarPublicaciones() throws NotFoundException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            Query query = em.createQuery("SELECT e FROM Publicacion e");
            return query.getResultList();
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo consultar las publicaciones de la BD");
        }
    }

    @Override
    public Publicacion eliminar(Integer idPublicacion) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            ModeloComentario mc = new ModeloComentario(this.conexionBD);
            for (Comentario consultarComentario : mc.consultarComentarios(idPublicacion)) {
                mc.eliminar(consultarComentario);
            }
            em.getTransaction().begin();
            Publicacion publicacion = this.consultar(idPublicacion);
            Query query = em.createQuery("DELETE FROM Publicacion e WHERE e.id = :idPublicacion");
            query.setParameter("idPublicacion", idPublicacion).executeUpdate();
            this.comunicadorServidor.notificarEliminarPublicacion(publicacion);
            em.getTransaction().commit();
            return publicacion;
        } catch (Exception e) {
            throw new PersistException("No se pudo registrar la publicacion en la BD");
        }
    }

    @Override
    public Publicacion registrar(Publicacion publicacion) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            ModeloHashtag modelo = new ModeloHashtag(conexionBD);
            if (publicacion.getHashtagPublicacion() != null) {
                List<Hashtag> hashtagsRegistrados = modelo.registrarHashtags(publicacion.getHashtagPublicacion());
                publicacion.setHashtagPublicacion(hashtagsRegistrados);
            }
            em.getTransaction().begin();
            em.persist(publicacion);
            em.getTransaction().commit();
            log.info("Registro Publicacion " + publicacion.getId());
            comunicadorServidor.notificarNuevaPublicacion(publicacion);
            return publicacion;
        } catch (IllegalStateException e) {
            throw new PersistException("No se pudo registrar la publicacion en la BD");
        }
    }

    @Override
    public List<Publicacion> consultarPublicacionesPorEtiqueta(String hashtag) throws Exception {
        ModeloHashtag modeloHashtag = new ModeloHashtag(conexionBD);
        List<Publicacion> publicacionesRespuesta = new ArrayList();
        Hashtag hashtagRegistrado = modeloHashtag.consultarPorTema(hashtag);

        for (Publicacion publicacion : this.consultarPublicaciones()) {
            if (publicacion.getHashtagPublicacion().contains(hashtagRegistrado)) {
                publicacionesRespuesta.add(publicacion);
            }
        }

        return publicacionesRespuesta;
    }

}

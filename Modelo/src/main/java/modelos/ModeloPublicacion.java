/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import comunicacion.ComunicadorServidor;
import interfaces.IComunicadorServidor;
import entidades.Comentario;
import entidades.Hashtag;
import entidades.Publicacion;
import excepciones.NotFoundException;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloPublicacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que permite realizar el CRUD de publicaciones en BD, Permite hacer un
 * loggeo de las acciones CRUD realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ModeloPublicacion implements IModeloPublicacion {
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
    private static Logger log = LogManager.getLogger(ModeloPublicacion.class);
    /**
     * Instancia la conexion a base de datos, al valor de su parametro,
     * instancia la comunicacion con el servidor a su instancia concreta
     * @param conexionBD conexion con BD
     */
    public ModeloPublicacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
        comunicadorServidor = new ComunicadorServidor();
    }
    /**
     * Permite consultar en base de datos la publicacion con el id de la 
     * publicacion dada por el parametro
     * @param idPublicacion id de la publicacion
     * @return publicacion consultada
     * @throws NotFoundException Lanza una excepcion si ocurre un error al
     * realizar la consulta de la publicacion
     */
    @Override
    public Publicacion consultar(Integer idPublicacion) throws NotFoundException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            log.info("Consultar publicacion"+idPublicacion);
            return em.find(Publicacion.class, idPublicacion);
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo consultar la publicacion en la BD");
        }
    }
    /**
     * Permite consultar todas las publicaciones existentes en base de datos
     * @return lista de publicaciones consultadas
     * @throws NotFoundException Lanza una excepcion si ocurre un error al 
     * consultar todas las publicaciones
     */
    @Override
    public List<Publicacion> consultarPublicaciones() throws NotFoundException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            Query query = em.createQuery("SELECT e FROM Publicacion e");
            log.info("Consultar publicaciones");
            return query.getResultList();
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo consultar las publicaciones de la BD");
        }
    }
    /**
     * Permite eliminar una publicacion en base de datos con el id de la 
     * publicacion dado por el parametro
     * @param idPublicacion id de la publicacion
     * @return publicacion eliminada
     * @throws PersistException Lanza una excepcion si ocurre un error al 
     * eliminar la publicacion
     */
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
            log.info("Eliminar publicacion"+idPublicacion);
            return publicacion;
        } catch (Exception e) {
            throw new PersistException("No se pudo registrar la publicacion en la BD");
        }
    }
    /**
     * Permite registrar una publicacion en base de datos con la publicacion 
     * dada por el parametro
     * @param publicacion publicacion a registrar
     * @return publicacion registrada
     * @throws PersistException lanza una excepcion si ocurre un error al
     * registrar la publicación
     */
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
    /**
     * Permite consultar las publicaciones por la etiqueta que existan en base 
     * de datos, utilizando como parametro una string con el tema del hashtag
     * @param hashtag tema del hashtag
     * @return lista de publicaciones con determinado hashtag
     * @throws Exception Lanza una excepcion si ocurre un error al consultar
     * las publicaciones con determinado hashtag
     */
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
        log.info("consulta de publicaciones por tema de hashtag "+hashtag);
        return publicacionesRespuesta;
    }

}

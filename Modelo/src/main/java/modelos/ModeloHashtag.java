/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import entidades.Hashtag;
import excepciones.NotFoundException;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloHashtag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase que permite realizar el registro, el consultado y el eliminado de
 * Hashtags en BD. Permite hacer un loggeo de las acciones realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ModeloHashtag implements IModeloHashtag {
    /**
     * Objeto que permite conectar con BD
     */
    private final IConexionBD conexionBD;
    /**
     * Objeto que permite loggear las acciones CRUD y guardarlas en BD
     */
    private static Logger log = LogManager.getLogger(ModeloHashtag.class);
    /**
     * Instancia la conexion a base de datos, al valor de su parametro
     * @param conexionBD conexion con BD
     */
    public ModeloHashtag(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    /**
     * Permite registrar en la base de datos el hashtag dado por el parametro
     * @param hashtag hashtag a registrar
     * @return hashtag registrado
     * @throws PersistException Lanza una excepcion si ocurre un error al 
     * registrar el hashtag
     */
    @Override
    public Hashtag registrar(Hashtag hashtag) throws PersistException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(hashtag);
            em.getTransaction().commit();
            log.info("Registro Hashtag" + hashtag.getId());
            return hashtag;
        } catch (IllegalStateException e) {
            throw new PersistException("No se pudo Registrar en la BD");
        }
    }
    /**
     * Permite registrar una lista de hashtags dadas por el parametro
     * @param hashtags lista de hashtags a registrar
     * @return hashtags registrados
     * @throws PersistException Lanza una excepcion si ocurre un error al 
     * registrar los hashtags
     */
    @Override
    public List<Hashtag> registrarHashtags(List<Hashtag> hashtags) throws PersistException {
        List<Hashtag> hashtagsRegistrados = new ArrayList();
        for (Hashtag hashtag : hashtags) {
            if (!existeHashtag(hashtag)) {
                this.registrar(hashtag);
            }
            Hashtag hashtagRegistrado;
            try {
                hashtagRegistrado = this.consultarPorTema(hashtag.getTema());
                hashtagsRegistrados.add(hashtagRegistrado);
                log.info("Registro de Hashtags" + hashtagRegistrado.getId());
            } catch (Exception ex) {
                throw new PersistException("No se pudo registrar en la BD");
            }
    }
        return hashtagsRegistrados;
    }
    /**
     * Permite consultar en la base de datos el hashtag con el id dado por el
     * parametro
     * @param idHashtag id del hashtag a buscar
     * @return hashtag consultado
     * @throws NotFoundException Lanza una excepcion si ocurre un error al 
     * consultar el hashtag
     */
    @Override
    public Hashtag consultar(Integer idHashtag) throws NotFoundException {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            log.info("Consulta Hashtag" + idHashtag);
            return em.find(Hashtag.class, idHashtag);
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo registrar en la BD");
        }
    }
    /**
     * Permite eliminar de la base de datos el hashtag dado por el parametro
     * @param idHashtag id del hashtag a eliminar
     * @return hashtag eliminado
     * @throws PersistException Lanza una excepcion si ocurre un error al
     * eliminar un hashtag
     */
    @Override
    public Hashtag eliminar(Integer idHashtag) throws PersistException {
        try {
            EntityManager em = this.conexionBD.crearConexion();
            Hashtag hashtag;
            hashtag = this.consultar(idHashtag);
            if (hashtag != null) {
                try {
                    em.getTransaction().begin();
                    em.remove(hashtag);
                    em.getTransaction().commit();
                    log.info("Eliminacion Hashtag" + hashtag.getId());
                    return null;
                } catch (IllegalStateException e) {
                    throw new PersistException("No se pudo eliminar en la BD");
                }
            } else {
                return null;
            }
        } catch (NotFoundException ex) {
            throw new PersistException("No se pudo eliminar en la BD");
        }
    }
    /**
     * Permite consultar por tema en la base de datos con el tema del hashtag 
     * dado por el parametro
     * @param hashtag String con el tema para buscar
     * @return hashtag encontrado
     * @throws Exception Lanza una excepcion si ocurre un error al consultar
     * el hashtag
     */
    @Override
    public Hashtag consultarPorTema(String hashtag) throws Exception {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            Query query = em.createQuery("SELECT e FROM Hashtag e WHERE e.tema= :nombreHashtag", Hashtag.class);
            query.setParameter("nombreHashtag", hashtag);
            List<Hashtag> hashtags = query.getResultList();
            if (hashtags.isEmpty()) {
                throw new Exception("No se encontró un hashtag");
            }
            log.info("Hashtag consultado" + hashtag);
            return hashtags.get(0);
        } catch (IllegalStateException e) {
            throw new Exception("No pudo realizar la consulta");
        }
    }
    /**
     * Permite saber si el hashtag a consultar por tema existe
     * @param hashtag hashtag a consultar
     * @return boolean para saber si existe o no
     */
    public boolean existeHashtag(Hashtag hashtag) {
        try {
            this.consultarPorTema(hashtag.getTema());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import entidades.Hashtag;
import entidades.Hashtag;
import entidades.Usuario;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloHashtag;
import interfaces.INotificador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author tonyd
 */
public class ModeloHashtag implements IModeloHashtag {

    private final IConexionBD conexionBD;
    private static Logger log = LogManager.getLogger(ModeloHashtag.class);

    public ModeloHashtag(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public Hashtag registrar(Hashtag hashtag) throws PersistException{
        EntityManager em = this.conexionBD.crearConexion(); //Establece conexion con la BD
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

    @Override
    public List<Hashtag> registrarHashtags(List<Hashtag> hashtags) throws PersistException{
        List<Hashtag> hashtagsRegistrados = new ArrayList();
        for (Hashtag hashtag : hashtags) {
            if(!existeHashtag(hashtag)){
                this.registrar(hashtag);
            }
            Hashtag hashtagRegistrado;
            try {
                hashtagRegistrado = this.consultarPorTema(hashtag.getTema());
                hashtagsRegistrados.add(hashtagRegistrado);
            } catch (Exception ex) {
                throw new PersistException("No se pudo registrar en la BD");
            }
        }
        return hashtagsRegistrados;
    }
    
    @Override
    public Hashtag consultar(Integer idHashtag) throws PersistException{
        EntityManager em = this.conexionBD.crearConexion();
        try {
            return em.find(Hashtag.class, idHashtag);
        } catch (IllegalStateException e) {
            throw new PersistException("No se pudo registrar en la BD");
        }
    }

    @Override
    public Hashtag eliminar(Integer idHashtag) throws PersistException{
        EntityManager em = this.conexionBD.crearConexion();
        Hashtag hashtag = this.consultar(idHashtag);
        if (hashtag != null) {
            try {
                em.getTransaction().begin();
                em.remove(hashtag);
                em.getTransaction().commit();
                log.info("Eliminacion Hashtag" + hashtag.getId());
                return null;
            } catch (IllegalStateException e) {
                throw new PersistException("No se pudo registrar en la BD");
            }
        } else {
            return null;
        }
    }

    @Override
    public Hashtag consultarPorTema(String hashtag) throws Exception {
        EntityManager em = this.conexionBD.crearConexion();
        try
        {
            Query query = em.createQuery("SELECT e FROM Hashtag e WHERE e.tema= :nombreHashtag", Hashtag.class);
            query.setParameter("nombreHashtag", hashtag);
            List<Hashtag> hashtags =query.getResultList();
            if(hashtags.isEmpty()){
                throw new Exception("No se encontró un hashtag");
            }
            return hashtags.get(0);
        }
        catch(IllegalStateException e)
        {
            throw new Exception("No pudo realizar la consulta"); 
        }
    }

    public boolean existeHashtag(Hashtag hashtag){
        try{
            this.consultarPorTema(hashtag.getTema());
            return true;
        } catch(Exception ex){
            return false;
        }
    }

}

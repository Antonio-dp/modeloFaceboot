/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import entidades.Notificacion;
import entidades.Usuario;
import enumeradores.MotorEnvio;
import excepciones.NotFoundException;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloNotificacion;
import interfaces.INotificador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DecoradorNotificacion;
import utils.NotificacionDominio;
import utils.NotificacionSMS;
import utils.SimpleJavaMail;

/**
 * Clase que permite realizar el registro y la consulta de notificaciones en BD.
 * Permite hacer un loggeo de las acciones realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ModeloNotificacion implements IModeloNotificacion {
    /**
     * Objeto que permite conectar con BD
     */
    private final IConexionBD conexionBD;
    /**
     * Objeto que permite loggear las acciones CRUD y guardarlas en BD
     */
    private static Logger log = LogManager.getLogger(ModeloNotificacion.class);
    /**
     * Instancia la conexion a base de datos, al valor de su parametro
     * @param conexionBD 
     */
    public ModeloNotificacion(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    /**
     * Permite registrar en la base de datos una notificacion dada por el 
     * parametro, llama al notificador para notificar utilizando el
     * patron decorator
     * @param notificacion notificacion a registrar
     * @return notificacion registrada
     * @throws PersistException Lanza una excepcion si ocurre un error al 
     * registrar una notificacion
     */
    @Override
    public Notificacion registrar(Notificacion notificacion) throws PersistException{
        EntityManager em = this.conexionBD.crearConexion();
        System.out.println(notificacion.getMotorEnvio());
        try {
            INotificador notificador = new NotificacionDominio();
            if (notificacion.getMotorEnvio().equals(MotorEnvio.TwilioSMS)) {
                notificador = new NotificacionSMS(notificador);
                notificador.notificar(notificacion);
            }
            else if (notificacion.getMotorEnvio().equals(MotorEnvio.simpleJavaMail)) {
                notificador = new SimpleJavaMail(notificador);
                notificador.notificar(notificacion);
            } else if(notificacion.getMotorEnvio().equals(MotorEnvio.ambos)) {
                DecoradorNotificacion decorador = new NotificacionSMS(new SimpleJavaMail(new NotificacionDominio()));
                decorador.notificar(notificacion);
            }
            em.getTransaction().begin();
            em.persist(notificacion);
            em.getTransaction().commit();
            log.info("Registro Notificacion" + notificacion.getId());
            return notificacion;
        } catch (IllegalStateException e) {
            throw new PersistException("No se pudo registrar la notificacion en la BD");
        }
    }
    /**
     * Permite consultar de la base de datos la notificacion con el id de la 
     * notificacion dada por el parametro
     * @param idNotificacion id de la notificacion
     * @return Notificacion consultada
     * @throws NotFoundException Lanza una ecepcion si no se pudo realizar la 
     * consulta
     */
    @Override
    public Notificacion consultar(Integer idNotificacion) throws NotFoundException{
        EntityManager em = this.conexionBD.crearConexion();
        try {
            log.info("Consultar Notificacion" + idNotificacion);
            return em.find(Notificacion.class, idNotificacion);
        } catch (IllegalStateException e) {
            throw new NotFoundException("No se pudo consultar la notificacion en la BD");
        }
    }
    /**
     * Permite consultar en la base de datos una lista de notificaciones hechas
     * por un determinado remitente
     * @param remitente usuario remitente
     * @return lista de notificaciones hechas por el usuario remitente
     * @throws NotFoundException Lanza una excepcion si ocurre un error al 
     * consultar las notificaciones del remitente
     */
    @Override
    public List<Notificacion> consultarNotificacionesPorRemitente(Usuario remitente) throws NotFoundException{
        EntityManager em = this.conexionBD.crearConexion();
        try
        {
            Query query = em.createQuery("SELECT e FROM Notificacion e WHERE e.remitente= :remitente", Notificacion.class);
            query.setParameter("remitente", remitente);
            List<Notificacion> notificaciones = query.getResultList();
            log.info("Consultar notificaciones de remitente" + remitente.getId());
            return notificaciones;
        }
        catch(IllegalStateException e){
            throw new NotFoundException("No se pudo consultar la notificacion en la BD");
        }
    }
}

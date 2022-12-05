/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import entidades.Usuario;
import excepciones.FacebootException;
import excepciones.PersistException;
import interfaces.IConexionBD;
import interfaces.IModeloUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import org.apache.logging.log4j.*;

/**
 * Clase que permite realizar el CRUD de usuarios en BD, Permite hacer un loggeo
 * de las acciones CRUD realizadas
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ModeloUsuario implements IModeloUsuario {

    /**
     * Objeto que permite conectar con BD
     */
    private final IConexionBD conexionBD;
    /**
     * Objeto que permite loggear las acciones CRUD y guardarlas en BD
     */
    private static Logger log = LogManager.getLogger(ModeloUsuario.class);
    /**
     * Instancia la conexion a base de datos, al valor de su parametro
     * @param conexionBD 
     */
    public ModeloUsuario(IConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    /**
     * Permite verificar las credenciales (Email y contraseña) de login en BD 
     * para encontrar si el usuario esta registrado.
     * @param usuario usuario a buscar
     * @return usuario registrado
     */
    @Override
    public Usuario login(Usuario usuario) {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            String jpqlQuery = "SELECT c FROM Usuario c";
            TypedQuery<Usuario> consulta = em.createQuery(jpqlQuery, Usuario.class);
            List<Usuario> usuarios = consulta.getResultList();
            for (Usuario u : usuarios) {
                if (u.getEmail().equals(usuario.getEmail()) && u.getPassword().equals(usuario.getPassword())) {
                    log.info("Inicio sesion " + u.getEmail());
                    return u;
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    /**
     * Permite verificar las credenciales (idFacebook) de login por facebook 
     * en BD para encontrar si el usuario esta registrado.
     * @param usuario usuario a buscar
     * @return usuario registrado con facebook
     */
    @Override
    public Usuario loginFacebook(Usuario usuario) {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            String jpqlQuery = "SELECT c FROM Usuario c";
            TypedQuery<Usuario> consulta = em.createQuery(jpqlQuery, Usuario.class);
            List<Usuario> usuarios = consulta.getResultList();
            for (Usuario u : usuarios) {
                if (u.getIdFb() == null) {
                    continue;
                }
                if (u.getIdFb().equals(usuario.getIdFb())) {
                    log.info("Inicio sesion con facebook" + u.getEmail());
                    return u;
                }
            }
            registrar(usuario);
            return usuario;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Permite consultar un usuario en base de datos con el id del usuario dado
     * por el parametro
     * @param idUsuario id del usuario
     * @return usuario consultado
     */
    @Override
    public Usuario consultar(Integer idUsuario) {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            log.info("Consultar usuario " + idUsuario);
            return em.find(Usuario.class, idUsuario);
        } catch (IllegalStateException e) {
            System.err.println("No se pudo consultar el usuario" + idUsuario);
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Permite actualizar un usuario en base de datos con el usuario dado
     * por el parametro
     * @param usuario usuario a actualizar
     * @return usuario actualizado
     */
    @Override
    public Usuario actualizar(Usuario usuario) {
        EntityManager em = this.conexionBD.crearConexion();
        Usuario usuarioActualizar = this.consultar(usuario.getId());
        if (usuarioActualizar != null) {
            try {
                em.getTransaction().begin();
                em.merge(usuario);
                em.getTransaction().commit();
                log.info("Actualizacion usuario " + usuario.getNombre());
                return usuario;
            } catch (IllegalStateException e) {
                System.err.println("No se pudo actualizar el usuario");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
    /**
     * Permite eliminar un usuario en base de datos con el id del usuario dado
     * por el parametro
     * @param idUsuario id del usuario a eliminar
     * @return usuario eliminado
     */
    @Override
    public Usuario eliminar(Integer idUsuario) {
        EntityManager em = this.conexionBD.crearConexion();
        Usuario usuario = this.consultar(idUsuario);
        if (usuario != null) {
            try {
                em.getTransaction().begin();
                em.remove(usuario);
                em.getTransaction().commit();
                log.info("Eliminacion usuario " + usuario.getNombre());
                return null;
            } catch (IllegalStateException e) {
                System.err.println("No se pudo eliminar el usuario");
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }
    /**
     * Permite registrar a un usuario en base de datos con el usuario dado
     * por el parametro
     * @param usuario usuario a registrar
     * @return usuario registrado
     * @throws PersistException Lanza una excepcion si ocurre un error al
     * registrar el usuario
     * @throws FacebootException Lanza una excepcion si el email ya esta 
     * registrado
     */
    @Override
    public Usuario registrar(Usuario usuario) throws PersistException, FacebootException {
        EntityManager em = this.conexionBD.crearConexion(); //Establece conexion con la BD
        try
        {
            if(existeEmail(usuario)) {
                throw new FacebootException("El email colocado ya esta registrado");
            }
            if (validarFechaNac(usuario)) {
                throw new FacebootException("debes ser mayor de edad");
            }
            em.getTransaction().begin(); //Comienza la Transacción
            em.persist(usuario); //Agrega el usuario
            em.getTransaction().commit(); //Termina Transacción
            log.info("Registro usuario " + usuario.getNombre());
            return usuario;
        } catch (IllegalStateException e)
        {
            throw new PersistException("No se pudo registrar el usuario en la BD");
        }
    }
    /**
     * Permite saber si el usuario dado por el parametro existe
     * @param usuario nombre del usuario
     * @return usuario encontrado
     */
    public boolean existeUsuario(Usuario usuario) {
        Usuario usuarioEncontrado = consultarUsuarioPorNombre(usuario.getNombre());
        return usuarioEncontrado != null; //
    }
    /**
     * Permite saber si el email del usuario dado por el parametro existe
     * @param usuario email del usuario
     * @return boolean para saber si se encontro el email o no
     */
    public boolean existeEmail(Usuario usuario) {
        for (Usuario usuarioRegistrado : this.consultarUsuarios()) {
            System.out.println(usuario.getEmail());
            if (usuarioRegistrado.getEmail().equals(usuario.getEmail())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validarFechaNac(Usuario usuario) {
        LocalDate fechaNac = utils.ConversorFechas.toLocalDate(usuario.getFechaNacimiento());
        LocalDate hoy = LocalDate.now();
        long edad = ChronoUnit.YEARS.between(fechaNac, hoy);
        if (edad < 18) {
            return true;
        }
        return false;
    }
    
    public List<Usuario> consultarUsuarios() {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            Query query = em.createQuery("SELECT e FROM Usuario e");
            return query.getResultList();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Permite consultar usuarios por el nombre en la base de datos con el 
     * nombre dado por el parametro
     * @param nombre nombre del usuario
     * @return usuario consultado
     */
    @Override
    public Usuario consultarUsuarioPorNombre(String nombre) {
        EntityManager em = this.conexionBD.crearConexion();
        try {
            Query query = em.createQuery("SELECT e FROM Usuario e WHERE e.nombre= :nombreUsuario", Usuario.class);
            query.setParameter("nombreUsuario", nombre);
            List<Usuario> usuarios = query.getResultList();
            log.info("Consulta usuario por nombre " + nombre);
            return usuarios.isEmpty() ? null : usuarios.get(0);
        } catch (IllegalStateException e) {
            System.err.println("No se pudo consultar el usuario" + nombre);
            e.printStackTrace();
            return null;
        }
    }

}

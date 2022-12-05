/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexiones;

import interfaces.IConexionBD;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Clase que permite realizar la conexion con la base de datos
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ConexionBD implements IConexionBD{
    /**
     * Objeto estatico que permite realizar la conexion con base de datos
     */
    private static ConexionBD conexion;
    /**
     * Objeto de tipo EntityManajer para crear la conexion con base de datos 
     */
    private EntityManager manejadorEntidades;
    /**
     * Constructor con patron singleton que permite crear una sola instancia de la clase
     * @return Instancia unica de la clase
     */
    public static ConexionBD getInstance(){
        if(conexion == null){
            conexion = new ConexionBD();
        }
        return conexion;
    }
    /**
     * Crea la conexion con la base de datos de MySQL
     * @return Regresa el manejador de eventos con la conexion a base de datos ya realizada
     * @throws IllegalStateException Lanza una excepcion en caso de un fallo en la conexion con BD
     */
    @Override
    public EntityManager crearConexion() throws IllegalStateException {
        if(manejadorEntidades == null){
            //Obtiene acceso alemFactory a partir de la persistence unit utilizada
            EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("facebootPU"); 
            // Creamos una em(bd) para realizar operaciones con la bd
            manejadorEntidades = emFactory.createEntityManager();
        }
        return manejadorEntidades;
    }
}

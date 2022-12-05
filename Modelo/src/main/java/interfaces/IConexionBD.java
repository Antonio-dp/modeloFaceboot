/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import jakarta.persistence.EntityManager;

/**
 * Fachada que permite realizar la conexion con la base de datos
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public interface IConexionBD {
    /**
     * Crea la conexion con la base de datos de MySQL
     * @return Regresa el manejador de eventos con la conexion a base de datos ya realizada
     * @throws IllegalStateException Lanza una excepcion en caso de un fallo en la conexion con BD 
     */
    public EntityManager crearConexion() throws IllegalStateException;
}

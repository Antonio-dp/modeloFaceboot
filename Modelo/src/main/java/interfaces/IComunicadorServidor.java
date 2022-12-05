/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Comentario;
import entidades.Publicacion;

/**
 * Interfaz fachada para comunicar al modelo con el server
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public interface IComunicadorServidor {
    /**
     * Permite notificar a la vista cuando se registra una nueva publicacion
     * @param publicacion 
     */
    public void notificarNuevaPublicacion(Publicacion publicacion);
    /**
     * Permite notificar a la vista cuando se registra un nuevo comentario
     * @param comentario 
     */
    public void notificarNuevoComentario(Comentario comentario);
    /**
     * Permite notificar a la vista cuando se elimina una publicacion
     * @param publicacion 
     */
    public void notificarEliminarPublicacion(Publicacion publicacion);
    /**
     * Permite notificar a la vista cuando se elimina un comentario
     * @param comentario 
     */
    public void notificarEliminarComentario(Comentario comentario);
}

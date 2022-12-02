/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Comentario;
import entidades.Publicacion;

/**
 *
 * @author jegav
 */
public interface IComunicadorServidor {
    public void notificarNuevaPublicacion(Publicacion publicacion);
    public void notificarNuevoComentario(Comentario comentario);
    public void notificarEliminarPublicacion(Publicacion publicacion);
    public void notificarEliminarComentario(Comentario comentario);
}

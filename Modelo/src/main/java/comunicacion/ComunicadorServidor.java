/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comunicacion;

import interfaces.IComunicadorServidor;
import conversors.IJsonToObject;
import conversors.JsonToObject;
import entidades.Comentario;
import entidades.Publicacion;
import eventos.Eventos;
import peticiones.PeticionComentario;
import peticiones.PeticionPublicacion;
import principales.Server;
/**
 * Fachada para comunicar al modelo con el server
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class ComunicadorServidor implements IComunicadorServidor{
    /**
     * Conversor para serializar objetos para su envio al servidor
     */
    public IJsonToObject conversor;

    /**
     * Instancia el objeto de tipo IJsonToObject a JsonToObject 
     */
    public ComunicadorServidor() {
        this.conversor = new JsonToObject();
    }
 
    /**
     * Permite notificar a la vista cuando se registra una nueva publicacion
     * @param publicacion publicacion registrada
     */
    @Override
    public void notificarNuevaPublicacion(Publicacion publicacion) {
        PeticionPublicacion respuesta = new PeticionPublicacion(Eventos.registrarPublicacion, 200, publicacion);
        Server.getInstance().notificarTodos(publicacion.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }
    /**
     * Permite notificar a la vista cuando se registra un nuevo comentario
     * @param comentario comentario registrado
     */
    @Override
    public void notificarNuevoComentario(Comentario comentario) {
        PeticionComentario respuesta = new PeticionComentario(Eventos.registrarComentario, 200, comentario);
        Server.getInstance().notificarTodos(comentario.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }
    /**
     * Permite notificar a la vista cuando se elimina una publicacion
     * @param publicacion publicacion eliminada
     */
    @Override
    public void notificarEliminarPublicacion(Publicacion publicacion) {
        PeticionPublicacion respuesta = new PeticionPublicacion(Eventos.eliminarPublicacion, 200, publicacion);
        Server.getInstance().notificarTodos(publicacion.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }

    /**
     * Permite notificar a la vista cuando se elimina un comentario
     * @param comentario comentario eliminado
     */
    @Override
    public void notificarEliminarComentario(Comentario comentario) {
        PeticionComentario respuesta = new PeticionComentario(Eventos.eliminarComentario, 200, comentario);
        Server.getInstance().notificarTodos(comentario.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }
    
}

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
import java.util.List;
import peticiones.PeticionComentario;
import peticiones.PeticionPublicacion;
import principales.Server;
/**
 *
 * @author jegav
 */
public class ComunicadorServidor implements IComunicadorServidor{
    
    public IJsonToObject conversor;

    public ComunicadorServidor() {
        this.conversor = new JsonToObject();
    }
 
    @Override
    public void notificarNuevaPublicacion(Publicacion publicacion) {
        PeticionPublicacion respuesta = new PeticionPublicacion(Eventos.registrarPublicacion, 200, publicacion);
        Server.getInstance().notificarTodos(publicacion.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }

    @Override
    public void notificarNuevoComentario(Comentario comentario) {
        PeticionComentario respuesta = new PeticionComentario(Eventos.registrarComentario, 200, comentario);
        Server.getInstance().notificarTodos(comentario.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }

    @Override
    public void notificarEliminarPublicacion(Publicacion publicacion) {
        PeticionPublicacion respuesta = new PeticionPublicacion(Eventos.eliminarPublicacion, 200, publicacion);
        Server.getInstance().notificarTodos(publicacion.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }

    @Override
    public void notificarEliminarComentario(Comentario comentario) {
        PeticionComentario respuesta = new PeticionComentario(Eventos.eliminarComentario, 200, comentario);
        Server.getInstance().notificarTodos(comentario.getUsuario().getId(), conversor.convertirObjetoString(respuesta));
    }
    
}

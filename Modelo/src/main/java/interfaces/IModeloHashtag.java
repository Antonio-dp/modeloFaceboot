/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Hashtag;
import excepciones.FacebootException;
import excepciones.PersistException;
import java.util.List;

/**
 *
 * @author tonyd
 */
public interface IModeloHashtag {
    public Hashtag registrar(Hashtag hashtag) throws PersistException;
    public List<Hashtag> registrarHashtags(List<Hashtag> hashtags) throws PersistException;
    public Hashtag consultar(Integer idHashtag) throws PersistException;
    public Hashtag consultarPorTema(String hashtag) throws Exception;
    public Hashtag eliminar(Integer idHashtag) throws PersistException;
}

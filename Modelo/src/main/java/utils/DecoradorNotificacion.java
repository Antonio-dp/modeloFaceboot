/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entidades.Notificacion;
import interfaces.INotificador;

/**
 * Clase decoradora que envuelve un objeto notificador
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class DecoradorNotificacion implements INotificador{
    /**
     * Objeto envolvedor
     */
    private INotificador notificador;
    /**
     * Instancia el objeto al valor del parametro
     * @param notificador notificador a utilizar
     */
    public DecoradorNotificacion(INotificador notificador) {
        this.notificador = notificador;
    }
    /**
     * Permite notificar un mensaje dado por el parametro
     * @param notificacion mensaje a notificar
     */
    @Override
    public void notificar(Notificacion notificacion) {
        notificador.notificar(notificacion);
    }
    
}

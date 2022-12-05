/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Notificacion;

/**
 * Interfaz que permite enviar una notificacion
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public interface INotificador {
    /**
     * Permite notificar un mensaje dado por el parametro
     * @param notificacion mensaje a notificar
     */
    public void notificar(Notificacion notificacion);
}

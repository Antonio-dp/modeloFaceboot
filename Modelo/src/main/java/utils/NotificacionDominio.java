/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entidades.Notificacion;
import interfaces.INotificador;

/**
 * Clase concreta de notificador
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class NotificacionDominio implements INotificador {

    /**
     * Permite notificar un mensaje dado por el parametro
     * @param notificacion mensaje a notificar
     */
    @Override
    public void notificar(Notificacion notificacion) {
        System.out.println("Notificando");
    }

}

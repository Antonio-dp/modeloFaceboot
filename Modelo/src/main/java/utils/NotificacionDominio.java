/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import entidades.Notificacion;
import interfaces.INotificador;

/**
 *
 * @author tonyd
 */
public class NotificacionDominio implements INotificador{

    @Override
    public void notificar(Notificacion notificacion) {
        System.out.println("Notificando");
    }

    
}

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
public class DecoradorNotificacion implements INotificador{

    private INotificador notificador;

    public DecoradorNotificacion(INotificador notificador) {
        this.notificador = notificador;
    }
    
    @Override
    public void notificar(Notificacion notificacion) {
        notificador.notificar(notificacion);
    }
    
}

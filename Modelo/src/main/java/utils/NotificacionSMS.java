package utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entidades.Notificacion;
import interfaces.INotificador;
/**
 * Clase hija de DecoradorNotificacion
 * @author Jesus Valencia, Antonio del Pardo, Marco Irineo, Giovanni Garrido
 */
public class NotificacionSMS extends DecoradorNotificacion{
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    /**
     * Variable String con valor del SID del twilio
     */
    public static final String ACCOUNT_SID = "ACc4d0a794220d8944f1e84822f93e754e";
    /**
     * Variable String con el valor del token de twilio
     */
    public static final String AUTH_TOKEN = "d2c19ac5591dee148489628407d36924";
    /**
     * Constructor encapsulado
     * @param notificador objeto con el que se envuelve
     */
    public NotificacionSMS(INotificador notificador) {
        super(notificador);
    }
    /**
     * Permite notificar un mensaje dado por el parametro
     * @param notificacion mensaje a notificar 
     */
    @Override
    public void notificar(Notificacion notificacion){
        super.notificar(notificacion);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+52"+notificacion.getDestinatario().getTelefono()),
                new com.twilio.type.PhoneNumber("+19033205651"),
                notificacion.getContenido())
            .create();

        System.out.println(message.getSid());    
    }
}
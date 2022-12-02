package utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import entidades.Notificacion;
import interfaces.INotificador;

public class NotificacionSMS extends DecoradorNotificacion{
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACc4d0a794220d8944f1e84822f93e754e";
    public static final String AUTH_TOKEN = "b5689b0a2b5754f8d8e88918ce24c516";

    public NotificacionSMS(INotificador notificador) {
        super(notificador);
    }

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
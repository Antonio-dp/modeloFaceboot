/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entidades.Notificacion;
import java.util.Properties;
import javax.mail.Session;
import interfaces.INotificador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author jegav
 */
public class SimpleJavaMail extends DecoradorNotificacion {
    /**
     * Session
     */
    private Session session;
    /**
     * Propiedades
     */
    private Properties properties;
    /**
     * Constructor encapsulado
     * @param notificador objeto con el que se envuelve
     */
    public SimpleJavaMail(INotificador notificador) {
        super(notificador);
        this.properties = new Properties();
        llenarProperties();
        this.session = Session.getDefaultInstance(properties);
    }

    /**
     * Propiedades del notificador
     */
    public void llenarProperties() {
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", "jegavale@gmail.com");
        properties.setProperty("mail.smtp.auth", "true");
    }

    /**
     * Permite notificar un mensaje dado por el parametro
     * @param notificacion mensaje a notificar
     */
    @Override
    public void notificar(Notificacion notificacion) {
        super.notificar(notificacion);
        MimeMessage contenedor = new MimeMessage(session);
        try {
            contenedor.setFrom(new InternetAddress((String) this.properties.get("mail.smtp.user")));
            contenedor.addRecipient(Message.RecipientType.TO, new InternetAddress(notificacion.getDestinatario().getEmail()));
            contenedor.setSubject("Notificación Faceboot");
            contenedor.setText(notificacion.getContenido());
            Transport t = session.getTransport("smtp");
            t.connect((String) this.properties.get("mail.smtp.user"), "iweuucvrydutylkg"); //Para conseguir la contraseñase debe activar la verificación po 2 pasos y Agregar contraseña para Aplicación
            t.sendMessage(contenedor, contenedor.getAllRecipients());
        } catch (Exception ex) {
            Logger.getLogger(SimpleJavaMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

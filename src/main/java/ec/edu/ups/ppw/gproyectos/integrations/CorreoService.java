package ec.edu.ups.ppw.gproyectos.integrations;

import java.util.Properties;

import jakarta.ejb.Stateless;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Stateless
public class CorreoService {

    // CAMBIAR ESTO POR TUS CREDENCIALES REALES SI QUIERES CORREOS DE VERDAD
    private final String REMITENTE = "tu_correo@gmail.com";
    private final String PASSWORD = "tu_contraseña_de_aplicacion"; // No es tu clave normal, es una App Password

    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        System.out.println(">>> INTENTANDO ENVIAR CORREO A: " + destinatario);

        // Configuración SMTP para Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, PASSWORD);
            }
        });

        try {
            // Creación del Mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(REMITENTE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            
            // Usamos setContent con HTML para que se vea bonito
            message.setContent(cuerpo, "text/html; charset=utf-8");

            // Enviar
            Transport.send(message);
            System.out.println(">>> [OK] CORREO ENVIADO EXITOSAMENTE.");

        } catch (Exception e) {
            // Si falla (por ejemplo, sin internet o claves mal), hacemos fallback a Simulación
            System.err.println(">>> [ERROR CORREO] No se pudo enviar real: " + e.getMessage());
            System.out.println("----------------------------------------------------");
            System.out.println("--- SIMULACIÓN DE CORREO ---");
            System.out.println("Para: " + destinatario);
            System.out.println("Asunto: " + asunto);
            System.out.println("Mensaje: " + cuerpo);
            System.out.println("----------------------------------------------------");
        }
    }
}
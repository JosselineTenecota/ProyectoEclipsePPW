package ec.edu.ups.ppw.gproyectos.integrations;

import jakarta.ejb.Stateless;

@Stateless
public class WhatsAppService {

    /**
     * Simula el envío de un mensaje de WhatsApp.
     * En un entorno real, aquí llamarías a la API de Twilio o Meta.
     */
    public void enviarMensaje(String numeroTelefono, String mensaje) {
        // Lógica simulada requerida por la Fase 2
        System.out.println("************************************************");
        System.out.println(">>> [WHATSAPP API SIMULATOR]");
        System.out.println(">>> Enviando a: " + numeroTelefono);
        System.out.println(">>> Payload: " + mensaje);
        System.out.println(">>> Status: SENT (200 OK)");
        System.out.println("************************************************");
    }
}
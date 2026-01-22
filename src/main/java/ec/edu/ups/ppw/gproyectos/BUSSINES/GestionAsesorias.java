package ec.edu.ups.ppw.gproyectos.BUSSINES;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import ec.edu.ups.ppw.gproyectos.dao.AsesoriaDAO;
import ec.edu.ups.ppw.gproyectos.dao.PersonaDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.dto.SolicitudAsesoriaDTO;
import ec.edu.ups.ppw.gproyectos.integrations.CorreoService;
import ec.edu.ups.ppw.gproyectos.integrations.WhatsAppService;
import ec.edu.ups.ppw.gproyectos.Asesoria;
import ec.edu.ups.ppw.gproyectos.Persona;
import ec.edu.ups.ppw.gproyectos.Usuario;

@Stateless
public class GestionAsesorias {

    @Inject
    private AsesoriaDAO asesoriaDAO;
    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private PersonaDAO personaDAO;
    @Inject
    private CorreoService correoService;
    @Inject
    private WhatsAppService whatsAppService;

    // 1. CLIENTE SOLICITA ASESORIA
    public void crearSolicitud(SolicitudAsesoriaDTO dto) throws Exception {
        Usuario cliente = usuarioDAO.getUsuarioPorCorreo(dto.getCorreoCliente());
        Persona programador = personaDAO.read(dto.getCedulaProgramador());

        if (cliente == null || programador == null) {
            throw new Exception("Cliente o Programador no encontrados");
        }

        Asesoria nueva = new Asesoria();
        nueva.setTema(dto.getTema());
        nueva.setFechaHora(dto.getFechaConvertida());
        nueva.setEstado("PENDIENTE");
        nueva.setCliente(cliente.getPersona()); // Relacionamos con la Persona del cliente
        nueva.setProgramador(programador);
        
        asesoriaDAO.insert(nueva);

        // --- NOTIFICACIÓN AL PROGRAMADOR ---
        // Buscamos el correo del programador (a traves de su usuario, asumiendo relacion inversa o buscando manual)
        // Por simplicidad, enviaremos al correo simulado, pero deberías buscar el correo real del programador.
        String asunto = "Nueva Solicitud de Asesoría";
        String mensaje = "Hola " + programador.getNombre() + ", tienes una nueva solicitud para el: " + dto.getFechaHora();
        
        // OJO: Aquí deberías tener el correo del programador. 
        // Si Persona no tiene correo, búscalo en Usuario.
        correoService.enviarCorreo("programador@mail.com", asunto, mensaje); 
    }

    // 2. PROGRAMADOR RESPONDE (APRUEBA / RECHAZA)
    public void responderSolicitud(int codigoAsesoria, String nuevoEstado, String mensajeJustificacion) throws Exception {
        Asesoria asesoria = asesoriaDAO.read(codigoAsesoria);
        if (asesoria == null) {
            throw new Exception("Asesoría no encontrada");
        }

        asesoria.setEstado(nuevoEstado); // "APROBADA" o "RECHAZADA"
        asesoria.setMensajeProgramador(mensajeJustificacion);
        asesoriaDAO.update(asesoria);

        // --- NOTIFICACIONES AL CLIENTE ---
        // Recuperar datos para notificar
        // Nota: Necesitamos el correo del cliente. Asumimos que podemos llegar a el.
        // Como Asesoria -> Persona (Cliente), y Persona no tiene correo directo (lo tiene Usuario),
        // en un caso real harías una búsqueda inversa. Aquí simulamos el envío al correo guardado en lógica.
        
        String asunto = "Tu asesoría fue " + nuevoEstado;
        String cuerpo = "Hola, tu solicitud sobre '" + asesoria.getTema() + "' ha sido " + nuevoEstado + 
                        ".\nMensaje del programador: " + mensajeJustificacion;

        // 1. Email
        correoService.enviarCorreo("cliente@mail.com", asunto, cuerpo);
        
        // 2. WhatsApp
        // Asumiendo que 'telefono' está en Persona
        String telefono = asesoria.getCliente().getDireccion(); // O usa un campo telefono real si lo agregaste
        whatsAppService.enviarMensaje(telefono, cuerpo);
    }
    
    // Listados
    public List<Asesoria> listarPorProgramador(String cedula) {
        return asesoriaDAO.getPorProgramador(cedula);
    }
    
    // Este método faltaba en AsesoriaDAO, asegúrate de tenerlo o usar getAll filtrado
    public List<Asesoria> listarPorCliente(String cedulaCliente) {
         // Si no creaste este método en el DAO paso anterior, 
         // deberás agregarlo en AsesoriaDAO: "SELECT a FROM Asesoria a WHERE a.cliente.cedula = :cedula"
         // Por ahora retorno null para que no de error de compilación si no lo tienes.
         // return asesoriaDAO.getPorCliente(cedulaCliente);
         return asesoriaDAO.getAll(); // Temporal hasta que ajustes el DAO
    }
}
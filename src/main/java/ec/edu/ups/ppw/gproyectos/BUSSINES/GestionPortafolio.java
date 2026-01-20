package ec.edu.ups.ppw.gproyectos.BUSSINES;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import ec.edu.ups.ppw.gproyectos.Asesoria;
import ec.edu.ups.ppw.gproyectos.Proyecto;
import ec.edu.ups.ppw.gproyectos.Usuario;
import ec.edu.ups.ppw.gproyectos.dao.AsesoriaDAO;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;

//@Stateless
/*public class GestionPortafolio {

    @Inject
    private UsuarioDAO daoUsuario;
    @Inject
    private ProyectoDAO daoProyecto;
    @Inject
    private AsesoriaDAO daoAsesoria;

    // --- LOGICA DE USUARIOS ---
    public Usuario login(String correo, String password) {
        // Aquí iría validación de password real. Por ahora simulamos.
        // En un caso real, daoUsuario.read(correo) debería traer el pass para comparar.
        Usuario u = daoUsuario.read(correo);
        if (u != null) {
            // Aquí validaríamos password
            return u;
        }
        return null;
    }

    public void registrarUsuario(Usuario u) throws Exception {
        if (daoUsuario.read(u.getCorreo()) != null) {
            throw new Exception("El correo ya está registrado");
        }
        daoUsuario.insert(u);
    }

    // --- LOGICA DE PROYECTOS ---
    public void publicarProyecto(Proyecto p) {
        daoProyecto.insert(p);
    }
    
    public List<Proyecto> listarProyectos() {
        return daoProyecto.getAll();
    }

    // --- LOGICA DE ASESORIAS Y NOTIFICACIONES (Punto 6) ---
    public void solicitarAsesoria(Asesoria a) {
        a.setEstado("Pendiente");
        daoAsesoria.insert(a);
        
        // Simulación Notificación al Programador
        System.out.println(">>> [EMAIL SIMULADO] Para: " + a.getProgramador().getCorreo());
        System.out.println(">>> Asunto: Nueva Solicitud de Asesoría");
        System.out.println(">>> Mensaje: El cliente " + a.getCliente().getNombre() + " quiere una cita.");
    }

    public void responderAsesoria(int codigoCita, String estado, String respuesta) throws Exception {
        Asesoria a = daoAsesoria.read(codigoCita);
        if (a != null) {
            a.setEstado(estado);
            // Aquí podrías agregar un campo 'respuesta' a tu entidad Asesoria si no lo tiene,
            // o concatenarlo al tema.
            String temaActual = a.getTema(); 
            a.setTema(temaActual + " | RESPUESTA: " + respuesta);
            
            daoAsesoria.update(a);

            // Simulación Notificación al Cliente (Correo y WhatsApp)
            System.out.println("*************************************************");
            System.out.println(">>> [WHATSAPP SIMULADO] Enviando a: " + a.getCliente().getNombre());
            System.out.println(">>> Mensaje: Tu cita fue " + estado.toUpperCase() + ". " + respuesta);
            System.out.println("*************************************************");
        } else {
            throw new Exception("Cita no encontrada");
        }
    }
    
    public List<Asesoria> listarAsesoriasPorUsuario(String cedula) {
        // Aquí deberías implementar el filtro en el DAO, por ahora devolvemos todas para probar
        return daoAsesoria.getAll(); 
    }
}*/

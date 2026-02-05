package ec.edu.ups.ppw.gproyectos.BUSSINES;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.Proyecto;
import ec.edu.ups.ppw.gproyectos.Usuario;
import ec.edu.ups.ppw.gproyectos.Persona;

@Stateless
public class GestionProyectos {

    @Inject
    private ProyectoDAO proyectoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    public void registrarProyecto(Proyecto proyecto, String correo) throws Exception {
        System.out.println("Intentando registrar proyecto para correo: " + correo);
        Usuario u = usuarioDAO.read(correo);
        
        if (u == null || u.getPersona() == null) {
            throw new Exception("Error: No se encontró la Persona vinculada al correo: " + correo);
        }
        
        System.out.println("Persona encontrada: " + u.getPersona().getCedula()); 
        proyecto.setProgramador(u.getPersona());
        
        proyectoDAO.insert(proyecto);
    }

    public void actualizarProyecto(Proyecto proyecto) throws Exception {
        proyectoDAO.update(proyecto);
    }

    public void eliminarProyecto(int codigo) {
        proyectoDAO.delete(codigo);
    }

    public List<Proyecto> listarTodos() {
        return proyectoDAO.getAll();
    }

    public List<Proyecto> listarPorProgramador(String cedula) {
        return proyectoDAO.getProyectosPorUsuario(cedula);
    }
}
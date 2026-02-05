package ec.edu.ups.ppw.gproyectos.BUSSINES;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.Proyecto;
import ec.edu.ups.ppw.gproyectos.Usuario;

@Stateless
public class GestionProyectos {

    @Inject
    private ProyectoDAO proyectoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    public void registrarProyecto(Proyecto proyecto, String cedula) throws Exception {
        // Buscamos al usuario por cédula para obtener la relación Persona
        Usuario u = usuarioDAO.read(cedula);
        
        if (u == null || u.getPersona() == null) {
            throw new Exception("No se encontró el programador vinculado a la cédula: " + cedula);
        }
        
        // Seteamos la relación (la llave foránea en Postgres)
        proyecto.setProgramador(u.getPersona());
        
        // Guardamos el objeto completo con todos sus atributos
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
}
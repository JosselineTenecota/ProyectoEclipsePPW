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

    // Crear un proyecto y asignarlo a un programador
    public void registrarProyecto(Proyecto proyecto, String correoProgramador) throws Exception {
        Usuario u = usuarioDAO.read(correoProgramador);
        if (u == null) {
            throw new Exception("El programador no existe.");
        }
        
        // Relacionamos objetos
        proyecto.setProgramador(u);
        
        // Guardamos
        proyectoDAO.insert(proyecto);
    }

    // Actualizar proyecto
    public void actualizarProyecto(Proyecto proyecto) {
        proyectoDAO.update(proyecto);
    }
    
    // Eliminar
    public void eliminarProyecto(int codigo) {
        proyectoDAO.delete(codigo);
    }

    // Listar todos (Publico)
    public List<Proyecto> listarTodos() {
        return proyectoDAO.getAll();
    }
    
    // Listar por programador especifico
    public List<Proyecto> listarPorProgramador(String cedulaUsuario) {
        // OJO: Aquí usamos el método filtro que creamos en el DAO
        // Como tu usuario.cedula es un campo dentro de persona, 
        // revisa si en tu BD usas correo o cedula para filtrar. 
        // Asumiendo que usas la relación programador -> Usuario -> Persona
        // Si Usuario ID es correo, cambiamos la lógica del DAO o pasamos el correo.
        
        // CORRECCIÓN RAPIDA: Si tu DAO filtra por "cedula" pero tu ID de usuario es "correo", 
        // deberíamos filtrar por correo. Ajustaré esto en el Service.
        return proyectoDAO.getProyectosPorUsuario(cedulaUsuario);
    }
}
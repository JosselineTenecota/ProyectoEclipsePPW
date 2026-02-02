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

    // Crear un proyecto y asignarlo a un programador
    public void registrarProyecto(Proyecto proyecto, String correoProgramador) throws Exception {

        // 1️⃣ Buscamos el usuario
        Usuario u = usuarioDAO.read(correoProgramador);
        if (u == null) {
            throw new Exception("El usuario no existe.");
        }

        // 2️⃣ Obtenemos la persona asociada al usuario
        Persona p = u.getPersona();
        if (p == null) {
            throw new Exception("El usuario no tiene una persona asociada.");
        }

        // 3️⃣ Relacionamos correctamente
        proyecto.setProgramador(p);

        // 4️⃣ Guardamos
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

    // Listar todos
    public List<Proyecto> listarTodos() {
        return proyectoDAO.getAll();
    }

    // Listar por programador
    public List<Proyecto> listarPorProgramador(String correoUsuario) {
        return proyectoDAO.getProyectosPorUsuario(correoUsuario);
    }
}

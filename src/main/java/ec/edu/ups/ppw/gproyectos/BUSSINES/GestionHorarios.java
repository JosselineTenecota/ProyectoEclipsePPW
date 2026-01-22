package ec.edu.ups.ppw.gproyectos.BUSSINES;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import ec.edu.ups.ppw.gproyectos.dao.HorarioDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.Horario;
import ec.edu.ups.ppw.gproyectos.Usuario;

@Stateless
public class GestionHorarios {

    @Inject
    private HorarioDAO horarioDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    public void agregarHorario(Horario horario, String correoProgramador) throws Exception {
        // 1. Buscamos al usuario por su correo
        Usuario u = usuarioDAO.getUsuarioPorCorreo(correoProgramador);
        
        if (u == null) {
            throw new Exception("El usuario no existe");
        }
        
        if (u.getPersona() == null) {
            throw new Exception("El usuario no tiene un perfil (Persona) asociado");
        }

        // 2. Asociamos el horario a la Persona del usuario
        horario.setProgramador(u.getPersona());
        
        // 3. Guardamos
        horarioDAO.insert(horario);
    }

    public void eliminarHorario(int id) {
        horarioDAO.delete(id);
    }

    public List<Horario> listarPorProgramador(String cedula) {
        return horarioDAO.getPorProgramador(cedula);
    }
}

package ec.edu.ups.ppw.gproyectos.BUSSINES;

import ec.edu.ups.ppw.gproyectos.dao.PersonaDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.Persona;
import ec.edu.ups.ppw.gproyectos.Usuario;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class GestionUsuarios {

    @Inject
    private UsuarioDAO usuarioDAO;
    
    @Inject
    private PersonaDAO personaDAO;

    public void registrarUsuario(Usuario usuario, Persona persona) throws Exception {
        // 1. Validar si ya existe
        Usuario u = usuarioDAO.getUsuarioPorCorreo(usuario.getCorreo());
        if (u != null) {
            throw new Exception("El correo ya está registrado.");
        }
        
        // 2. Asociar y guardar
        // Guardamos persona primero (si no tienes CascadeType.ALL configurado)
        personaDAO.insert(persona);
        
        usuario.setPersona(persona);
        usuario.setActivo(true);
        // Aquí deberías encriptar el password: usuario.setPassword(hash(usuario.getPassword()));
        
        usuarioDAO.insert(usuario);
    }
}
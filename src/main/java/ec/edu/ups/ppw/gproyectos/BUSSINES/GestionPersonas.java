package ec.edu.ups.ppw.gproyectos.BUSSINES;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import ec.edu.ups.ppw.gproyectos.dao.PersonaDAO;
import ec.edu.ups.ppw.gproyectos.Persona;

@Stateless
public class GestionPersonas {

    @Inject
    private PersonaDAO personaDAO;

    // Actualizar datos del perfil (Ej: El programador edita su bio)
    public void actualizarPerfil(Persona persona) throws Exception {
        Persona p = personaDAO.read(persona.getCedula());
        if (p != null) {
            // Solo actualizamos campos no sensibles
            p.setNombre(persona.getNombre());
            p.setDireccion(persona.getDireccion());
            p.setDescripcion(persona.getDescripcion());
            p.setFotoUrl(persona.getFotoUrl());
            p.setEspecialidad(persona.getEspecialidad());
            
            personaDAO.update(p);
        } else {
            throw new Exception("La persona no existe.");
        }
    }

    // Para que el Admin vea listado de programadores
    public List<Persona> listarPersonas() {
        return personaDAO.getAll();
    }
    
    // Buscar una persona especifica
    public Persona buscarPersona(String cedula) {
        return personaDAO.read(cedula);
    }
}
package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionUsuarios;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.dto.RegistroDTO;
import ec.edu.ups.ppw.gproyectos.Persona;
import ec.edu.ups.ppw.gproyectos.Usuario;

@Path("usuarios")
public class UsuarioService {

    @Inject
    private GestionUsuarios gestionUsuarios;
    
    @Inject
    private UsuarioDAO usuarioDAO;

    // 1. CREAR USUARIO (Registro)
    @POST
    @Path("/crear")
    @Consumes("application/json")
    @Produces("application/json")
    public Response registrar(RegistroDTO dto) {
        try {
            if (dto.getCorreo() == null || dto.getPassword() == null || dto.getCedula() == null) {
                return Response.status(400).entity("{\"mensaje\": \"Faltan datos obligatorios\"}").build();
            }

            Persona p = new Persona();
            p.setCedula(dto.getCedula());
            p.setNombre(dto.getNombre());
            p.setDireccion("");
            p.setDescripcion("");
            p.setFotoUrl(""); 
            
            Usuario u = new Usuario();
            u.setCorreo(dto.getCorreo());
            u.setPassword(dto.getPassword()); 
            u.setRol(dto.getRol() != null ? dto.getRol() : "CLIENTE");
            u.setActivo(true);

            gestionUsuarios.registrarUsuario(u, p);

            return Response.ok("{\"mensaje\": \"Usuario registrado exitosamente\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al registrar: " + e.getMessage() + "\"}").build();
        }
    }

    // 2. LISTAR TODOS (Para el panel de Admin)
    @GET
    @Produces("application/json")
    public Response listarUsuarios() {
        try {
            List<Usuario> lista = usuarioDAO.getAll();
            // Por seguridad, limpiamos los passwords antes de enviarlos
            for(Usuario u : lista) {
                u.setPassword(null); 
            }
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al listar\"}").build();
        }
    }

    // 3. BUSCAR UNO POR CORREO (Para ver perfil)
    @GET
    @Path("/{correo}")
    @Produces("application/json")
    public Response buscarUsuario(@PathParam("correo") String correo) {
        try {
            Usuario u = usuarioDAO.read(correo);
            if (u != null) {
                u.setPassword(null); 
                return Response.ok(u).build();
            }
            return Response.status(404).entity("{\"mensaje\": \"Usuario no encontrado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al buscar\"}").build();
        }
    }

    // 4. ACTUALIZAR CUENTA (Ej: Cambiar rol o contraseña)
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response actualizarUsuario(Usuario usuario) {
        try {
            Usuario uExistente = usuarioDAO.read(usuario.getCorreo());
            if (uExistente != null) {
                if(usuario.getRol() != null) uExistente.setRol(usuario.getRol());
                if(usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                    uExistente.setPassword(usuario.getPassword());
                }
                
                usuarioDAO.update(uExistente);
                return Response.ok("{\"mensaje\": \"Usuario actualizado\"}").build();
            }
            return Response.status(404).entity("{\"mensaje\": \"Usuario no existe\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al actualizar\"}").build();
        }
    }

    // 5. ELIMINAR (Desactivar) USUARIO
    @DELETE
    @Path("/{correo}")
    @Produces("application/json")
    public Response eliminarUsuario(@PathParam("correo") String correo) {
        try {
            Usuario u = usuarioDAO.read(correo);
            if (u != null) {
                u.setActivo(false);
                usuarioDAO.update(u);
                return Response.ok("{\"mensaje\": \"Usuario desactivado correctamente\"}").build();
            }
            return Response.status(404).entity("{\"mensaje\": \"Usuario no encontrado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al eliminar\"}").build();
        }
    }
}
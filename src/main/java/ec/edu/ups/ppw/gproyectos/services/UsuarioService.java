package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionUsuarios;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.dto.LoginDTO;
import ec.edu.ups.ppw.gproyectos.dto.RegistroDTO;
import ec.edu.ups.ppw.gproyectos.Persona;
import ec.edu.ups.ppw.gproyectos.Usuario;

@Path("usuarios")
public class UsuarioService {

    @Inject
    private GestionUsuarios gestionUsuarios;

    @Inject
    private UsuarioDAO usuarioDAO;

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
            return Response.status(500).entity("{\"mensaje\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        try {
            List<Usuario> lista = usuarioDAO.getAll();
            for (Usuario u : lista) u.setPassword(null);
            return Response.ok(lista).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @GET
    @Path("/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuario(@PathParam("correo") String correo) {
        try {
            Usuario u = usuarioDAO.read(correo);
            if (u != null) {
                u.setPassword(null);
                return Response.ok(u).build();
            }
            return Response.status(404).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }

    @POST
    @Path("/login-social")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginSocial(LoginDTO loginDTO) {
        try {
            String correo = loginDTO.getCorreo();
            System.out.println("Intentando login social para: " + correo);
            
            Usuario u = usuarioDAO.read(correo);
            
            if (u != null) {
                // EVITAR LAZY INITIALIZATION EXCEPTION
                // Rompemos el vínculo con las listas perezosas manualmente
                if (u.getPersona() != null) {
                    u.getPersona().setProyectos(null);
                    u.getPersona().setHorarios(null);
                }
                
                u.setPassword(null); // Seguridad: nunca enviar la clave
                
                return Response.ok(u).build();
            } else {
                return Response.status(404)
                    .entity("{\"mensaje\": \"Usuario no registrado en el sistema local\"}")
                    .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("{\"mensaje\": \"Error interno\"}").build();
        }
    }

    @DELETE
    @Path("/{correo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarUsuario(@PathParam("correo") String correo) {
        try {
            Usuario u = usuarioDAO.read(correo);
            if (u != null) {
                u.setActivo(false);
                usuarioDAO.update(u);
                return Response.ok("{\"mensaje\": \"Desactivado\"}").build();
            }
            return Response.status(404).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }
    }
}
package ec.edu.ups.ppw.gproyectos.services;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.dto.LoginDTO;
import ec.edu.ups.ppw.gproyectos.Usuario;
import ec.edu.ups.ppw.gproyectos.utils.TokenUtils;

@Path("auth")
public class AuthService {

    @Inject
    private UsuarioDAO usuarioDAO;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDto) {
        try {
            if (loginDto.getCorreo() == null || loginDto.getPassword() == null) {
                return Response.status(400).entity("{\"mensaje\": \"Correo y contraseña son requeridos\"}").build();
            }

            Usuario usuario = usuarioDAO.getUsuarioPorCorreo(loginDto.getCorreo());

            if (usuario == null) {
                return Response.status(401).entity("{\"mensaje\": \"Credenciales incorrectas (Usuario no existe)\"}").build();
            }

            if (!usuario.getPassword().equals(loginDto.getPassword())) {
                return Response.status(401).entity("{\"mensaje\": \"Credenciales incorrectas (Password mal)\"}").build();
            }
            
            if (!usuario.isActivo()) {
                return Response.status(403).entity("{\"mensaje\": \"La cuenta está inactiva.\"}").build();
            }

            return construirRespuestaExitosa(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("{\"mensaje\": \"Error interno del servidor\"}").build();
        }
    }

    @POST
    @Path("/login-social")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginSocial(LoginDTO loginDto) {
        try {
            if (loginDto.getCorreo() == null) {
                return Response.status(400).entity("{\"mensaje\": \"Correo es requerido\"}").build();
            }

            Usuario usuario = usuarioDAO.getUsuarioPorCorreo(loginDto.getCorreo());

            if (usuario == null) {
                return Response.status(404).entity("{\"mensaje\": \"Usuario no registrado en el sistema local\"}").build();
            }

            return construirRespuestaExitosa(usuario);

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("{\"mensaje\": \"Error en login social\"}").build();
        }
    }

    private Response construirRespuestaExitosa(Usuario usuario) {
        String cedula = "";
        String nombre = "Usuario";

        // Mantenemos tus mapeos corregidos para evitar errores de compilación
        if (usuario.getPersona() != null) {
            cedula = usuario.getPersona().getCedula(); // Atributo en Java
            nombre = usuario.getPersona().getNombre(); // Atributo en Java
            
            // Mantenemos tu funcionalidad de bloqueo por cédula temporal "SOC-"
            if (cedula != null && cedula.startsWith("SOC-")) {
                return Response.status(403)
                    .entity("{\"mensaje\": \"Debe completar su registro con una cédula real (actualmente: " + cedula + ")\"}")
                    .build();
            }
        } else {
            return Response.status(400).entity("{\"mensaje\": \"Usuario sin datos de persona vinculados\"}").build();
        }

        String token = TokenUtils.generateToken(usuario.getCorreo(), nombre, usuario.getRol());

        // Retornamos el JSON con los campos exactos que Angular busca para llenar los paréntesis ()
        return Response.ok()
                .entity("{"
                        + "\"token\": \"" + token + "\", "
                        + "\"rol\": \"" + usuario.getRol() + "\", "
                        + "\"correo\": \"" + usuario.getCorreo() + "\", "
                        + "\"nombre\": \"" + nombre + "\", "
                        + "\"cedula\": \"" + cedula + "\""
                        + "}")
                .build();
    }
}
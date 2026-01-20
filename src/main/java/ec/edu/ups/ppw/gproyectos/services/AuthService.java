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
            // 1. Validar que vengan datos
            if (loginDto.getCorreo() == null || loginDto.getPassword() == null) {
                return Response.status(400).entity("{\"mensaje\": \"Correo y contraseña son requeridos\"}").build();
            }

            // 2. Buscar usuario en la BD (Usamos el método optimizado que creamos antes)
            Usuario usuario = usuarioDAO.getUsuarioPorCorreo(loginDto.getCorreo());

            if (usuario == null) {
                return Response.status(401).entity("{\"mensaje\": \"Credenciales incorrectas (Usuario no existe)\"}").build();
            }

            // 3. Verificar contraseña 
            // NOTA: Para fase 2 final, aquí deberías usar BCrypt.checkpw(...)
            if (!usuario.getPassword().equals(loginDto.getPassword())) {
                return Response.status(401).entity("{\"mensaje\": \"Credenciales incorrectas (Password mal)\"}").build();
            }
            
            // 4. Verificar si está activo
            if (!usuario.isActivo()) {
                return Response.status(403).entity("{\"mensaje\": \"La cuenta está inactiva.\"}").build();
            }

            // 5. Generar Token
            // Obtenemos el nombre de la Persona asociada (si existe)
            String nombre = "Usuario";
            if (usuario.getPersona() != null) {
                nombre = usuario.getPersona().getNombre();
            }

            String token = TokenUtils.generateToken(usuario.getCorreo(), nombre, usuario.getRol());

            // 6. Responder con JSON
            // Devolvemos el token, el rol y el usuario para que Angular los guarde
            return Response.ok()
                    .entity("{"
                            + "\"token\": \"" + token + "\", "
                            + "\"rol\": \"" + usuario.getRol() + "\", "
                            + "\"usuario\": \"" + usuario.getCorreo() + "\""
                            + "}")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("{\"mensaje\": \"Error interno del servidor\"}").build();
        }
    }
}
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
			Persona p = new Persona();
			p.setCedula(dto.getCedula());
			p.setNombre(dto.getNombre());
			p.setTelefono(dto.getTelefono() != null ? dto.getTelefono() : "0000000000");
			p.setDireccion("");
			p.setDescripcion("");
			p.setFotoUrl("");

			Usuario u = new Usuario();
			u.setCorreo(dto.getCorreo());
			u.setPassword(dto.getPassword());
			u.setRol(dto.getRol() != null ? dto.getRol() : "CLIENTE");
			u.setActivo(true);

			gestionUsuarios.registrarUsuario(u, p);
			return Response.ok("{\"mensaje\": \"Usuario registrado\"}").build();
		} catch (Exception e) {
			return Response.status(500).entity("{\"mensaje\": \"Error: " + e.getMessage() + "\"}").build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizar(Usuario usuario) {
		try {
			Usuario existente = usuarioDAO.read(usuario.getCorreo());
			if (existente != null) {
				existente.setRol(usuario.getRol());
				existente.setActivo(usuario.isActivo());

				if (usuario.getPersona() != null && existente.getPersona() != null) {
					existente.getPersona().setNombre(usuario.getPersona().getNombre());
					existente.getPersona().setTelefono(usuario.getPersona().getTelefono());
				}

				usuarioDAO.update(existente);
				return Response.ok("{\"mensaje\": \"Usuario actualizado\"}").build();
			}
			return Response.status(404).entity("{\"mensaje\": \"No encontrado\"}").build();
		} catch (Exception e) {
			return Response.status(500).entity("{\"mensaje\": \"Error interno\"}").build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarUsuarios() {
		try {
			List<Usuario> lista = usuarioDAO.getAll();
			for (Usuario u : lista) {
				u.setPassword(null);
				limpiarProxies(u);
			}
			return Response.ok(lista).build();
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
	        Usuario u = usuarioDAO.read(loginDTO.getCorreo());
	        if (u == null) {
	            Persona p = new Persona();
	            p.setCedula("SOC-" + (System.currentTimeMillis() / 1000)); 
	            p.setNombre(loginDTO.getNombre());
	            p.setTelefono("0900000000"); // Importante para WhatsApp
	            p.setDireccion("Pendiente"); 

	            u = new Usuario();
	            u.setCorreo(loginDTO.getCorreo());
	            u.setPassword("FIREBASE_AUTH");
	            u.setRol("CLIENTE");
	            u.setActivo(true);
	            u.setPersona(p);

	            gestionUsuarios.registrarUsuario(u, p);
	            u = usuarioDAO.read(u.getCorreo());
	        }
	        limpiarProxies(u);
	        return Response.ok(u).build();
	    } catch (Exception e) {
	        e.printStackTrace(); 
	        return Response.status(500).entity("{\"error\":\"" + e.getMessage() + "\"}").build();
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

	private void limpiarProxies(Usuario u) {
		if (u.getPersona() != null) {
			u.getPersona().setProyectos(null);
			u.getPersona().setHorarios(null);
		}
	}
	
	@GET // Asegúrate de que esta línea esté presente
	@Path("/programadores") // Asegúrate de que tenga el slash inicial
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarProgramadores() {
	    try {
	        // Tu lógica de filtrado aquí
	        List<Usuario> lista = usuarioDAO.getAll();
	        List<Usuario> programadores = lista.stream()
	            .filter(u -> u.getRol() != null && u.getRol().equalsIgnoreCase("PROGRAMADOR"))
	            .toList();

	        for (Usuario u : programadores) {
	            u.setPassword(null);
	            limpiarProxies(u);
	        }
	        return Response.ok(programadores).build();
	    } catch (Exception e) {
	        return Response.status(500).entity(e.getMessage()).build();
	    }
	}
	
	
}
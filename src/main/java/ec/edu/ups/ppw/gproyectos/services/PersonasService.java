package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.Persona;
import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionPersonas;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("persona")
public class PersonasService {
	
	@Inject
	private GestionPersonas gp;
/*	
    //listar
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListaPersonas(){
		List<Persona> listado = gp.getPersonas();
		return Response.ok(listado).build(); 
	}
	
    //leer
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPersona(@PathParam("id") String cedula) {
		try {
			Persona p = gp.getPersona(cedula);
            
            if(p == null) {
                Error error = new Error(404, "No encontrado", "Persona con ID " + cedula + " no encontrada");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.ok(p).build();
            
		} catch (Exception e) {
			Error error = new Error(500, "Error interno", e.getMessage());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();	
		} 
	}
	
    //crear
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createPersona(Persona persona) {
		try {
			gp.crearPersona(persona);
            return Response.ok(persona).build();
		} catch (Exception e) {
			Error error = new Error(500, "Error al crear", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
		}
	}
	
    //actualizar
	@PUT
    @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePersona(@PathParam("id") String cedulaUrl, Persona personaBody) {
        try {
            Persona personaExistente = gp.getPersona(cedulaUrl);
            if (personaExistente == null) {
                Error error = new Error(404, "No encontrado", "No se puede editar, la cédula " + cedulaUrl + " no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            if (!cedulaUrl.equals(personaBody.getCedula())) {
                
                if (gp.getPersona(personaBody.getCedula()) != null) {
                    Error error = new Error(409, "Conflicto", "La nueva cédula " + personaBody.getCedula() + " ya está en uso.");
                    return Response.status(Response.Status.CONFLICT).entity(error).build();
                }

                gp.eliminarPersona(cedulaUrl);

                gp.crearPersona(personaBody);
                
                return Response.ok(personaBody).build();
            } 
            else {
                gp.actualizarPersona(personaBody);
                return Response.ok(personaBody).build();
            }

        } catch (Exception e) {
            Error error = new Error(500, "Error al actualizar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
	}
    
    //eliminar
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePersona(@PathParam("id") String cedula) {
        try {
            Persona p = gp.getPersona(cedula);
            if (p == null) {
                Error error = new Error(404, "No encontrado", "La cédula " + cedula + " no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            
            gp.eliminarPersona(cedula);
            return Response.noContent().build();
            
        } catch (Exception e) {
            Error error = new Error(500, "Error al eliminar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
    */
}
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.Asesoria;
import ec.edu.ups.ppw.gproyectos.dao.AsesoriaDAO;

@Path("asesorias")
public class AsesoriaService {

    @Inject
    private AsesoriaDAO dao;

    //listar
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listar() {
        List<Asesoria> lista = dao.getAll();
        return Response.ok(lista).build();
    }
    
    //leer
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response leer(@PathParam("id") int codigo) {
        try {
            Asesoria a = dao.read(codigo);
            if(a == null) {
                Error error = new Error(404, "No encontrado", "Asesoría " + codigo + " no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.ok(a).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    //crear
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agendar(Asesoria a) {
        try {
            a.setEstado("PENDIENTE"); 
            dao.insert(a);
            return Response.ok(a).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error al agendar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
    
    //actualizar
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(@PathParam("id") int codigo, Asesoria a) {
        try {
            Asesoria existente = dao.read(codigo);
            if (existente == null) {
                Error error = new Error(404, "No encontrado", "Asesoría no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            a.setCodigo(codigo);
            dao.update(a); 
            return Response.ok(a).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error al actualizar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
    
    //eliminar
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") int codigo) {
        try {
            Asesoria a = dao.read(codigo);
            if (a == null) {
                Error error = new Error(404, "No encontrado", "Asesoría no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            dao.delete(codigo);
            return Response.noContent().build();
        } catch (Exception e) {
            Error error = new Error(500, "Error al eliminar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionProyectos;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.Proyecto;

@Path("proyectos")
public class ProyectoService {
    
    @Inject
    private GestionProyectos gestionProyectos;
    
    @Inject
    private ProyectoDAO proyectoDAO;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardar(Proyecto proyecto, @QueryParam("cedula") String cedula) {
        try {
            if (cedula == null || cedula.isEmpty()) {
                return Response.status(400).entity("{\"error\": \"Cédula obligatoria\"}").build();
            }
            gestionProyectos.registrarProyecto(proyecto, cedula);
            return Response.ok(proyecto).build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizar(Proyecto proyecto) {
        try {
            gestionProyectos.actualizarProyecto(proyecto);
            return Response.ok("{\"mensaje\": \"Proyecto actualizado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al actualizar\"}").build();
        }
    }

    @DELETE
    @Path("/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("codigo") int codigo) {
        try {
            gestionProyectos.eliminarProyecto(codigo);
            return Response.ok("{\"mensaje\": \"Proyecto eliminado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al eliminar\"}").build();
        }
    }

    @GET
    @Path("/usuario/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorUsuario(@PathParam("cedula") String cedula) {
        if (cedula == null || cedula.trim().isEmpty() || cedula.equals("null")) {
             return Response.status(400).entity("{\"error\": \"Cédula inválida\"}").build();
        }
        List<Proyecto> lista = proyectoDAO.getProyectosPorUsuario(cedula);
        return Response.ok(lista != null ? lista : "[]").build();
    }
}
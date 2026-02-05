package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionProyectos;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.Proyecto;

@Path("proyectos") // Esta es la ruta base: /api/proyectos
public class ProyectoService {

    @Inject
    private GestionProyectos gestionProyectos;
    
    @Inject
    private ProyectoDAO proyectoDAO;

    // 1. CREAR PROYECTO
    // CORRECCIÓN: Se elimina el @Path("proyectos") interno para que la ruta sea simplemente /proyectos
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardar(Proyecto proyecto, @QueryParam("correo") String correo) {
        try {
            // Este método llama a gestionProyectos que ahora tiene el @JoinColumn(name="programador_id")
            gestionProyectos.registrarProyecto(proyecto, correo);
            return Response.ok(proyecto).build();
        } catch (Exception e) {
            // Si el programador_id sigue llegando null, este error 500 saldrá aquí
            return Response.status(500).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }

    // 2. ACTUALIZAR PROYECTO
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

    // 3. ELIMINAR PROYECTO
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

    // 4. LISTAR TODOS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Proyecto> listarTodos() {
        return gestionProyectos.listarTodos();
    }

    // 5. LISTAR POR PROGRAMADOR
    @GET
    @Path("/usuario/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Proyecto> listarPorUsuario(@PathParam("cedula") String cedula) {
        return proyectoDAO.getProyectosPorUsuario(cedula);
    }
}
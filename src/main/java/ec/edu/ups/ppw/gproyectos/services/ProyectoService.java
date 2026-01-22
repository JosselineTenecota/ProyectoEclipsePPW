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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionProyectos;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.Proyecto;

@Path("proyectos")
public class ProyectoService {

    @Inject
    private GestionProyectos gestionProyectos;
    
    @Inject
    private ProyectoDAO proyectoDAO; // Para lecturas directas

    // 1. CREAR PROYECTO
    // Ejemplo de uso: POST /proyectos?correo=juan@mail.com
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response crear(Proyecto proyecto, @QueryParam("correo") String correoProgramador) {
        try {
            if(correoProgramador == null) {
                return Response.status(400).entity("{\"mensaje\": \"Falta indicar el correo del programador\"}").build();
            }
            
            gestionProyectos.registrarProyecto(proyecto, correoProgramador);
            return Response.ok("{\"mensaje\": \"Proyecto creado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    // 2. ACTUALIZAR PROYECTO
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
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
    @Produces("application/json")
    public Response eliminar(@PathParam("codigo") int codigo) {
        try {
            gestionProyectos.eliminarProyecto(codigo);
            return Response.ok("{\"mensaje\": \"Proyecto eliminado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al eliminar\"}").build();
        }
    }

    // 4. LISTAR TODOS (Para la vista pública "Explorar")
    @GET
    @Produces("application/json")
    public List<Proyecto> listarTodos() {
        return gestionProyectos.listarTodos();
    }

    // 5. LISTAR POR PROGRAMADOR (Para el panel del programador)
    // Ejemplo: GET /proyectos/usuario/100200300
    // OJO: Aquí pasamos la Cédula (o ID de Persona) porque así lo definimos en el DAO anteriormente
    @GET
    @Path("/usuario/{cedula}")
    @Produces("application/json")
    public List<Proyecto> listarPorUsuario(@PathParam("cedula") String cedula) {
        return proyectoDAO.getProyectosPorUsuario(cedula);
    }
}
package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionHorarios;
import ec.edu.ups.ppw.gproyectos.Horario;

@Path("horarios")
public class HorarioService {

    @Inject
    private GestionHorarios gestionHorarios;

    // Crear Horario
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response crear(Horario horario, @QueryParam("correo") String correo) {
        try {
            gestionHorarios.agregarHorario(horario, correo);
            return Response.ok("{\"mensaje\": \"Horario agregado correctamente\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    // Listar Horarios de un Programador
    @GET
    @Path("/{cedula}")
    @Produces("application/json")
    public List<Horario> listar(@PathParam("cedula") String cedula) {
        return gestionHorarios.listarPorProgramador(cedula);
    }

    // Eliminar Horario
    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    public Response eliminar(@PathParam("id") int id) {
        try {
            gestionHorarios.eliminarHorario(id);
            return Response.ok("{\"mensaje\": \"Horario eliminado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error al eliminar\"}").build();
        }
    }
}
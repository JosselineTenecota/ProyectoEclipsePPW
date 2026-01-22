package ec.edu.ups.ppw.gproyectos.services;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.BUSSINES.GestionAsesorias;
import ec.edu.ups.ppw.gproyectos.dto.SolicitudAsesoriaDTO;
import ec.edu.ups.ppw.gproyectos.Asesoria;

@Path("asesorias")
public class AsesoriaService {

    @Inject
    private GestionAsesorias gestionAsesorias;

    // 1. CREAR SOLICITUD
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response solicitar(SolicitudAsesoriaDTO dto) {
        try {
            gestionAsesorias.crearSolicitud(dto);
            return Response.ok("{\"mensaje\": \"Solicitud enviada y notificada al programador\"}").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity("{\"mensaje\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    // 2. RESPONDER SOLICITUD (Aprobar/Rechazar)
    // Url: PUT /asesorias/responder/5?estado=APROBADA&mensaje=TodoOk
    @PUT
    @Path("/responder/{codigo}")
    @Produces("application/json")
    public Response responder(@PathParam("codigo") int codigo, 
                              @QueryParam("estado") String estado,
                              @QueryParam("mensaje") String mensaje) {
        try {
            gestionAsesorias.responderSolicitud(codigo, estado, mensaje);
            return Response.ok("{\"mensaje\": \"Asesoría " + estado + " y cliente notificado\"}").build();
        } catch (Exception e) {
            return Response.status(500).entity("{\"mensaje\": \"Error: " + e.getMessage() + "\"}").build();
        }
    }

    // 3. LISTAR POR PROGRAMADOR
    @GET
    @Path("/programador/{cedula}")
    @Produces("application/json")
    public List<Asesoria> listarPorProgramador(@PathParam("cedula") String cedula) {
        return gestionAsesorias.listarPorProgramador(cedula);
    }
}
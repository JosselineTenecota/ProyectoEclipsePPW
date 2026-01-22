package ec.edu.ups.ppw.gproyectos.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.dao.AsesoriaDAO;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.Asesoria;

@Path("reportes")
public class ReporteService {

    @Inject
    private AsesoriaDAO asesoriaDAO;
    @Inject
    private UsuarioDAO usuarioDAO;
    @Inject
    private ProyectoDAO proyectoDAO;

    /**
     * Endpoint para el Dashboard del Administrador.
     * Devuelve un JSON con conteos totales.
     * GET /reportes/admin-dashboard
     */
    @GET
    @Path("/admin-dashboard")
    @Produces("application/json")
    public Response getDashboardAdmin() {
        try {
            // Contamos los datos usando el tamaño de las listas
            // (Para optimizar en producción, se deberían hacer consultas COUNT en el DAO)
            int totalUsuarios = usuarioDAO.getAll().size();
            int totalProyectos = proyectoDAO.getAll().size();
            List<Asesoria> todasAsesorias = asesoriaDAO.getAll();
            
            int totalAsesorias = todasAsesorias.size();
            long aprobadas = todasAsesorias.stream().filter(a -> "APROBADA".equals(a.getEstado())).count();
            long pendientes = todasAsesorias.stream().filter(a -> "PENDIENTE".equals(a.getEstado())).count();
            long rechazadas = todasAsesorias.stream().filter(a -> "RECHAZADA".equals(a.getEstado())).count();

            // Construimos un Mapa para devolver el JSON limpio
            Map<String, Object> dashboard = new HashMap<>();
            dashboard.put("totalUsuarios", totalUsuarios);
            dashboard.put("totalProyectos", totalProyectos);
            dashboard.put("totalAsesorias", totalAsesorias);
            
            Map<String, Long> estados = new HashMap<>();
            estados.put("aprobadas", aprobadas);
            estados.put("pendientes", pendientes);
            estados.put("rechazadas", rechazadas);
            
            dashboard.put("asesoriasPorEstado", estados);

            return Response.ok(dashboard).build();

        } catch (Exception e) {
            return Response.status(500).entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        }
    }
}
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ec.edu.ups.ppw.gproyectos.Usuario;
import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;

@Path("usuarios")
public class UsuarioService {

    @Inject
    private UsuarioDAO dao;

    //listar
    @GET
    @Produces("application/json")
    public Response listar() {
        List<Usuario> lista = dao.getAll();
        return Response.ok(lista).build();
    }
    
    // leer
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response leer(@PathParam("id") String cedula) {
        try {
            Usuario u = dao.read(cedula);
            if (u == null) {
                Error error = new Error(404, "No encontrado", "Usuario con cédula " + cedula + " no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            return Response.ok(u).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error interno", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
    /*
    
    //buscar
    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@QueryParam("cedula") String cedula) {
        return leer(cedula); 
    }
*/
    //crear
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crear(Usuario usuario) {
        try {
            if (dao.read(usuario.getCedula()) != null) {
                Error error = new Error(409, "Conflicto", "La cédula " + usuario.getCedula() + " ya existe.");
                return Response.status(Response.Status.CONFLICT).entity(error).build();
            }
            dao.insert(usuario);
            return Response.ok(usuario).build();
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
    public Response actualizar(@PathParam("id") String cedulaUrl, Usuario usuarioBody) {
        try {
            Usuario usuarioExistente = dao.read(cedulaUrl);
            if (usuarioExistente == null) {
                Error error = new Error(404, "No encontrado", "No se puede editar, el usuario " + cedulaUrl + " no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }

            if (!cedulaUrl.equals(usuarioBody.getCedula())) {
                if (dao.read(usuarioBody.getCedula()) != null) {
                    Error error = new Error(409, "Conflicto", "La nueva cédula ya está en uso.");
                    return Response.status(Response.Status.CONFLICT).entity(error).build();
                }
                dao.delete(cedulaUrl);
                dao.insert(usuarioBody);
            } else {
                dao.update(usuarioBody);
            }
            return Response.ok(usuarioBody).build();
        } catch (Exception e) {
            Error error = new Error(500, "Error al actualizar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }

    //eliminar
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminar(@PathParam("id") String cedula) {
        try {
            Usuario u = dao.read(cedula);
            if (u == null) {
                Error error = new Error(404, "No encontrado", "Usuario no existe.");
                return Response.status(Response.Status.NOT_FOUND).entity(error).build();
            }
            dao.delete(cedula);
            return Response.noContent().build();
        } catch (Exception e) {
            Error error = new Error(500, "Error al eliminar", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
        }
    }
}
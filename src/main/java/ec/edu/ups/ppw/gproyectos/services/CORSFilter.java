package ec.edu.ups.ppw.gproyectos.services;

import java.io.IOException;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CORSFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            request.abortWith(Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build());
        }
    }

    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
        // Solo agregamos si no existen para evitar duplicados que rompan el navegador
        if (!response.getHeaders().containsKey("Access-Control-Allow-Origin")) {
            response.getHeaders().add("Access-Control-Allow-Origin", "*");
        }
        if (!response.getHeaders().containsKey("Access-Control-Allow-Headers")) {
            response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        }
        if (!response.getHeaders().containsKey("Access-Control-Allow-Methods")) {
            response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        }
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}
package ec.edu.ups.ppw.gproyectos.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
    
    // Clave lo suficientemente larga para HS256
    private static final String SECRET_KEY = "EstaEsUnaClaveSecretaSuperSeguraParaTuProyectoFase2";
    private static final long TIEMPO_EXPIRACION = 3600000; // 1 hora

    // Convertimos la clave String a objeto Key una sola vez
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    /**
     * Genera el token cuando el usuario hace Login
     */
    public static String generateToken(String correo, String nombre, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nombre", nombre);
        claims.put("rol", rol);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correo) // Usamos el CORREO como identificador principal
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TIEMPO_EXPIRACION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida si el token que envía Angular es real y no ha expirado
     */
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrae el correo (Subject) del token
     */
    public static String getCorreoFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Extrae el rol para saber si tiene permisos de Admin/Programador
     */
    public static String getRolFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();
            return (String) claims.get("rol");
        } catch (Exception e) {
            return null;
        }
    }
}
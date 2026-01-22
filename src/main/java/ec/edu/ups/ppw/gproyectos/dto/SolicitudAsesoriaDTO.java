package ec.edu.ups.ppw.gproyectos.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SolicitudAsesoriaDTO implements Serializable {
    
    private String tema;
    private String fechaHora; // Formato esperado: "yyyy-MM-ddTHH:mm:ss" (Ej: 2023-11-20T10:00:00)
    private String correoCliente;
    private String cedulaProgramador; // O el identificador que uses para el programador
    
    // Getters y Setters
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }
    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }
    public String getCedulaProgramador() { return cedulaProgramador; }
    public void setCedulaProgramador(String cedulaProgramador) { this.cedulaProgramador = cedulaProgramador; }
    
    // Helper para convertir el String a fecha real
    public LocalDateTime getFechaConvertida() {
        return LocalDateTime.parse(this.fechaHora, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
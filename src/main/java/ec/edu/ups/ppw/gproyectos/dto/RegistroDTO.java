package ec.edu.ups.ppw.gproyectos.dto;

import java.io.Serializable;

public class RegistroDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    // Datos de la Persona
    private String cedula;
    private String nombre;
    private String apellido; 
    private String telefono; // <--- AGREGADO PARA WHATSAPP

    // Datos del Usuario (Cuenta)
    private String correo;
    private String password;
    private String rol; 

    // Constructor vacío obligatorio para Jackson
    public RegistroDTO() {
    }

    // Getters y Setters
    public String getCedula() { 
        return cedula; 
    }
    public void setCedula(String cedula) { 
        this.cedula = cedula; 
    }
    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    public String getApellido() { 
        return apellido; 
    }
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }
    public String getTelefono() {
        return telefono; // Asegúrate de devolver la variable real
    }
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    public String getCorreo() { 
        return correo; 
    }
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }
    public String getPassword() { 
        return password; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    public String getRol() { 
        return rol; 
    }
    public void setRol(String rol) { 
        this.rol = rol; 
    }
}
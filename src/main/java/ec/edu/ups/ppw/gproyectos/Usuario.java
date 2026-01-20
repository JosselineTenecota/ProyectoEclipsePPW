package ec.edu.ups.ppw.gproyectos; 

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usu_correo") // El correo es único y sirve como ID de acceso
    private String correo;

    @Column(name = "usu_password")
    private String password;

    @Column(name = "usu_rol")
    private String rol; // "ADMIN", "PROGRAMADOR", "CLIENTE"

    @Column(name = "usu_activo")
    private boolean activo = true;

    // Relación Uno a Uno con Persona (Perfil)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "per_cedula_fk")
    private Persona persona;

    // Getters y Setters
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
    public boolean isActivo() { 
    	return activo; 
    }
    public void setActivo(boolean activo) { 
    	this.activo = activo; 
    }
    public Persona getPersona() { 
    	return persona; 
    }
    public void setPersona(Persona persona) { 
    	this.persona = persona; 
    }
}
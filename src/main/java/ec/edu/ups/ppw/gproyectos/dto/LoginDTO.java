package ec.edu.ups.ppw.gproyectos.dto;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    private String correo;
    private String password;

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
}
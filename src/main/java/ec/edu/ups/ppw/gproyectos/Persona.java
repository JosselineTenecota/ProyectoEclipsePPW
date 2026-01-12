package ec.edu.ups.ppw.gproyectos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PERSONA")
public class Persona {
	
	@Id
	@Column(name = "per_cedula", length = 10)
	private String cedula;
	
	@Column(name = "per_nombre", length = 60)
	private String nombre;
	
	@Column(name = "per_direccion")
	private String direccion;
	
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
} 

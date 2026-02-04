package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.json.bind.annotation.JsonbTransient;

@Entity
@Table(name = "TBL_PERSONA")
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "per_cedula", length = 10)
    private String cedula;

    @Column(name = "per_nombre")
    private String nombre;

    @Column(name = "per_direccion")
    private String direccion;

    @Column(name = "per_descripcion", length = 500)
    private String descripcion;

    @Column(name = "per_foto")
    private String fotoUrl;

    @Column(name = "per_especialidad")
    private String especialidad;

    @OneToMany(mappedBy = "programador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonbTransient
    private List<Proyecto> proyectos;

    @OneToMany(mappedBy = "programador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonbTransient
    private List<Horario> horarios;

    // ===== GETTERS & SETTERS ESTÁNDAR =====

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    // ===== TRUCO: CAMBIO DE NOMBRE EN GETTERS PARA EVITAR EL ERROR 400 =====
    // Al renombrar 'getProyectos' a 'fetchProyectos', Jackson lo ignora en el JSON
    
    public List<Proyecto> fetchProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public List<Horario> fetchHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }
}
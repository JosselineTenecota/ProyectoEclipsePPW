package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_USUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usu_cedula", length = 10)
    private String cedula;

    @Column(name = "usu_nombre")
    private String nombre;

    @Column(name = "usu_correo")
    private String correo;
    
    @Column(name = "usu_rol") 
    private String rol; // "Admin", "Programador", "Cliente"

    // --- CAMPOS ESPECÍFICOS PARA PROGRAMADOR ---
    @Column(name = "usu_horario_inicio")
    private String horaInicio; // Ej: "08:00"
    
    @Column(name = "usu_horario_fin")
    private String horaFin;    // Ej: "16:00"
    
    @Column(name = "usu_especialidad")
    private String especialidad;

    // --- ASOCIACIÓN 1: Un Programador tiene muchos Proyectos ---
    @OneToMany(mappedBy = "programador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Proyecto> proyectos;

    // Getters y Setters
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public List<Proyecto> getProyectos() { return proyectos; }
    public void setProyectos(List<Proyecto> proyectos) { this.proyectos = proyectos; }
    
    // Método helper para agregar proyectos fácilmente
    public void addProyecto(Proyecto p) {
        if(this.proyectos == null) {
            this.proyectos = new ArrayList<>();
        }
        this.proyectos.add(p);
        p.setProgramador(this); // Vinculación bidireccional
    }
}
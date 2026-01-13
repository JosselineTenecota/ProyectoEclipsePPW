package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_ASESORIA")
public class Asesoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ase_codigo")
    private int codigo;

    @Column(name = "ase_tema")
    private String tema; 
    
    @Column(name = "ase_fecha")
    private String fecha; 
    
    @Column(name = "ase_estado")
    private String estado; 

    @Column(name = "ase_mensaje_prog")
    private String mensajeProgramador; 

    @ManyToOne
    @JoinColumn(name = "ase_cliente_fk")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "ase_programador_fk")
    private Usuario programador;

    
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMensajeProgramador() { return mensajeProgramador; }
    public void setMensajeProgramador(String mensajeProgramador) { this.mensajeProgramador = mensajeProgramador; }

    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }

    public Usuario getProgramador() { return programador; }
    public void setProgramador(Usuario programador) { this.programador = programador; }
}
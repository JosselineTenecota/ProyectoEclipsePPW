package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_ASESORIA")
public class Asesoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ase_codigo")
    private int codigo;

    @Column(name = "ase_tema")
    private String tema;
    
    @Column(name = "ase_fecha")
    private String fecha;

    // --- ASOCIACIÓN 1: El Cliente que solicita ---
    @ManyToOne
    @JoinColumn(name = "ase_cliente_fk")
    private Usuario cliente;

    // --- ASOCIACIÓN 2: El Programador que atiende ---
    @ManyToOne
    @JoinColumn(name = "ase_programador_fk")
    private Usuario programador;

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public Usuario getCliente() { return cliente; }
    public void setCliente(Usuario cliente) { this.cliente = cliente; }
    public Usuario getProgramador() { return programador; }
    public void setProgramador(Usuario programador) { this.programador = programador; }
}
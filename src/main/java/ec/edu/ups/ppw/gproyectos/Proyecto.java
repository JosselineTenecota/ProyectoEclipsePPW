package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TBL_PROYECTO")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "pro_codigo")
    private int codigo;

    @Column(name = "pro_titulo")
    private String titulo;
    
    @Column(name = "pro_tipo")
    private String tipo; // Academico/Laboral

    // --- ASOCIACIÓN: Muchos Proyectos pertenecen a Un Programador ---
    @ManyToOne
    @JoinColumn(name = "pro_usuario_fk") // Llave foránea
    private Usuario programador;

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Usuario getProgramador() { return programador; }
    public void setProgramador(Usuario programador) { this.programador = programador; }
}
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
@Table(name = "TBL_PROYECTO")
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "pro_codigo")
    private int codigo;

    @Column(name = "pro_titulo")
    private String titulo;

    @Column(name = "pro_descripcion")
    private String descripcion; 
    
    @Column(name = "pro_tipo")
    private String tipo; 
    
    @Column(name = "pro_participacion")
    private String participacion; 

    @Column(name = "pro_tecnologias")
    private String tecnologias; 

    @Column(name = "pro_url_repo")
    private String urlRepo; 

    @Column(name = "pro_url_deploy")
    private String urlDeploy; 

    @ManyToOne
    @JoinColumn(name = "pro_usuario_fk")
    private Usuario programador;

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public String getParticipacion() { return participacion; }
    public void setParticipacion(String participacion) { this.participacion = participacion; }
    
    public String getTecnologias() { return tecnologias; }
    public void setTecnologias(String tecnologias) { this.tecnologias = tecnologias; }
    
    public String getUrlRepo() { return urlRepo; }
    public void setUrlRepo(String urlRepo) { this.urlRepo = urlRepo; }
    
    public String getUrlDeploy() { return urlDeploy; }
    public void setUrlDeploy(String urlDeploy) { this.urlDeploy = urlDeploy; }
    
    public Usuario getProgramador() { return programador; }
    public void setProgramador(Usuario programador) { this.programador = programador; }
}
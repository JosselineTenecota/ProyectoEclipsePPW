package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import java.time.LocalDateTime; // Importante para Fase 2
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_ASESORIA")
public class Asesoria implements Serializable {

    private static final long serialVersionUID = 1L;
//Verificacion de asesorias
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ase_codigo")
    private int codigo;

    @Column(name = "ase_tema")
    private String tema;

    @Column(name = "ase_fecha_hora")
    private LocalDateTime fechaHora;

    @Column(name = "ase_estado")
    private String estado; //pendiente, probada, rechazada

    @Column(name = "ase_mensaje_prog")
    private String mensajeProgramador;

    @ManyToOne
    @JoinColumn(name = "per_cliente_fk") //relacion con persona
    private Persona cliente;

    @ManyToOne
    @JoinColumn(name = "per_programador_fk")
    private Persona programador;

    public int getCodigo() { 
    	return codigo; 
    }
    public void setCodigo(int codigo) { 
    	this.codigo = codigo; 
    }
    public String getTema() { 
    	return tema; 
    }
    public void setTema(String tema) { 
    	this.tema = tema; 
    }
    public LocalDateTime getFechaHora() { 
    	return fechaHora; 
    }
    public void setFechaHora(LocalDateTime fechaHora) { 
    	this.fechaHora = fechaHora; 
    }
    public String getEstado() { 
    	return estado; 
    }
    public void setEstado(String estado) { 
    	this.estado = estado; 
    }
    public String getMensajeProgramador() { 
    	return mensajeProgramador; 
    }
    public void setMensajeProgramador(String mensajeProgramador) { 
    	this.mensajeProgramador = mensajeProgramador; 
    }
    public Persona getCliente() { 
    	return cliente; 
    }
    public void setCliente(Persona cliente) { 
    	this.cliente = cliente; 
    }
    public Persona getProgramador() { 
    	return programador; 
    }
    public void setProgramador(Persona programador) { 
    	this.programador = programador; 
    	
    }
}
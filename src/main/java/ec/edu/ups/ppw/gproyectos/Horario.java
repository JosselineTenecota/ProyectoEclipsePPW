package ec.edu.ups.ppw.gproyectos;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "TBL_HORARIO")
public class Horario implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hor_id")
    private int id;

    @Column(name = "hor_dia_semana")
    private String diaSemana;

    @Column(name = "hor_hora_inicio")
    private String horaInicio;

    @Column(name = "hor_hora_fin")
    private String horaFin;

    @ManyToOne
    @JoinColumn(name = "per_programador_fk")
    private Persona programador;

    public int getId() { 
    	return id; 
    }
    public void setId(int id) { 
    	this.id = id;
    }
    public String getDiaSemana() { 
    	return diaSemana; 
    }
    public void setDiaSemana(String diaSemana) { 
    	this.diaSemana = diaSemana; 
    }
    public String getHoraInicio() { 
    	return horaInicio; 
    }
    public void setHoraInicio(String horaInicio) { 
    	this.horaInicio = horaInicio; 
    }
    public String getHoraFin() { 
    	return horaFin; 
    }
    public void setHoraFin(String horaFin) { 
    	this.horaFin = horaFin; 
    }
    public Persona getProgramador() { 
    	return programador; 
    }
    public void setProgramador(Persona programador) { 
    	this.programador = programador; 
    }
}

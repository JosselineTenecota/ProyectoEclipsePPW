package ec.edu.ups.ppw.gproyectos.BUSSINES;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import ec.edu.ups.ppw.gproyectos.Usuario;
import ec.edu.ups.ppw.gproyectos.Proyecto;
import ec.edu.ups.ppw.gproyectos.Asesoria;

import ec.edu.ups.ppw.gproyectos.dao.UsuarioDAO;
import ec.edu.ups.ppw.gproyectos.dao.ProyectoDAO;
import ec.edu.ups.ppw.gproyectos.dao.AsesoriaDAO;

@Singleton
@Startup
public class Demo {

    @Inject
    private UsuarioDAO daoUsuario;
    @Inject
    private ProyectoDAO daoProyecto;
    @Inject
    private AsesoriaDAO daoAsesoria;

    @PostConstruct
    public void init() {
        System.out.println("========== INICIANDO CARGA DE DATOS ==========");

        //Crear programador
        Usuario programador = new Usuario();
        programador.setCedula("0105050505"); 
        programador.setNombre("Erick Salinas");
        programador.setCorreo("erick@code.com");
        programador.setRol("Programador");
        programador.setHoraInicio("08:00");
        programador.setHoraFin("14:00");
        programador.setEspecialidad("Backend Java");
        
        daoUsuario.insert(programador);

        //Crear cliente
        Usuario cliente = new Usuario();
        cliente.setCedula("0102030405");
        cliente.setNombre("Josseline Tenecota");
        cliente.setRol("Cliente");
        
        daoUsuario.insert(cliente);

        //Asignar proyecto
        Proyecto proy = new Proyecto();
        // proy.setCodigo(1);
        proy.setTitulo("Sistema de Portafolio");
        proy.setTipo("Academico");
        proy.setProgramador(programador);
        
        proy.setDescripcion("Sistema de gestión de proyectos");
        proy.setParticipacion("Backend");
        proy.setTecnologias("Java, Angular");
        
        daoProyecto.insert(proy); 

        //Crear asesoria
        Asesoria cita = new Asesoria();
        // cita.setCodigo(100); 
        cita.setTema("Revisión de Avance");
        cita.setFecha("2026-01-20");
        cita.setCliente(cliente);
        cita.setProgramador(programador);
        cita.setEstado("PENDIENTE");
        
        daoAsesoria.insert(cita);
        
        System.out.println("========== CARGA FINALIZADA CON ÉXITO ==========");
    }
}
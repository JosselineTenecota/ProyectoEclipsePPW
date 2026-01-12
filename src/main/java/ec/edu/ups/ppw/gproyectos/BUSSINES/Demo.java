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

        // 1. CREAR UN PROGRAMADOR (Con Horarios)
        Usuario programador = new Usuario();
        programador.setCedula("0105050505");
        programador.setNombre("Erick Salinas");
        programador.setCorreo("erick@code.com");
        programador.setRol("Programador");
        
        // Aquí registramos la disponibilidad (Tu requerimiento principal)
        programador.setHoraInicio("08:00");
        programador.setHoraFin("14:00");
        programador.setEspecialidad("Backend Java");
        
        daoUsuario.insert(programador);
        System.out.println("✅ Programador insertado con horario: " + programador.getHoraInicio() + " - " + programador.getHoraFin());

        // 2. CREAR UN CLIENTE
        Usuario cliente = new Usuario();
        cliente.setCedula("0102030405");
        cliente.setNombre("Josseline Tenecota");
        cliente.setRol("Cliente");
        // Cliente no necesita horarios, se quedan null
        
        daoUsuario.insert(cliente);
        System.out.println("✅ Cliente insertado.");

        // 3. ASIGNAR UN PROYECTO AL PROGRAMADOR
        Proyecto proy = new Proyecto();
        proy.setCodigo(1);
        proy.setTitulo("Sistema de Portafolio");
        proy.setTipo("Academico");
        
        // ASOCIACIÓN: Este proyecto es de Erick
        proy.setProgramador(programador);
        
        daoProyecto.insert(proy);
        System.out.println("✅ Proyecto asociado al programador.");

        // 4. CREAR UNA ASESORÍA
        Asesoria cita = new Asesoria();
        cita.setCodigo(100);
        cita.setTema("Revisión de Avance");
        cita.setFecha("2026-01-20");
        
        // ASOCIACIONES: Josseline pide cita a Erick
        cita.setCliente(cliente);
        cita.setProgramador(programador);
        
        daoAsesoria.insert(cita);
        System.out.println("✅ Asesoría agendada entre Cliente y Programador.");
        
        System.out.println("========== CARGA FINALIZADA ==========");
    }
}
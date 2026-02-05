package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import ec.edu.ups.ppw.gproyectos.Proyecto;

@Stateless
public class ProyectoDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Inserta un nuevo proyecto. 
     * Se usa merge en lugar de persist para evitar errores si la Persona 
     * vinculada ya existe en el contexto de persistencia.
     */
    public void insert(Proyecto proyecto) {
        em.merge(proyecto);
    }

    /**
     * Actualiza un proyecto existente.
     */
    public void update(Proyecto proyecto) {
        em.merge(proyecto);
    }

    /**
     * Lee un proyecto por su código (ID).
     */
    public Proyecto read(int codigo) {
        return em.find(Proyecto.class, codigo);
    }

    /**
     * Elimina un proyecto por su código.
     */
    public void delete(int codigo) {
        Proyecto p = read(codigo);
        if (p != null) {
            em.remove(p);
        }
    }

    /**
     * Obtiene todos los proyectos registrados.
     */
    public List<Proyecto> getAll() {
        String jpql = "SELECT p FROM Proyecto p";
        return em.createQuery(jpql, Proyecto.class).getResultList();
    }

    /**
     * Obtiene los proyectos filtrados por la cédula del programador.
     * @param cedula La cédula de la Persona vinculada.
     */
    public List<Proyecto> getProyectosPorUsuario(String cedula) {
        // Se accede a p.programador.cedula porque en Persona.java el atributo es 'cedula'
        String jpql = "SELECT p FROM Proyecto p WHERE p.programador.cedula = :cedula";
        TypedQuery<Proyecto> q = em.createQuery(jpql, Proyecto.class);
        q.setParameter("cedula", cedula);
        return q.getResultList();
    }
}
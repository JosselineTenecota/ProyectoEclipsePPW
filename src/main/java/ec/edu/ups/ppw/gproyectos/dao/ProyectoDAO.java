package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
// IMPORTANTE: Si moviste tus entidades al paquete .model, ajusta este import:
// import ec.edu.ups.ppw.gproyectos.model.Proyecto;
import ec.edu.ups.ppw.gproyectos.Proyecto;

@Stateless
public class ProyectoDAO {

    @PersistenceContext
    private EntityManager em;

    // Crear
    public void insert(Proyecto proyecto) {
        em.persist(proyecto);
    }

    // Actualizar
    public void update(Proyecto proyecto) {
        em.merge(proyecto);
    }

    // Leer por id
    public Proyecto read(int codigo) {
        Proyecto p = em.find(Proyecto.class, codigo);
        return p;
    }

    // Eliminar
    public void delete(int codigo) {
        Proyecto p = em.find(Proyecto.class, codigo);
        if (p != null) {
            em.remove(p);
        }
    }

    // Listar todos
    public List<Proyecto> getAll() {
        String jpql = "SELECT p FROM Proyecto p";
        Query q = em.createQuery(jpql, Proyecto.class);
        return q.getResultList();
    }

    // -------------------------------------------------------
    // ESTE ES EL MÉTODO QUE TE FALTABA
    // -------------------------------------------------------
    public List<Proyecto> getProyectosPorUsuario(String cedula) {
        // La consulta asume que Proyecto tiene 'programador' (Usuario) 
        // y Usuario tiene 'persona' (Persona) donde está la 'cedula'.
        String jpql = "SELECT p FROM Proyecto p WHERE p.programador.persona.cedula = :cedula";
        
        Query q = em.createQuery(jpql, Proyecto.class);
        q.setParameter("cedula", cedula);
        return q.getResultList();
    }
}
package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
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
}
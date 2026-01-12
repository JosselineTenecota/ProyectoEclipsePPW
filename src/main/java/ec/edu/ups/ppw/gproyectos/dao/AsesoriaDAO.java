package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import ec.edu.ups.ppw.gproyectos.Asesoria;

@Stateless
public class AsesoriaDAO {

    @PersistenceContext
    private EntityManager em;

    // Crear
    public void insert(Asesoria asesoria) {
        em.persist(asesoria);
    }

    // Actualizar
    public void update(Asesoria asesoria) {
        em.merge(asesoria);
    }

    // Leer por ID (Codigo es int)
    public Asesoria read(int codigo) {
        Asesoria a = em.find(Asesoria.class, codigo);
        return a;
    }

    // Eliminar
    public void delete(int codigo) {
        Asesoria a = em.find(Asesoria.class, codigo);
        if (a != null) {
            em.remove(a);
        }
    }

    // Listar todos
    public List<Asesoria> getAll() {
        String jpql = "SELECT a FROM Asesoria a";
        Query q = em.createQuery(jpql, Asesoria.class);
        return q.getResultList();
    }
}
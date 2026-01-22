package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import ec.edu.ups.ppw.gproyectos.Horario;

@Stateless
public class HorarioDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(Horario h) {
        em.persist(h);
    }

    public void update(Horario h) {
        em.merge(h);
    }

    public Horario read(int id) {
        return em.find(Horario.class, id);
    }

    public void delete(int id) {
        Horario h = em.find(Horario.class, id);
        if (h != null) {
            em.remove(h);
        }
    }

    // Obtener horarios de un programador específico (por su cédula)
    public List<Horario> getPorProgramador(String cedula) {
        // Relacionamos Horario -> Persona (programador) -> cedula
        String jpql = "SELECT h FROM Horario h WHERE h.programador.cedula = :cedula";
        Query q = em.createQuery(jpql, Horario.class);
        q.setParameter("cedula", cedula);
        return q.getResultList();
    }
}

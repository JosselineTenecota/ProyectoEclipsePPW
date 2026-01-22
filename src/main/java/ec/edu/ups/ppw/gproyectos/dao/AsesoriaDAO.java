package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;

// Ajusta este import si moviste la entidad a .model
import ec.edu.ups.ppw.gproyectos.Asesoria; 

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class AsesoriaDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(Asesoria a) { 
        em.persist(a); 
    }
    
    public void update(Asesoria a) { 
        em.merge(a); 
    }
    
    public Asesoria read(int codigo) { 
        return em.find(Asesoria.class, codigo); 
    }

    // Listar todos (para reportes generales)
    public List<Asesoria> getAll() {
        String jpql = "SELECT a FROM Asesoria a";
        Query q = em.createQuery(jpql, Asesoria.class);
        return q.getResultList();
    }

    // Filtro para el Programador
    public List<Asesoria> getPorProgramador(String cedulaProg) {
        String jpql = "SELECT a FROM Asesoria a WHERE a.programador.cedula = :cedula";
        Query q = em.createQuery(jpql, Asesoria.class);
        q.setParameter("cedula", cedulaProg);
        return q.getResultList();
    }
    
    // Filtro por Estado (Pendiente, Aprobada...)
    public List<Asesoria> getPorEstado(String estado) {
        String jpql = "SELECT a FROM Asesoria a WHERE a.estado = :estado";
        Query q = em.createQuery(jpql, Asesoria.class);
        q.setParameter("estado", estado);
        return q.getResultList();
    }

    // --- NUEVO MÉTODO OBLIGATORIO ---
    // Filtro para el Cliente (Usuario Normal)
    public List<Asesoria> getPorCliente(String cedulaCliente) {
        String jpql = "SELECT a FROM Asesoria a WHERE a.cliente.cedula = :cedula";
        Query q = em.createQuery(jpql, Asesoria.class);
        q.setParameter("cedula", cedulaCliente);
        return q.getResultList();
    }
}
package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import ec.edu.ups.ppw.gproyectos.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(Usuario u) { 
    	em.persist(u); 
    }
    public void update(Usuario u) { 
    	em.merge(u); 
    }
    public Usuario read(String correo) { 
    	return em.find(Usuario.class, correo); 
    }
    
    public Usuario getUsuarioPorCorreo(String correo) {
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.correo = :correo";
            Query q = em.createQuery(jpql, Usuario.class);
            q.setParameter("correo", correo);
            return (Usuario) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Usuario> getAll() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }
}
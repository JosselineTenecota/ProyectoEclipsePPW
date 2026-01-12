package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import ec.edu.ups.ppw.gproyectos.Usuario;

@Stateless
public class UsuarioDAO {

    @PersistenceContext
    private EntityManager em;

    // Crear
    public void insert(Usuario usuario) {
        em.persist(usuario);
    }

    // Actualizar
    public void update(Usuario usuario) {
        em.merge(usuario);
    }

    // Leer por ID (Cédula es String)
    public Usuario read(String cedula) {
        Usuario u = em.find(Usuario.class, cedula);
        return u;
    }

    // Eliminar
    public void delete(String cedula) {
        Usuario u = em.find(Usuario.class, cedula);
        if (u != null) {
            em.remove(u);
        }
    }

    // Listar todos
    public List<Usuario> getAll() {
        String jpql = "SELECT u FROM Usuario u";
        Query q = em.createQuery(jpql, Usuario.class);
        return q.getResultList();
    }
}
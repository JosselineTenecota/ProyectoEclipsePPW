package ec.edu.ups.ppw.gproyectos.dao;

import java.util.List;

import ec.edu.ups.ppw.gproyectos.Persona;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class PersonaDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void insert(Persona persona) {
		em.persist(persona);
		
	}
	
	public void update(Persona persona) {
		em.merge(persona);
	}

	public Persona read(String pk ) {
		return em.find(Persona.class, pk);
		
	}
	
	public void delete(String pk) {
		Persona persona = em.find(Persona.class, pk);
		em.remove(persona);
	}
	
	public List<Persona> getAll(){
		String jpql = "SELECT p FROM Persona p";
		Query q = em.createQuery(jpql, Persona.class);
		return q.getResultList();
		
	}
}

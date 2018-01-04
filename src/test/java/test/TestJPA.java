package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;

public class TestJPA {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantPU");
		
		EntityManager em = emf.createEntityManager();
		
		Owner owner = new Owner("Pocas", "Helder", "test", +277225568, "email");
		
		em.getTransaction().begin();
		em.persist(owner);
		
		
		em.getTransaction().commit();
		

	}

}

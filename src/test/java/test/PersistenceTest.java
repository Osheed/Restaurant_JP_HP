package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import ch.hevs.businessobject.Menu;
import ch.hevs.businessobject.Owner;
import ch.hevs.businessobject.Restaurant;



public class PersistenceTest {

	
	
	@Test
	public void test(){
		EntityTransaction tx = null;
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantPU");
			EntityManager em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			 

			Owner owner = new Owner("Pocas", "Helder", "test", +277225568, "email");
			
			Restaurant restaurant = new Restaurant("Hotel Porte d'octodure", "Route du Grand-St-Bernard", 1920, "Switzerland", owner);
			
			owner.addRestaurant(restaurant);
			
			Menu menu1 = new Menu("menu1", "menu1Desc", 19.90f, restaurant);
			
			Menu menu2 = new Menu("menu2", "menu2Desc", 25.50f, restaurant);
			
			Menu menu3= new Menu("menu3", "menu3Desc", 5.5f, restaurant);
			
			restaurant.addMenu(menu1);
			restaurant.addMenu(menu2);
			restaurant.addMenu(menu3);
			
			//Rating rating = new Rating(5, "rating1", restaurant);
			
			//restaurant.addRating(rating);
			
			
			em.persist(owner);
			em.persist(restaurant);
			
			
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();

			try {
				tx.rollback();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}

		}

	}
}

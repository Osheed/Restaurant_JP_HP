package test;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import businessobject.Owner;

public class PersistenceTest {

	public static void main(String[] args)throws NamingException{
		EntityTransaction tx = null;
		try {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("RestaurantPU");
			EntityManager em = emf.createEntityManager();
			//tx = em.getTransaction();
			//tx.begin();

			Owner owner = new Owner("Pocas", "Helder", "test", +277225568, "email");
			
			em.persist(owner);

			//em.flush();
			
			//tx.commit();
			em.getTransaction().commit();
			
			System.out.println("Id = "+owner.getId());
		} catch (Exception e) {
			e.printStackTrace();

			try {
				//tx.rollback();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}
}

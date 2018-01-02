package restaurantService;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;

@Stateful
public class RestaurantBean implements IRestaurant {

	@PersistenceContext(name = "HolidayPU", type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Override
	public List<Owner> getOwners() {
		try {
			return em.createQuery("FROM Owner").getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Owner getOwner(String email) {
		try {
			Query query = em.createQuery("FROM Owner o WHERE o.email=:email");
			query.setParameter("email", email);
			
			Owner owner = (Owner)query.getSingleResult();
			return owner;
		} catch (Exception e) {
			return null;
		}	
	}

	@Override
	public List<Restaurant> getRestaurants() {
		try {
			return em.createQuery("FROM Restaurant").getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Restaurant getRestaurant(String name) {
		try {
			Query query = em.createQuery("FROM Restaurant r WHERE r.name_restaurant=:name_restaurant");
			query.setParameter("name_restaurant", name);
			
			Restaurant restaurant = (Restaurant)query.getSingleResult();
			return restaurant;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Menu> getMenus(Restaurant currentRestaurantId) {
		try {
			Query query = em.createQuery("FROM Menu m where m.restaurant=:restaurant");
			query.setParameter("restaurant", currentRestaurantId);
			
			return query.getResultList();			
			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void addMenu(String name, String desc, float price, Restaurant restaurant) {
		Menu menu = new Menu(name, desc, price, restaurant);		
		em.persist(menu);
	}

	@Override
	public void updateMenu(Menu menu, String name, String desc, float price) {
		menu.setName(name);
		menu.setDescription(desc);
		menu.setPrice(price);
		menu.setRestaurant(menu.getRestaurant());
		
		em.merge(menu);
	}

	@Override
	public void removeMenu(long menuId) {
		Query query = em.createQuery("FROM Menu m WHERE m.id=:menuId");
		query.setParameter("menuId", menuId);
		Menu menu = (Menu)query.getSingleResult();
		
		em.remove(menu);
	}

	@Override
	public List<Rating> getRatings() {
		return em.createQuery("FROM Rating").getResultList();
	}

	@Override
	public List<Rating> getSelectedRatings(Restaurant currentRestaurantId) {
		try {
			Query query = em.createQuery("FROM Rating r where r.restaurant=:restaurant");
			query.setParameter("restaurant", currentRestaurantId);
			
			return query.getResultList();			
			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void insertRating(int amount_stars, String comment, String cusername, Restaurant restaurant) {
		Rating rating = new Rating(amount_stars, comment, cusername, restaurant);
		em.persist(rating);
	}

}

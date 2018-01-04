package restaurantService;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;

@Stateful
public class RestaurantBean implements IRestaurant {

	@PersistenceContext(name = "RestaurantPU")
	private EntityManager em;
	
	@Override
	public List<Owner> getOwners() {
		try {
			System.out.println("RestaurantBean - getOwners");
			return em.createQuery("FROM Owner").getResultList();
		} catch (Exception e) {
			System.out.println("RestaurantBean - getOwners failed");
			return null;
		}
	}
	
	public void registerOwner( String lastname, String firstname,String password,long phone, String email  ){
		System.out.println("RestaurantBean - registerOwner");
		Owner new_Owner = new Owner(lastname, firstname, password, phone, email);
		em.persist(new_Owner);
	}

	@Override
	public Owner getOwner(String email) {
		try {
			Query query = em.createQuery("FROM Owner o WHERE o.email=:email");
			query.setParameter("email", email);
			
			Owner owner = (Owner)query.getSingleResult();
			System.out.println("RestaurantBean - getOwner");
			return owner;
		} catch (Exception e) {
			System.out.println("RestaurantBean - getOwner failed");
			return null;
		}	
	}

	@Override
	public List<Restaurant> getRestaurants() {
		try {
			System.out.println("RestaurantBean - getRestaurants");
			return em.createQuery("FROM Restaurant").getResultList();
		} catch (Exception e) {
			System.out.println("RestaurantBean - getRestaurants failed");
			return null;
		}
	}

	@Override
	public Restaurant getRestaurant(String name) {
		try {
			Query query = em.createQuery("FROM Restaurant r WHERE r.name_restaurant=:name_restaurant");
			query.setParameter("name_restaurant", name);
			
			Restaurant restaurant = (Restaurant)query.getSingleResult();
			System.out.println("RestaurantBean - getRestaurant");
			return restaurant;
		} catch (Exception e) {
			System.out.println("RestaurantBean - getRestaurant failed");
			return null;
		}
	}

	@Override
	public List<Menu> getMenus(Restaurant currentRestaurantId) {
		try {
			Query query = em.createQuery("FROM Menu m where m.restaurant=:restaurant");
			query.setParameter("restaurant", currentRestaurantId);
			System.out.println("RestaurantBean - getMenus");
			return query.getResultList();				
		} catch (Exception e) {
			System.out.println("RestaurantBean - getMenus failed");
			return null;
		}
	}

	@Override
	public void addMenu(String name, String desc, float price, Restaurant restaurant) {
		System.out.println("RestaurantBean - addMenu");
		Menu menu = new Menu(name, desc, price, restaurant);		
		em.persist(menu);
	}

	@Override
	public void updateMenu(Menu menu, String name, String desc, float price) {
		menu.setName(name);
		menu.setDescription(desc);
		menu.setPrice(price);
		menu.setRestaurant(menu.getRestaurant());
		System.out.println("RestaurantBean - updateMenu");
		em.merge(menu);
	}

	@Override
	public void removeMenu(long menuId) {
		Query query = em.createQuery("FROM Menu m WHERE m.id=:menuId");
		query.setParameter("menuId", menuId);
		Menu menu = (Menu)query.getSingleResult();
		System.out.println("RestaurantBean - removeMenu");
		em.remove(menu);
	}

	@Override
	public List<Rating> getRatings() {
		System.out.println("RestaurantBean - getRatings");
		return em.createQuery("FROM Rating").getResultList();
	}

	@Override
	public List<Rating> getSelectedRatings(Restaurant currentRestaurantId) {
		try {
			Query query = em.createQuery("FROM Rating r where r.restaurant=:restaurant");
			query.setParameter("restaurant", currentRestaurantId);
			System.out.println("RestaurantBean - getSelectedRatings");
			return query.getResultList();			
			
		} catch (Exception e) {
			System.out.println("RestaurantBean - getSelectedRatings failed");
			return null;
		}
	}

	@Override
	public void insertRating(int amount_stars, String comment, String cusername, Restaurant restaurant) {
		Rating rating = new Rating(amount_stars, comment, cusername, restaurant);
		System.out.println("RestaurantBean - insertRating");
		em.persist(rating);
	}

}

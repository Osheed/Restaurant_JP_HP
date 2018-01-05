package restaurantService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import businessobject.Rating;
import businessobject.Restaurant;

public class RatingManagementBean implements IRating{

	@PersistenceContext(name = "RestaurantPU")
	private EntityManager em;
	

	@Override
	public List<Rating> getRatings() {
		System.out.println("RestaurantManagementBean - getRatings");
		return em.createQuery("FROM Rating").getResultList();
	}

	@Override
	public List<Rating> getSelectedRatings(Restaurant currentRestaurantId) {
		try {
			Query query = em.createQuery("FROM Rating r where r.restaurant=:restaurant");
			query.setParameter("restaurant", currentRestaurantId);
			System.out.println("RestaurantManagementBean - getSelectedRatings");
			return query.getResultList();			
			
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - getSelectedRatings failed");
			return null;
		}
	}

	@Override
	public void insertRating(int amount_stars, String comment, String cusername, Restaurant restaurant) {
		Rating rating = new Rating(amount_stars, comment, cusername, restaurant);
		System.out.println("RestaurantManagementBean - insertRating");
		em.persist(rating);
	}
}

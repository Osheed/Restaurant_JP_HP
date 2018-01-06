package restaurantService;

import java.util.List;

import businessobject.Rating;
import businessobject.Restaurant;



public interface IRating {

	public void insertRating(int amount_stars, String comment, String cusername, Restaurant restaurant);
	
	public List<Rating> getRatings();
	
	public List<Rating> getSelectedRatings(Restaurant currentRestaurantId);	
	
	public List<Restaurant> getRestaurants();
	
	

}

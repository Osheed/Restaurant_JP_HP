package restaurantservice;

import java.util.List;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;

public interface IManagement {

	public List<Owner> getOwners();
	public Owner getOwner(String email);
	
	public List<Restaurant> getRestaurants();
	public Restaurant getRestaurant(String name);
	
	public List<Menu> getMenus(Restaurant currentRestaurantId);
	public void addMenu(String name, String desc, float price, Restaurant restaurant);
	public void updateMenu(Menu menu, String name, String desc, float price);
	public void removeMenu(long menuId);
	
	public List<Rating> getRatings();
	public List<Rating> getSelectedRatings(Restaurant currentRestaurantId);
	public void insertRating(int amount_stars, String comment, String cusername, Restaurant restaurant);
	
}

package restaurantservice;

import java.util.List;

import javax.ejb.Stateless;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;

@Stateless
public class ManagementBean implements IManagement {

	@Override
	public List<Owner> getOwners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Owner getOwner(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Restaurant> getRestaurants() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Restaurant getRestaurant(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> getMenus(Restaurant currentRestaurantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMenu(String name, String desc, float price, Restaurant restaurant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMenu(Menu menu, String name, String desc, float price) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMenu(long menuId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rating> getRatings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> getSelectedRatings(Restaurant currentRestaurantId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertRating(int amount_stars, String comment, String cusername, Restaurant restaurant) {
		// TODO Auto-generated method stub
		
	}

}

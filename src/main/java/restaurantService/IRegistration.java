package restaurantService;

import businessobject.Owner;
import businessobject.Restaurant;

public interface IRegistration {

	public Owner login(String email, String password);
	
	public void register(Owner owner, Restaurant restaurant, String lastname, String firstname, String password, int phone, String email, String name_restaurant, String address, int postcode, String country);
	
	public boolean checkEmailDuplication(IRestaurant irestaurant, String email);
	public boolean checkRestaurantDuplication(IRestaurant irestaurant, String name);
}

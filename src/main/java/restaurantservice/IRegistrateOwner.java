package restaurantservice;

import businessobject.Owner;
import businessobject.Restaurant;

public interface IRegistrateOwner {
	
	public Owner login(String email, String password);
	public void register(Owner owner, Restaurant restaurant, String lastname, String firstname, String password, int phone, String email, String name_restaurant, String address, int postcode, String country);
	public boolean checkEmailDuplication(IManagement imanage, String email);
	
}
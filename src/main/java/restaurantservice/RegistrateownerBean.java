package restaurantservice;

import javax.ejb.Stateful;

import businessobject.Owner;
import businessobject.Restaurant;

@Stateful
public class RegistrateownerBean implements IRegistrateOwner {

	@Override
	public Owner login(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(Owner owner, Restaurant restaurant, String lastname, String firstname, String password,
			int phone, String email, String name_restaurant, String address, int postcode, String country) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkEmailDuplication(IManagement imanage, String email) {
		// TODO Auto-generated method stub
		return false;
	}

}

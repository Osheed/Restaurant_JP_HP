package restaurantService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import businessobject.Owner;
import businessobject.Restaurant;

public class RegistrationBean2 implements IRegistration {

	@PersistenceContext(name = "RestaurantPU")
	private EntityManager em;
	
	@Override
	public Owner login(String email, String password) {
		try {
			Query query = em.createQuery("FROM Owner o WHERE o.email=:email AND o.password=:password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			
			Owner owner = (Owner)query.getSingleResult();

			return owner;
		} catch (Exception e) {
			return null;
		}
	}

	public void register(Owner owner, Restaurant restaurant, String lastname, String firstname, String password, String phone, String email, 
			String name_restaurant, String address, int postcode, String country) {
		
		Owner ownerNew = new Owner(lastname, firstname, password, phone, email);
		Restaurant restaurantNew = new Restaurant(name_restaurant, address, postcode, country, ownerNew);
		ownerNew.setRestaurant(restaurantNew);
		
		//setCurrentOwner(owner, ownerNew);
		//setCurrentRestaurant(restaurant, restaurantNew);

		em.persist(ownerNew);
		em.persist(restaurantNew);
		
		
	}
	
	private void setCurrentOwner(Owner currentOwner, Owner newOwner){
		currentOwner.setFirstname(newOwner.getFirstname());
		currentOwner.setLastname(newOwner.getLastname());
		currentOwner.setPassword(newOwner.getPassword());
		currentOwner.setPhone(newOwner.getPhone());
		currentOwner.setEmail(newOwner.getEmail());
	}
	
	private void setCurrentRestaurant(Restaurant currentRestaurant, Restaurant newRestaurant){
		currentRestaurant.setName_restaurant(newRestaurant.getName_restaurant());
		currentRestaurant.setAddress(newRestaurant.getAddress());
		currentRestaurant.setPostcode(newRestaurant.getPostcode());
		currentRestaurant.setCountry(newRestaurant.getCountry());
	}

	@Override
	public boolean checkEmailDuplication(IRestaurant irestaurant, String email) {
		Owner owner = irestaurant.getOwner(email);
		
		if (owner != null) {return true;}
		return false;
	}

	@Override
	public boolean checkRestaurantDuplication(IRestaurant irestaurant, String name) {
		Restaurant restaurant = irestaurant.getRestaurant(name);

		if (restaurant != null) {return true;}
		return false;
	}

	@Override
	public void register(Owner owner, Restaurant restaurant, String lastname, String firstname, String password,
			int phone, String email, String name_restaurant, String address, int postcode, String country) {
		// TODO Auto-generated to rename class
		
	}

}

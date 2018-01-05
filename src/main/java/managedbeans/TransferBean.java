package managedbeans;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;
import restaurantService.IRestaurant;

public class TransferBean {

	private IRestaurant manageBean;
	private List<String> restaurantNames;
	private List<Restaurant> restaurants;
	
	//Objects
	private Owner owner;
	private Menu menu;
	private Rating rating;
	private Restaurant restaurant;
	
	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		manageBean = (IRestaurant) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantBean!restaurantService.IRestaurant");
		
		//get restaurants
		List<Restaurant> restaurantList = manageBean.getRestaurants();
		
		this.restaurantNames = new ArrayList<String>();
		for(Restaurant rest : restaurantList) {
			this.restaurantNames.add(rest.getName_restaurant());
		}
		
		System.out.println("Test initialise lalslaosoasoasoaolalaoalaoalaasoalasosao");
		
	}
	public void registration(){
		owner = new Owner();
		try{
		manageBean.registerOwner("Pocas", "Helder", "1234", +788417093, "test@test.com");
		System.out.println(owner+"aososoaoasofasofosfoasofdosadfoasodfasodfaosdfoasdofasofsoa");
	}
	
	//TODO: Erase the tests
	public List<String> getRestaurantNames() {
		this.restaurantNames.add("test1");
		this.restaurantNames.add("test2");
		this.restaurantNames.add("test3");
    	return restaurantNames;
    }
	//TODO: Erase the tests
    public List<String> restaurantNames() {
		this.restaurantNames.add("test1");
		this.restaurantNames.add("test2");
		this.restaurantNames.add("test3");
    	return restaurantNames;
    }
    
  //TODO: Check if is correct
    public List<Restaurant> getRestaurants() {
    	return restaurants;
    }
	
}

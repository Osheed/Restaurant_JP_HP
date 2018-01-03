package managedbeans;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import businessobject.Restaurant;
import restaurantService.IRestaurant;

public class TransferBean {

	private IRestaurant manageBean;
	private List<String> restaurantNames;
	private List<Restaurant> restaurants;
	
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
		
		this.restaurantNames.add("test1");
		this.restaurantNames.add("test2");
		this.restaurantNames.add("test3");
		
	}
	
	
	
	public List<String> getRestaurantNames() {
		this.restaurantNames.add("test1");
		this.restaurantNames.add("test2");
		this.restaurantNames.add("test3");
    	return restaurantNames;
    }
    
    public List<String> restaurantNames() {
		this.restaurantNames.add("test1");
		this.restaurantNames.add("test2");
		this.restaurantNames.add("test3");
    	return restaurantNames;
    }
    
    
    public List<Restaurant> getRestaurants() {
    	return restaurants;
    }
	
}

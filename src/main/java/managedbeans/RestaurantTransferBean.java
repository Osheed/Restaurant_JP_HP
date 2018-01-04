package managedbeans;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Restaurant;
import restaurantService.IRegistration;
import restaurantService.IRestaurant;

public class RestaurantTransferBean {

	private IRestaurant manageBean;
	private Owner currentOwner;
	private Restaurant currentRestaurant;
	private Menu currentMenu;
	private List<Menu> menuList;
	private IRegistration registrateOwnerBean;
	
	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		registrateOwnerBean = (IRegistration) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RegistrationBean!restaurantService.IRegistration");
		manageBean = (IRestaurant) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantBean!restaurantService.IRestaurant");
	}
	
}

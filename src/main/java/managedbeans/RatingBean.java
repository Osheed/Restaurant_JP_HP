package managedbeans;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import restaurantService.IRating;

public class RatingBean {

	private IRating rater;
	
	
	@PostConstruct
	public void initialize() throws NamingException{
		
		
	InitialContext ctx = new InitialContext();
	rater = (IRating) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantManagementBean!restaurantService.IRating");
	
	}
	
}

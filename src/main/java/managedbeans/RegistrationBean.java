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
import restaurantService.IRegistration;
import restaurantService.IRestaurant;

public class RegistrationBean {

	private IRestaurant irestaurant;
	private IRegistration iregistration;
	private List<String> restaurantNames;
	private List<Restaurant> restaurants;
	
	//NavigationRule
	private String navigateTo;
	
	//Objects
	private Owner owner;
	private Menu menu;
	private Rating rating;
	private Restaurant restaurant;
	
	//Data for new User
	private String lastname;
	private String firstname;
	private String password;
	private String phone;
	private String confirmPassword;
	private String email;
	
	//Data for RegisterUser
	private String emailLogin;
	private String passwordLogin;
	
	//Informations
	private String loginInformation="";
	
	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		irestaurant = (IRestaurant) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantManagementBean!restaurantService.IRestaurant");
		
		//get restaurants
		List<Restaurant> restaurantList = irestaurant.getRestaurants();
		
		this.restaurantNames = new ArrayList<String>();
		for(Restaurant rest : restaurantList) {
			this.restaurantNames.add(rest.getName_restaurant());
		}
		
		System.out.println("Test initialise in RegistrationBean,Test initialise in RegistrationBean");
		
	}
	
	//TODO: Tests for EmptyValues/DuplicatedValues(email)/NoSamePassword-
	public String registration(){
		irestaurant.registerOwner(this.lastname, this.firstname, this.password, this.phone, this.email);
		loginInformation = "You are Register Successfully - Enter your credentials";	
		navigateTo = "welcomePage";

		return navigateTo;
	}
	
	
	public String login(){
		try {
			if (isEmptyLoginData()) {
				this.loginInformation = "Please insert all fields";
				navigateTo = "welcomePage";
			} 
			Owner ownerTemp = this.iregistration.login(this.emailLogin, this.passwordLogin);

			if(ownerTemp == null){
				this.loginInformation = "Wrong username or password";
				navigateTo = "welcomePage";
			}
			else {
				this.owner = ownerTemp;
				//this.currentRestaurant = this.manageBean.getRestaurant(this.currentOwner.getRestaurant(0L).getName_restaurant());
				navigateTo = "manageData";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.navigateTo;
	}
	
	private boolean isEmptyLoginData(){
		if(this.emailLogin.isEmpty() || this.emailLogin.trim().equals("") || this.passwordLogin.isEmpty() || this.passwordLogin.trim().equals("")) {return true;}
		return false;
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
	
    
    /*
     * NavigationRule: Method to navigate 
     */
    public String getNavigateTo() {
		return navigateTo;
	}
    
	public void setNavigateTo(String navigateTo) {
		this.navigateTo = navigateTo;
	}
	
	/*
	 * Getters & Setters
	 */
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailLogin() {
		return emailLogin;
	}

	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
	}

	public String getPasswordLogin() {
		return passwordLogin;
	}

	public void setPasswordLogin(String passwordLogin) {
		this.passwordLogin = passwordLogin;
	}

	public String getLoginInformation() {
		return loginInformation;
	}

	public void setLoginInformation(String loginInformation) {
		this.loginInformation = loginInformation;
	}
	
	
}

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
import restaurantService.IRating;
import restaurantService.IManagement;

public class RegistrationBean {

	private IManagement manager;
	private IRating iregistration;
	private List<String> restaurantNames;
	private List<Restaurant> restaurants;
	private List<Menu> menus;
	
	//NavigationRule
	private String navigateTo;
	
	//ManageData
	private String ownerTitleLabel;
	
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
	
	//Data for Restaurant
	private String addressR;
	private String countryR;
	private String nameR;
	private int postcodeR;
	
	//Data for Menu
	private String nameM;
	private String descriptionM;
	private float priceM;
		
	//Informations
	private String loginInformation="";
	private String registerInformation="";
	private String newRestaurantInformation="";
	private String manageDataInformation="";
	
	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		manager = (IManagement) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantManagementBean!restaurantService.IManagement");
		
		//get restaurants
		List<Restaurant> restaurantList = manager.getRestaurants();
		
		this.restaurantNames = new ArrayList<String>();
		for(Restaurant rest : restaurantList) {
			this.restaurantNames.add(rest.getName_restaurant());
		}
		
		System.out.println("Test initialise in RegistrationBean,Test initialise in RegistrationBean");
		
	}
	
	//TODO: Tests for EmptyValues/DuplicatedValues(email)/NoSamePassword-
	public String registration(){
		manager.registerOwner(this.lastname, this.firstname, this.password, this.phone, this.email);
		loginInformation = "You are Register Successfully - Enter your credentials";	
		navigateTo = "welcomePage";

		cleanRegistrationForm();
		return navigateTo;
	}
	
	public void cleanRegistrationForm() {
		this.lastname = "";
		this.firstname = "";
		this.phone = "";
		this.email = "";
	}
	
	public String login(){
		try {
			if (isEmptyLoginData()) {
				this.loginInformation = "Please insert all fields";
				navigateTo = "welcomePage";
			} 
			Owner ownerTemp = this.manager.login(this.emailLogin, this.passwordLogin);

			if(ownerTemp == null){
				this.loginInformation = "Wrong username or password";
				navigateTo = "welcomePage";
			}
			else {
				owner = ownerTemp;
				navigateTo = "manageData";
				System.out.println( "In the login method i have "+owner.getFirstname()+" "+owner.getLastname());
				ownerTitleLabel = "Welcome "+owner.getFirstname()+" "+owner.getLastname();
				lastname = owner.getLastname();
				firstname = owner.getFirstname();
				phone = owner.getFirstname();
				email= owner.getFirstname();
				isRestaurantInDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.emailLogin = "";
		return this.navigateTo;
	}
	
	public String logout(){
		this.owner = null;
		this.restaurant = null;
		this.restaurants = null;
		//resetValueMenuNull();
		//resetValueNull();
		return "welcomePage";
	}
	
	/*
	 * Managing the Restaurants
	 */
	public Boolean isRestaurantInDB(){
		if(restaurants.size() == 0){
			System.out.println("The size of the Restaurants in DB : "+restaurants.size());
			manageDataInformation = "You need to Add a Restaurant before";
			return false;
		}
		manageDataInformation = "";
		return true;
	}
	
	//TODO: test for owner/DuplicatedValues(name-address)/and no restaurant dont show addmenu
	public String registerNewRestaurant(){
		manager.registerRestaurant(addressR, countryR, nameR, postcodeR, this.owner);
		newRestaurantInformation = "New Restaurant Successfully Created ";
		navigateTo = "manageData";
		
		resetValueRestaurantNull();
		
		return navigateTo;
	}
	
	private boolean isEmptyLoginData(){
		if(this.emailLogin.isEmpty() || this.emailLogin.trim().equals("") || this.passwordLogin.isEmpty() || this.passwordLogin.trim().equals("")) {return true;}
		return false;
	}
	
	public String editRestaurant(Restaurant r){
		this.restaurant = r;

		// set data on variables of the menu to display
		this.nameR = r.getName_restaurant();
		this.addressR = r.getAddress();
		this.postcodeR = r.getPostcode();
		this.countryR = r.getCountry();

		navigateTo = "addRestaurant";
		return navigateTo;
	}
	
	public String removeRestaurant(Restaurant r){
		this.manager.removeRestaurant(r.getId());
		return null;
	}
	
	private void resetValueRestaurantNull(){
		this.nameR = "";
		this.addressR = "";
		this.postcodeR = 0;
		this.countryR = "";
	}	
	
	/*
	 * Managing the Menus
	 */
	public String saveMenu(){
		// get data
		if (menu != null) {
			manager.updateMenu(menu, this.nameM, this.descriptionM, this.priceM);
			resetValueMenuNull();
		}else{
			manager.addMenu(this.nameM,this.descriptionM, this.priceM, restaurant);
			resetValueMenuNull();
		}

		navigateTo = "manageMenus";
		return navigateTo;
	}

	public String editMenu(Menu m){
		// specific menu selected in the list or user wants to add new menu
		this.menu = m;

		// set data on variables of the menu to display
		this.nameM = m.getName();
		this.descriptionM = m.getDescription();
		this.priceM = m.getPrice();

		navigateTo = "addMenu";
		return navigateTo;
	}
	
	public String removeMenu(Menu m){
		this.manager.removeMenu(m.getId());
		return null;
	}
	
	private void resetValueMenuNull(){
		this.nameM = "";
		this.descriptionM = "";
		this.priceM = 0f;
		this.menu = null;
		this.loginInformation = "";
	}	
    
	public String details(){
		System.out.println("value of is RestaurantinDB() : "+isRestaurantInDB());
		if(isRestaurantInDB()){
			navigateTo = "manageMenus";
		}
		else{
			navigateTo = "manageData";
		}
		return navigateTo;
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
	
	public String cancel(){
		// delete data from variables
		resetValueMenuNull();
		return navigateTo = "manageMenus";
	}
	
	/*
	 * Getters & Setters
	 */
	
	public String getLastname() {
		return lastname;
	}
	
	public List<Restaurant> getRestaurants() {
		List<Restaurant> restaurants = this.manager.getRestaurants();
		List<Restaurant> ownersRestaurants = new ArrayList<>(); 
		for (Restaurant restaurant : restaurants) {
			if(restaurant.getOwner().getId() == this.owner.getId()) {
				ownersRestaurants.add(restaurant);
			}
		}
		return ownersRestaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	
	public List<Menu> getMenus() {
		List<Menu> menus = this.manager.getMenus(restaurant);
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
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

	public String getOwnerTitleLabel() {
		return ownerTitleLabel;
	}

	public void setOwnerTitleLabel(String ownerTitleLabel) {
		this.ownerTitleLabel = ownerTitleLabel;
	}

	public String getNewRestaurantInformation() {
		return newRestaurantInformation;
	}

	public void setNewRestaurantInformation(String newRestaurantInformation) {
		this.newRestaurantInformation = newRestaurantInformation;
	}

	public String getRegisterInformation() {
		return registerInformation;
	}

	public void setRegisterInformation(String registerInformation) {
		this.registerInformation = registerInformation;
	}

	public String getAddressR() {
		return addressR;
	}

	public void setAddressR(String addressR) {
		this.addressR = addressR;
	}

	public String getCountryR() {
		return countryR;
	}

	public void setCountryR(String countryR) {
		this.countryR = countryR;
	}

	public String getNameR() {
		return nameR;
	}

	public void setNameR(String nameR) {
		this.nameR = nameR;
	}

	public int getPostcodeR() {
		return postcodeR;
	}

	public void setPostcodeR(int postcodeR) {
		this.postcodeR = postcodeR;
	}

	public IManagement getManager() {
		return manager;
	}

	public void setManager(IManagement manager) {
		this.manager = manager;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	public String getNameM() {
		return nameM;
	}

	public void setNameM(String nameM) {
		this.nameM = nameM;
	}

	public String getDescriptionM() {
		return descriptionM;
	}

	public void setDescriptionM(String descriptionM) {
		this.descriptionM = descriptionM;
	}

	public float getPriceM() {
		return priceM;
	}

	public void setPriceM(float priceM) {
		this.priceM = priceM;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getManageDataInformation() {
		return manageDataInformation;
	}

	public void setManageDataInformation(String manageDataInformation) {
		this.manageDataInformation = manageDataInformation;
	}
	
	
}

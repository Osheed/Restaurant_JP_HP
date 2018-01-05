package managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;
import restaurantService.IRegistration;
import restaurantService.IRestaurant;

public class RegistrationTransferBean {

	private IRegistration registrationBean;
	private IRestaurant manageBean;

	//Current objects
	private Owner currentOwner;
	private Restaurant currentRestaurant;
	private Menu currentMenu;
	private List<Menu> menuList;

	private String pageChange;

	private String loginInformation = "";
	private String registerInformation = "";
	private String rateInformation = "";

	//Login data from User
	private String emailLogin;
	private String passwordLogin;

	//Register data from User
	private String lastname;
	private String firstname;
	private int phone;
	private String emailRegister;
	private String passwordRegister;
	private String passwordRegisterConfirm;

	private String name_restaurant;
	private String address;
	private int postcode;
	private String country;

	// register new menu
	private String name_menu;
	private String description;
	private float price;	

	private List<Restaurant> restaurantList;
	private List<String> restaurantNames;

	//Rating restaurant
	private Restaurant restaurant_rating;
	private List<Restaurant> restaurantsForRating;
	private String restaurant_name_rating;
	private List<Menu> menusForRating;

	private int selectedRating;
	private String name_restaurant_rating;
	private String commentRating;
	private List<Rating> ratingList;
	private List<Rating> selectedRatings;
	private String userComment;


	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		registrationBean = (IRegistration) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RegistrationBean!restaurantService.IRegistration");
		manageBean = (IRestaurant) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantBean!restaurantService.IRestaurant");
	}

	public String navigateHome(){
		this.pageChange = "welcomePage";
		return this.pageChange;
	}


	public String registration(){
		try {
			if (isEmptyUserData()) {
				this.registerInformation = "Please insert all fields";
				this.pageChange = "register";
			} 
			else if(isDuplicatedOwner()){
				this.registerInformation = "User already exists";
				this.pageChange = "register";
			}
			else if(isDuplicatedRestaurant()){
				this.registerInformation = "Restaurant already exists";
				this.pageChange = "register";
			}
			else {
				this.currentOwner = new Owner();
				this.currentRestaurant = new Restaurant();
				this.registrationBean.register(this.currentOwner, this.currentRestaurant, 
						lastname, firstname, passwordRegister,
						phone, emailRegister, name_restaurant, address,
						postcode, country);

				Owner ownerTemp = this.registrationBean.login(this.emailRegister, this.passwordRegister);
				this.currentOwner = ownerTemp;
				this.currentRestaurant = this.manageBean.getRestaurant(this.currentOwner.getRestaurant(0L).getName_restaurant());

				this.pageChange =  "manageData";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return this.pageChange;
	}

	public String login(){
		try {
			if (isEmptyLoginData()) {
				this.loginInformation = "Please insert all fields";
				this.pageChange = "welcomePage";
			} 
			Owner ownerTemp = this.registrationBean.login(this.emailLogin, this.passwordLogin);

			if(ownerTemp == null){
				this.loginInformation = "Wrong username or password";
				this.pageChange = "welcomePage";
			}
			else {
				this.currentOwner = ownerTemp;
				this.currentRestaurant = this.manageBean.getRestaurant(this.currentOwner.getRestaurant(0L).getName_restaurant());
				this.pageChange =  "manageData";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return this.pageChange;
	}

	public String logout(){
		this.currentOwner = null;
		this.currentRestaurant = null;
		resetValueMenuNull();
		resetValueNull();
		return "welcomePage";
	}

	public String editMenu(Menu m){
		// specific menu selected in the list or user wants to add new menu
		this.currentMenu = m;

		// set data on variables of the menu to display
		this.name_menu = m.getName();
		this.description = m.getDescription();
		this.price = m.getPrice();

		this.pageChange = "addMenu";
		return this.pageChange;
	}

	public String cancel(){
		// delete data from variables
		resetValueMenuNull();

		return this.pageChange = "manageMenus";
	}

	private void resetValueMenuNull(){
		this.name_menu = "";
		this.description = "";
		this.price = 0f;
		this.currentMenu = null;
		this.loginInformation = "";
	}

	private void resetValueNull(){
		this.emailLogin = "";
		this.passwordLogin = "";
		this.emailRegister = "";
		this.passwordRegister = "";
		this.passwordRegisterConfirm = "";
		this.lastname = "";
		this.firstname = "";
		this.phone = 0;
		this.name_restaurant = "";
		this.address = "";
		this.postcode = 0;
		this.country = "";
	}

	public String saveMenu(){
		// get data
		if (currentMenu != null) {
			manageBean.updateMenu(currentMenu, this.name_menu, this.description, this.price);
			resetValueMenuNull();
		}else{
			manageBean.addMenu(this.name_menu,this.description , this.price, currentRestaurant);
			resetValueMenuNull();
		}
		this.pageChange = "manageMenus";
		return this.pageChange;
	}

	public String removeMenu(Menu m){
		this.manageBean.removeMenu(m.getId());
		return null;
	}

	public List<Menu> getMenus(){
		List<Menu> menus = this.manageBean.getMenus(this.currentRestaurant);
		return menus;
	}

	public List<Restaurant> getRestaurantsForRating(){
		this.restaurantsForRating = manageBean.getRestaurants();
		return restaurantsForRating;
	}

	private boolean isDuplicatedOwner(){
		return registrationBean.checkEmailDuplication(manageBean, this.emailRegister);
	}

	private boolean isDuplicatedRestaurant(){
		return registrationBean.checkRestaurantDuplication(manageBean, this.name_restaurant);
	}

	private boolean isEmptyLoginData(){
		if(this.emailLogin.isEmpty() || this.emailLogin.trim().equals("") || 
				this.passwordLogin.isEmpty() || this.passwordLogin.trim().equals(""))return true;
		return false;
	}

	private boolean isEmptyUserData(){
		List<String> userData = new ArrayList<String>();
		userData.add(this.lastname);
		userData.add(this.firstname);
		userData.add(this.phone+"");
		userData.add(this.emailRegister);
		userData.add(this.passwordRegister);
		userData.add(this.passwordRegisterConfirm);
		userData.add(this.name_restaurant);
		userData.add(this.address);
		userData.add(this.postcode+"");
		userData.add(this.country);

		for(String data : userData){
			if(data.isEmpty() || data.trim().equals(""))return true;
		}
		return false;
	}

	public List<String> getRestaurantList() {
		this.restaurantList = manageBean.getRestaurants();
		this.restaurantNames = new ArrayList<>();

		for (int i = 0; i < this.restaurantList.size(); i++) {
			this.restaurantNames.add(this.restaurantList.get(i).getName_restaurant());
		}
		return restaurantNames;
	}

	public void restaurantChanged(String restaurantName) {
		setMenusForRatingChanged(restaurantName);
	}

	public void setMenusForRatingChanged(String restaurantName){
		Restaurant restaurant = this.manageBean.getRestaurant(restaurantName);
		this.menusForRating = this.manageBean.getMenus(restaurant);
	}

	public String insertRating(){		
		// get restaurant by checking strings name
		this.restaurantList = manageBean.getRestaurants();
		int selectedRestIndex = -1;
		for (int i = 0; i < this.restaurantList.size(); i++) {
			if (this.restaurantList.get(i).getName_restaurant().equals(this.name_restaurant_rating)) {
				selectedRestIndex = i;
				break;
			}
		}

		if (this.selectedRating != 0 && !this.commentRating.trim().equals("") && !this.userComment.trim().equals("") && selectedRestIndex != -1) {
			manageBean.insertRating(this.selectedRating, this.commentRating, this.userComment, this.restaurantList.get(selectedRestIndex));
			// reset content
			resetRateRestaurant();
		} else {
			this.rateInformation = "sorry - not every form is filled out!";
		}
		this.pageChange = "rateRestaurant";

		return this.pageChange;
	}

	public void resetRateRestaurant(){
		this.commentRating = null;
		this.selectedRating = 0;
		this.name_restaurant_rating = null;
		this.userComment = null;
		this.rateInformation = "";
	}

	public void changeRestaurantRating() {
		System.out.println("Selected Restaurant is: ");
	}

	public void setRestaurantList(List<Restaurant> restaurantList) {
		this.restaurantList = restaurantList;
	}

	public List<String> getRestaurantNames() {
		return restaurantNames;
	}

	public void setRestaurantNames(List<String> restaurantNames) {
		this.restaurantNames = restaurantNames;
	}

	public IRegistration getRegistrationBean() {
		return registrationBean;
	}

	public void setRegistrationBean(IRegistration registrationBean) {
		this.registrationBean = registrationBean;
	}

	public IRestaurant getRestaurantBean() {
		return manageBean;
	}

	public void setRestaurantBean(IRestaurant manageBean) {
		this.manageBean = manageBean;
	}

	public Owner getCurrentOwner() {
		return currentOwner;
	}

	public void setCurrentOwner(Owner currentOwner) {
		this.currentOwner = currentOwner;
	}

	public Restaurant getCurrentRestaurant() {
		return currentRestaurant;
	}

	public void setCurrentRestaurant(Restaurant currentRestaurant) {
		this.currentRestaurant = currentRestaurant;
	}

	public Menu getCurrentMenu() {
		return currentMenu;
	}

	public void setCurrentMenu(Menu currentMenu) {
		this.currentMenu = currentMenu;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public String getPageChange() {
		return pageChange;
	}

	public void setPageChange(String pageChange) {
		this.pageChange = pageChange;
	}

	public String getLoginInformation() {
		return loginInformation;
	}

	public void setLoginInformation(String loginInformation) {
		this.loginInformation = loginInformation;
	}

	public String getRegisterInformation() {
		return registerInformation;
	}

	public void setRegisterInformation(String registerInformation) {
		this.registerInformation = registerInformation;
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

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmailRegister() {
		return emailRegister;
	}

	public void setEmailRegister(String emailRegister) {
		this.emailRegister = emailRegister;
	}

	public String getPasswordRegister() {
		return passwordRegister;
	}

	public void setPasswordRegister(String passwordRegister) {
		this.passwordRegister = passwordRegister;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPasswordRegisterConfirm() {
		return passwordRegisterConfirm;
	}

	public void setPasswordRegisterConfirm(String passwordRegisterConfirm) {
		this.passwordRegisterConfirm = passwordRegisterConfirm;
	}	


	public String getName_menu() {
		return name_menu;
	}

	public void setName_menu(String name_menu) {
		this.name_menu = name_menu;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getName_restaurant() {
		return name_restaurant;
	}

	public void setName_restaurant(String name_restaurant) {
		this.name_restaurant = name_restaurant;
	}


	public Restaurant getRestaurant_rating() {
		return restaurant_rating;
	}


	public void setRestaurant_rating(Restaurant restaurant_rating) {
		this.restaurant_rating = restaurant_rating;
	}


	public void setRestaurantsForRating(List<Restaurant> restaurantsForRating) {
		this.restaurantsForRating = restaurantsForRating;
	}


	public String getRestaurant_name_rating() {
		return restaurant_name_rating;
	}


	public void setRestaurant_name_rating(String restaurant_name_rating) {
		this.restaurant_name_rating = restaurant_name_rating;
	}

	public List<Menu> getMenusForRating() {
		return menusForRating;
	}

	public void setMenusForRating(List<Menu> menusForRating) {
		this.menusForRating = menusForRating;
	}


	public int getSelectedRating() {
		return selectedRating;
	}


	public void setSelectedRating(int selectedRating) {
		this.selectedRating = selectedRating;
	}


	public String getName_restaurant_rating() {
		return name_restaurant_rating;
	}


	public void setName_restaurant_rating(String name_restaurant_rating) {
		this.name_restaurant_rating = name_restaurant_rating;
	}


	public String getCommentRating() {
		return commentRating;
	}


	public void setCommentRating(String commentRating) {
		this.commentRating = commentRating;
	}


	public List<Rating> getRatingList() {
		return ratingList;
	}


	public void setRatingList(List<Rating> ratingList) {
		this.ratingList = ratingList;
	}


	public String getUserComment() {
		return userComment;
	}


	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public List<Rating> getSelectedRatings() {
		Restaurant restaurant = this.manageBean.getRestaurant(this.name_restaurant_rating);
		return manageBean.getSelectedRatings(restaurant);
	}

	public void setSelectedRatings(List<Rating> selectedRatings) {
		this.selectedRatings = selectedRatings;
	}

	public String getRateInformation() {
		return rateInformation;
	}

	public void setRateInformation(String rateInformation) {
		this.rateInformation = rateInformation;
	}
}

package ch.hevs.businessobject;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Owner")
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name="lastname")
	private String lastname;
	@Column(name="firstname")
	private String firstname;
	@Column(name="password")
	private String password;
	@Column(name="phone")
	private long phone;
	@Column(name="email")
	private String email;
	
	// relations
	@OneToMany(mappedBy="owner",cascade = CascadeType.ALL)
	private Set<Restaurant> restaurants;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Owner(){
		this.restaurants = new HashSet<Restaurant>();
	}

	public Owner(String lastname, String firstname, String password, long phone, String email) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.restaurants = new HashSet<Restaurant>();
	}

	@Override
	public String toString() {
		return "Owner [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", password=" + password
				+ ", phone=" + phone + ", email=" + email + ", restaurants=" + restaurants + "]";
	}

		//helper methods
	public void addRestaurant(Restaurant restaurant){
		restaurants.add(restaurant);
	}

}

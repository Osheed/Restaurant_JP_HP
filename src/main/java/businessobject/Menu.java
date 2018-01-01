package businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="Menu")
public class Menu {
	
	@Id
	@SequenceGenerator(name="menuKey", initialValue=1, allocationSize=2)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="price")
	private float price;
		
	@ManyToOne
	private Restaurant restaurant;
	
	//constructors
	public Menu(){}
	
	public Menu(String name, String description, float price, Restaurant restaurant) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.restaurant = restaurant;
	}
	
	//getters and setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
		
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}

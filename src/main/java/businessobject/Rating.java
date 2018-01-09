package businessobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="amount_stars")
	private int amount_stars;
	@Column(name="comment")
	private String comment;
	@Column(name="cusername")
	private String cusername;
	
	//Add relations : : FetchType Eager by default
	@ManyToOne 
	private Restaurant restaurant;

	//constructors
	public Rating() {}
	
	public Rating(int amount_stars, String comment, String cusername, Restaurant restaurant) {
		this.amount_stars = amount_stars;
		this.comment = comment;
		this.restaurant = restaurant;
		this.cusername = cusername;
	}

	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAmount_stars() {
		return amount_stars;
	}

	public void setAmount_stars(int amount_stars) {
		this.amount_stars = amount_stars;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCusername() {
		return cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
}

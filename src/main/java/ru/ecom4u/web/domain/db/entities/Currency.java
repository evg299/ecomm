package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the currencies database table.
 * 
 */
@Entity
@Table(name="currencies")
public class Currency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private int name;

	private double rate;

	@Column(name="short_name", nullable=false)
	private int shortName;

	//bi-directional many-to-one association to Price
	@OneToMany(mappedBy="currency")
	private List<Price> prices;

	public Currency() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getName() {
		return this.name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public double getRate() {
		return this.rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getShortName() {
		return this.shortName;
	}

	public void setShortName(int shortName) {
		this.shortName = shortName;
	}

	public List<Price> getPrices() {
		return this.prices;
	}

	public void setPrices(List<Price> prices) {
		this.prices = prices;
	}

	public Price addPrice(Price price) {
		getPrices().add(price);
		price.setCurrency(this);

		return price;
	}

	public Price removePrice(Price price) {
		getPrices().remove(price);
		price.setCurrency(null);

		return price;
	}

}
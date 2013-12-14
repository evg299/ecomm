package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the prices database table.
 * 
 */
@Entity
@Table(name="prices")
public class Price implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	private double value;

	//bi-directional many-to-one association to Currency
	@ManyToOne
	@JoinColumn(name="currency_id", nullable=false)
	private Currency currency;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="price")
	private List<Product> products;

	public Price() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setPrice(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setPrice(null);

		return product;
	}

}
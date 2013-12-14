package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the product_properties database table.
 * 
 */
@Entity
@Table(name="product_properties")
public class ProductProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String name;

	@Lob
	@Column(nullable=false)
	private String value;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;

	//bi-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name="unit_id", nullable=false)
	private Unit unit;

	public ProductProperty() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
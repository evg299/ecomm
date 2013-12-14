package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product_variant_options database table.
 * 
 */
@Entity
@Table(name="product_variant_options")
public class ProductVariantOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String value;

	//bi-directional many-to-one association to ProductVariantOption
	@ManyToOne
	@JoinColumn(name="pid", nullable=false)
	private ProductVariantOption productVariantOption;

	//bi-directional many-to-one association to ProductVariantOption
	@OneToMany(mappedBy="productVariantOption")
	private List<ProductVariantOption> productVariantOptions;

	//bi-directional many-to-one association to ProductVariant
	@ManyToOne
	@JoinColumn(name="variant_id", nullable=false)
	private ProductVariant productVariant;

	//bi-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name="unit_id", nullable=false)
	private Unit unit;

	public ProductVariantOption() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ProductVariantOption getProductVariantOption() {
		return this.productVariantOption;
	}

	public void setProductVariantOption(ProductVariantOption productVariantOption) {
		this.productVariantOption = productVariantOption;
	}

	public List<ProductVariantOption> getProductVariantOptions() {
		return this.productVariantOptions;
	}

	public void setProductVariantOptions(List<ProductVariantOption> productVariantOptions) {
		this.productVariantOptions = productVariantOptions;
	}

	public ProductVariantOption addProductVariantOption(ProductVariantOption productVariantOption) {
		getProductVariantOptions().add(productVariantOption);
		productVariantOption.setProductVariantOption(this);

		return productVariantOption;
	}

	public ProductVariantOption removeProductVariantOption(ProductVariantOption productVariantOption) {
		getProductVariantOptions().remove(productVariantOption);
		productVariantOption.setProductVariantOption(null);

		return productVariantOption;
	}

	public ProductVariant getProductVariant() {
		return this.productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

	public Unit getUnit() {
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

}
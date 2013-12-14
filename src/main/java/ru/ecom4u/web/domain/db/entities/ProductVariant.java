package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the product_variants database table.
 * 
 */
@Entity
@Table(name="product_variants")
public class ProductVariant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String name;

	//bi-directional many-to-one association to ProductVariantOption
	@OneToMany(mappedBy="productVariant")
	private List<ProductVariantOption> productVariantOptions;

	//bi-directional many-to-one association to ProductVariant
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private ProductVariant productVariant;

	//bi-directional many-to-one association to ProductVariant
	@OneToMany(mappedBy="productVariant")
	private List<ProductVariant> productVariants;

	public ProductVariant() {
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

	public List<ProductVariantOption> getProductVariantOptions() {
		return this.productVariantOptions;
	}

	public void setProductVariantOptions(List<ProductVariantOption> productVariantOptions) {
		this.productVariantOptions = productVariantOptions;
	}

	public ProductVariantOption addProductVariantOption(ProductVariantOption productVariantOption) {
		getProductVariantOptions().add(productVariantOption);
		productVariantOption.setProductVariant(this);

		return productVariantOption;
	}

	public ProductVariantOption removeProductVariantOption(ProductVariantOption productVariantOption) {
		getProductVariantOptions().remove(productVariantOption);
		productVariantOption.setProductVariant(null);

		return productVariantOption;
	}

	public ProductVariant getProductVariant() {
		return this.productVariant;
	}

	public void setProductVariant(ProductVariant productVariant) {
		this.productVariant = productVariant;
	}

	public List<ProductVariant> getProductVariants() {
		return this.productVariants;
	}

	public void setProductVariants(List<ProductVariant> productVariants) {
		this.productVariants = productVariants;
	}

	public ProductVariant addProductVariant(ProductVariant productVariant) {
		getProductVariants().add(productVariant);
		productVariant.setProductVariant(this);

		return productVariant;
	}

	public ProductVariant removeProductVariant(ProductVariant productVariant) {
		getProductVariants().remove(productVariant);
		productVariant.setProductVariant(null);

		return productVariant;
	}

}
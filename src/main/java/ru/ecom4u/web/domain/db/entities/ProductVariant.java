package ru.ecom4u.web.domain.db.entities;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the product_variants database table.
 */
@Entity
@Table(name = "product_variants")
public class ProductVariant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Lob
    @Column(nullable = false)
    private String name;

    @Lob
    @Column(name = "default_value", nullable = true)
    private String defaultValue;

    @JsonIgnore
    // bi-directional many-to-one association to ProductVariantOption
    @OneToMany(mappedBy = "productVariant")
    private List<ProductVariantOption> productVariantOptions;

    @JsonIgnore
    // bi-directional many-to-one association to ProductVariant
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = true)
    private Unit unit;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
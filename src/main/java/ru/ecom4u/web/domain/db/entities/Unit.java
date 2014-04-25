package ru.ecom4u.web.domain.db.entities;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the units database table.
 */
@Entity
@Table(name = "units")
public class Unit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false, length = 16)
    private String abbr;

    @Column(nullable = true, length = 128)
    private String name;

    @Column(nullable = false)
    private int rate;

    @JsonIgnore
    //bi-directional many-to-one association to ProductProperty
    @OneToMany(mappedBy = "unit")
    private List<ProductProperty> productProperties;

    @JsonIgnore
    //bi-directional many-to-one association to ProductVariantOption
    @OneToMany(mappedBy = "unit")
    private List<ProductVariantOption> productVariantOptions;

    public Unit() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbbr() {
        return this.abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<ProductProperty> getProductProperties() {
        return this.productProperties;
    }

    public void setProductProperties(List<ProductProperty> productProperties) {
        this.productProperties = productProperties;
    }

    public ProductProperty addProductProperty(ProductProperty productProperty) {
        getProductProperties().add(productProperty);
        productProperty.setUnit(this);

        return productProperty;
    }

    public ProductProperty removeProductProperty(ProductProperty productProperty) {
        getProductProperties().remove(productProperty);
        productProperty.setUnit(null);

        return productProperty;
    }

    public List<ProductVariantOption> getProductVariantOptions() {
        return this.productVariantOptions;
    }

    public void setProductVariantOptions(List<ProductVariantOption> productVariantOptions) {
        this.productVariantOptions = productVariantOptions;
    }

    public ProductVariantOption addProductVariantOption(ProductVariantOption productVariantOption) {
        getProductVariantOptions().add(productVariantOption);
        productVariantOption.setUnit(this);

        return productVariantOption;
    }

    public ProductVariantOption removeProductVariantOption(ProductVariantOption productVariantOption) {
        getProductVariantOptions().remove(productVariantOption);
        productVariantOption.setUnit(null);

        return productVariantOption;
    }

}
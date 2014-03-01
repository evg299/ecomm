package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the product_categories database table.
 */
@Entity
@Table(name = "product_categories")
public class ProductCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Lob
    @Column(nullable = false)
    private String name;

    @Column(name = "url_name", nullable = false, length = 256)
    private String urlName;

    @Column(name = "pid")
    private Integer parentId;

    // bi-directional many-to-one association to Product
    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;

    @Transient
    private ProductCategory parentProductCategory;

    @Transient
    private List<ProductCategory> subProductCategories;


    public ProductCategory() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlName() {
        return this.urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    public ProductCategory getParentProductCategory() {
        return parentProductCategory;
    }

    public void setParentProductCategory(ProductCategory parentProductCategory) {
        this.parentProductCategory = parentProductCategory;
        if (null != parentProductCategory) {
            parentProductCategory.addSubProductCategory(this);
        }
    }

    public List<ProductCategory> getSubProductCategories() {
        if (null == subProductCategories)
            subProductCategories = new ArrayList<ProductCategory>();

        return subProductCategories;
    }

    public void setSubProductCategories(List<ProductCategory> subProductCategories) {
        this.subProductCategories = subProductCategories;
    }

    public ProductCategory addSubProductCategory(ProductCategory productCategory) {
        getSubProductCategories().add(productCategory);
        if (!this.equals(productCategory.getParentProductCategory()))
            productCategory.setParentProductCategory(this);

        return productCategory;
    }

    public ProductCategory removeSubProductCategory(ProductCategory productCategory) {
        getSubProductCategories().remove(productCategory);
        productCategory.setParentProductCategory(null);

        return productCategory;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Product addProduct(Product product) {
        getProducts().add(product);
        product.setProductCategory(this);

        return product;
    }

    public Product removeProduct(Product product) {
        getProducts().remove(product);
        product.setProductCategory(null);

        return product;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductCategory) {
            ProductCategory pc = (ProductCategory) obj;
            return pc.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
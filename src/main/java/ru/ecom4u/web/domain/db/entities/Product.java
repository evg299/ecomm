package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the products database table.
 */
@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    private int amt;

    @Lob
    private String description;

    @Lob
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String uuid;

    @Column(name = "date_of_receipt")
    private Timestamp dateOfReceipt;

    //bi-directional many-to-one association to Comment
    @OneToMany(mappedBy = "product")
    private List<Comment> comments;

    //bi-directional many-to-one association to ProductProperty
    @OneToMany(mappedBy = "product")
    private List<ProductProperty> productProperties;

    //bi-directional many-to-one association to Picture
    @ManyToOne
    @JoinColumn(name = "main_pict_id")
    private Picture picture;

    //bi-directional many-to-one association to Price
    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;

    //bi-directional many-to-one association to ProductCategory
    @ManyToOne
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;

    @OneToOne(mappedBy = "product")
    private AuxProductCount auxProductCount;

    @OneToOne(mappedBy = "product")
    private AuxProductRecommended auxProductRecommended;

    @ManyToMany
    @JoinTable(name = "product_pictures", joinColumns = {@JoinColumn(name = "product_id")}, inverseJoinColumns = {@JoinColumn(name = "picture_id")})
    private List<Picture> additionalPictures;

    public Product() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmt() {
        return this.amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Comment addComment(Comment comment) {
        getComments().add(comment);
        comment.setProduct(this);

        return comment;
    }

    public Comment removeComment(Comment comment) {
        getComments().remove(comment);
        comment.setProduct(null);

        return comment;
    }

    public List<ProductProperty> getProductProperties() {
        return this.productProperties;
    }

    public void setProductProperties(List<ProductProperty> productProperties) {
        this.productProperties = productProperties;
    }

    public ProductProperty addProductProperty(ProductProperty productProperty) {
        getProductProperties().add(productProperty);
        productProperty.setProduct(this);

        return productProperty;
    }

    public ProductProperty removeProductProperty(ProductProperty productProperty) {
        getProductProperties().remove(productProperty);
        productProperty.setProduct(null);

        return productProperty;
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Price getPrice() {
        return this.price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Timestamp getDateOfReceipt() {
        return dateOfReceipt;
    }

    public void setDateOfReceipt(Timestamp dateOfReceipt) {
        this.dateOfReceipt = dateOfReceipt;
    }

    public AuxProductCount getAuxProductCount() {
        return auxProductCount;
    }

    public void setAuxProductCount(AuxProductCount auxProductCount) {
        this.auxProductCount = auxProductCount;
    }

    public AuxProductRecommended getAuxProductRecommended() {
        return auxProductRecommended;
    }

    public void setAuxProductRecommended(AuxProductRecommended auxProductRecommended) {
        this.auxProductRecommended = auxProductRecommended;
    }

    public List<Picture> getAdditionalPictures() {
        return additionalPictures;
    }

    public void setAdditionalPictures(List<Picture> additionalPictures) {
        this.additionalPictures = additionalPictures;
    }
}
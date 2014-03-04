package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Evgeny(e299792459@gmail.com) on 04.03.14.
 */
@Entity
@Table(name = "aux_product_recommended")
public class AuxProductRecommended implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

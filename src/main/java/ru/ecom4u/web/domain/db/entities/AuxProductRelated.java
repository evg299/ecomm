package ru.ecom4u.web.domain.db.entities;

/**
 * Created by Evgeny(e299792459@gmail.com) on 24.03.14.
 */

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "aux_product_related")
public class AuxProductRelated implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "product_id_1", nullable = false)
    private Product product1;

    @OneToOne
    @JoinColumn(name = "product_id_2", nullable = false)
    private Product product2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct1() {
        return product1;
    }

    public void setProduct1(Product product1) {
        this.product1 = product1;
    }

    public Product getProduct2() {
        return product2;
    }

    public void setProduct2(Product product2) {
        this.product2 = product2;
    }
}

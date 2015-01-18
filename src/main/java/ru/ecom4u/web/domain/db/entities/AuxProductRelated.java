package ru.ecom4u.web.domain.db.entities;

/**
 * Created by Evgeny(e299792459@gmail.com) on 24.03.14.
 */

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "aux_product_related")
public class AuxProductRelated implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Embeddable
    public static class Id implements Serializable
    {
        @Column(name = "product_id_1")
        private long productId1;
        @Column(name = "product_id_2")
        private long productId2;

        public long getProductId1()
        {
            return productId1;
        }

        public void setProductId1(long productId1)
        {
            this.productId1 = productId1;
        }

        public long getProductId2()
        {
            return productId2;
        }

        public void setProductId2(long productId2)
        {
            this.productId2 = productId2;
        }
    }

    @EmbeddedId
    private Id id;

    @OneToOne
    @JoinColumn(name = "product_id_1", nullable = false, insertable = false, updatable = false)
    private Product product1;

    @OneToOne
    @JoinColumn(name = "product_id_2", nullable = false, insertable = false, updatable = false)
    private Product product2;

    public Id getId()
    {
        return id;
    }

    public void setId(Id id)
    {
        this.id = id;
    }

    public Product getProduct1()
    {
        return product1;
    }

    public Product getProduct2()
    {
        return product2;
    }

}

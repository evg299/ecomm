package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the orders database table.
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false)
    private String uuid;

    @Lob
    private String comment;

    @Column(name = "creation_date", nullable = false)
    private Timestamp creationDate;

    //bi-directional many-to-one association to Delivery
    @OneToOne(mappedBy = "order")
    private Delivery delivery;

    @Column(nullable = false)
    private Double coast;

    @Column(name = "sum_coast", nullable = false)
    private Double sumCoast;

    //bi-directional many-to-one association to OrderStatus
    @ManyToOne
    @JoinColumn(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;

    //bi-directional many-to-one association to Person
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    private List<Product> products;

    public Order()
    {
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getComment()
    {
        return this.comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public Timestamp getCreationDate()
    {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate)
    {
        this.creationDate = creationDate;
    }

    public Delivery getDelivery()
    {
        return this.delivery;
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery = delivery;
    }

    public OrderStatus getOrderStatus()
    {
        return this.orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    public Person getPerson()
    {
        return this.person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public Double getCoast()
    {
        return coast;
    }

    public void setCoast(Double coast)
    {
        this.coast = coast;
    }

    public Double getSumCoast()
    {
        return sumCoast;
    }

    public void setSumCoast(Double sumCoast)
    {
        this.sumCoast = sumCoast;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public void setProducts(List<Product> products)
    {
        this.products = products;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
}
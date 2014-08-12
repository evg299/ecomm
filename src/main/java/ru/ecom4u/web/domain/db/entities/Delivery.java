package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the deliveries database table.
 */
@Entity
@Table(name = "deliveries")
public class Delivery implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(name = "weight", nullable = false)
    private double weight;

    //bi-directional many-to-one association to Address
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(nullable = false)
    private Double coast;

    //bi-directional many-to-one association to DeliveryType
    @Column(name = "delivery_class", nullable = false)
    private String deliveryClass;

    //bi-directional many-to-one association to Order
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    //bi-directional many-to-one association to Person
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    public Delivery()
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

    public double getWeight()
    {
        return this.weight;
    }

    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public Address getAddress()
    {
        return this.address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public Order getOrder()
    {
        return this.order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
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

    public String getDeliveryClass()
    {
        return deliveryClass;
    }

    public void setDeliveryClass(String deliveryClass)
    {
        this.deliveryClass = deliveryClass;
    }
}
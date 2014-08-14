package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the orders database table.
 * 
 */
@Entity
<<<<<<< HEAD
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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "payment_class", nullable = false)
    private String paymentClass;

    //bi-directional many-to-one association to Person
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderProduct> orderProducts;

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

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getPaymentClass()
    {
        return paymentClass;
    }

    public void setPaymentClass(String paymentClass)
    {
        this.paymentClass = paymentClass;
    }

    public List<OrderProduct> getOrderProducts()
    {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts)
    {
        this.orderProducts = orderProducts;
    }
=======
@Table(name="orders")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	private String comment;

	@Column(name="creation_date", nullable=false)
	private Timestamp creationDate;

	//bi-directional many-to-one association to Delivery
	@OneToMany(mappedBy="order")
	private List<Delivery> deliveries;

	//bi-directional many-to-one association to Delivery
	@ManyToOne
	@JoinColumn(name="delivery_id", nullable=false)
	private Delivery delivery;

	//bi-directional many-to-one association to OrderStatus
	@ManyToOne
	@JoinColumn(name="order_status_id", nullable=false)
	private OrderStatus orderStatus;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public Order() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public List<Delivery> getDeliveries() {
		return this.deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public Delivery addDelivery(Delivery delivery) {
		getDeliveries().add(delivery);
		delivery.setOrder(this);

		return delivery;
	}

	public Delivery removeDelivery(Delivery delivery) {
		getDeliveries().remove(delivery);
		delivery.setOrder(null);

		return delivery;
	}

	public Delivery getDelivery() {
		return this.delivery;
	}

	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

>>>>>>> f13c0105780753766da1b5b461e5ac3f65071602
}
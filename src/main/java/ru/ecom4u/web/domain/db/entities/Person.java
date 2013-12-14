package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the persons database table.
 * 
 */
@Entity
@Table(name="persons")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="birth_date")
	private Date birthDate;

	@Lob
	private String fname;

	private Boolean gender;

	@Lob
	private String lname;

	@Lob
	private String mname;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="person")
	private List<Comment> comments;

	//bi-directional many-to-one association to Delivery
	@OneToMany(mappedBy="person")
	private List<Delivery> deliveries;

	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="person")
	private List<Order> orders;

	//bi-directional many-to-one association to PersonContact
	@OneToMany(mappedBy="person")
	private List<PersonContact> personContacts;

	//bi-directional many-to-one association to Picture
	@ManyToOne
	@JoinColumn(name="picture_id")
	private Picture picture;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="person")
	private List<User> users;

	public Person() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMname() {
		return this.mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setPerson(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setPerson(null);

		return comment;
	}

	public List<Delivery> getDeliveries() {
		return this.deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public Delivery addDelivery(Delivery delivery) {
		getDeliveries().add(delivery);
		delivery.setPerson(this);

		return delivery;
	}

	public Delivery removeDelivery(Delivery delivery) {
		getDeliveries().remove(delivery);
		delivery.setPerson(null);

		return delivery;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setPerson(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setPerson(null);

		return order;
	}

	public List<PersonContact> getPersonContacts() {
		return this.personContacts;
	}

	public void setPersonContacts(List<PersonContact> personContacts) {
		this.personContacts = personContacts;
	}

	public PersonContact addPersonContact(PersonContact personContact) {
		getPersonContacts().add(personContact);
		personContact.setPerson(this);

		return personContact;
	}

	public PersonContact removePersonContact(PersonContact personContact) {
		getPersonContacts().remove(personContact);
		personContact.setPerson(null);

		return personContact;
	}

	public Picture getPicture() {
		return this.picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setPerson(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setPerson(null);

		return user;
	}

}
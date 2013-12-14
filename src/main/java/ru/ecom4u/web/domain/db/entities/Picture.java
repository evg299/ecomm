package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pictures database table.
 * 
 */
@Entity
@Table(name="pictures")
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String big;

	@Lob
	@Column(nullable=false)
	private String medium;

	@Lob
	@Column(nullable=false)
	private String small;

	@Lob
	private String title;

	@Column(name="url_name", nullable=false, length=128)
	private String urlName;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="picture")
	private List<Person> persons;

	//bi-directional many-to-one association to Product
	@OneToMany(mappedBy="picture")
	private List<Product> products;

	//bi-directional many-to-one association to StaticPage
	@OneToMany(mappedBy="picture")
	private List<StaticPage> staticPages;

	public Picture() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBig() {
		return this.big;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public String getMedium() {
		return this.medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}

	public String getSmall() {
		return this.small;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrlName() {
		return this.urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setPicture(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setPicture(null);

		return person;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setPicture(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setPicture(null);

		return product;
	}

	public List<StaticPage> getStaticPages() {
		return this.staticPages;
	}

	public void setStaticPages(List<StaticPage> staticPages) {
		this.staticPages = staticPages;
	}

	public StaticPage addStaticPage(StaticPage staticPage) {
		getStaticPages().add(staticPage);
		staticPage.setPicture(this);

		return staticPage;
	}

	public StaticPage removeStaticPage(StaticPage staticPage) {
		getStaticPages().remove(staticPage);
		staticPage.setPicture(null);

		return staticPage;
	}

}
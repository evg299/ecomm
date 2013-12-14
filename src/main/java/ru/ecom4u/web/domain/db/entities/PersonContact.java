package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the person_contacts database table.
 * 
 */
@Entity
@Table(name="person_contacts")
public class PersonContact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	@Column(nullable=false)
	private String value;

	//bi-directional many-to-one association to ContactType
	@ManyToOne
	@JoinColumn(name="contact_type_id", nullable=false)
	private ContactType contactType;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

	public PersonContact() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public ContactType getContactType() {
		return this.contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}
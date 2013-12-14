package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the contact_types database table.
 * 
 */
@Entity
@Table(name="contact_types")
public class ContactType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Lob
	private String desc;

	@Column(nullable=false, length=512)
	private String name;

	//bi-directional many-to-one association to PersonContact
	@OneToMany(mappedBy="contactType")
	private List<PersonContact> personContacts;

	public ContactType() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PersonContact> getPersonContacts() {
		return this.personContacts;
	}

	public void setPersonContacts(List<PersonContact> personContacts) {
		this.personContacts = personContacts;
	}

	public PersonContact addPersonContact(PersonContact personContact) {
		getPersonContacts().add(personContact);
		personContact.setContactType(this);

		return personContact;
	}

	public PersonContact removePersonContact(PersonContact personContact) {
		getPersonContacts().remove(personContact);
		personContact.setContactType(null);

		return personContact;
	}

}
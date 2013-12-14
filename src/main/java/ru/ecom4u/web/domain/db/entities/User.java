package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="confirmed_flag", nullable=false)
	private Boolean confirmedFlag;

	@Column(nullable=false, length=256)
	private String email;

	@Column(name="hash_passord", nullable=false, length=256)
	private String hashPassord;

	@Column(nullable=false, length=32)
	private String login;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="user")
	private List<Person> persons;

	//bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name="person_id")
	private Person person;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id", nullable=false)
	private Role role;

	public User() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Boolean getConfirmedFlag() {
		return this.confirmedFlag;
	}

	public void setConfirmedFlag(Boolean confirmedFlag) {
		this.confirmedFlag = confirmedFlag;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHashPassord() {
		return this.hashPassord;
	}

	public void setHashPassord(String hashPassord) {
		this.hashPassord = hashPassord;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setUser(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setUser(null);

		return person;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
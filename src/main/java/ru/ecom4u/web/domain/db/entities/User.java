package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(name = "confirmed_flag", nullable = false)
	private Boolean confirmedFlag;

	@Column(nullable = false, length = 256)
	private String email;

	@Column(name = "hash_passord", nullable = false, length = 256)
	private String hashPassord;

	@Column(nullable = false, length = 32)
	private String login;

	@OneToOne(mappedBy = "user")
	private Person person;

	// bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
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
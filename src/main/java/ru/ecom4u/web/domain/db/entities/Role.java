package ru.ecom4u.web.domain.db.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum MainRoles {
		GUEST, USER, MANAGER, ADMIN
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;

	@Column(nullable = false, length = 32)
	private String name;

	// bi-directional many-to-one association to User
	@OneToMany(mappedBy = "role")
	private List<User> users;

	public Role() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setRole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setRole(null);

		return user;
	}

}
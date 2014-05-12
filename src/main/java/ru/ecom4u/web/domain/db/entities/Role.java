package ru.ecom4u.web.domain.db.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the roles database table.
 */
@Entity
@Table(name = "roles")
public class Role implements Serializable, GrantedAuthority {
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

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<User> users;

    public Role() {
    }

    @Override
    public String getAuthority() {
        return name;
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

}
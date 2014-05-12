package ru.ecom4u.web.domain.db.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the users database table.
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

    @Column(name = "confirmed_code", length = 256)
    private String confirmedCode;

    @Column(nullable = false, length = 256, unique = true)
    private String email;

    @Column(name = "hash_passord", nullable = false, length = 256)
    private String hashPassord;

    @Column(nullable = false, length = 32, unique = true)
    private String login;

    @OneToOne(mappedBy = "user")
    private Person person;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "role_id", nullable = false)})
    private List<Role> roles;

    public User() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfirmedCode() {
        return confirmedCode;
    }

    public void setConfirmedCode(String confirmedCode) {
        this.confirmedCode = confirmedCode;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
package ru.ecom4u.web.domain.db.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The persistent class for the users database table.
 */
@Entity
@Table(name = "users")
public class User implements Serializable, UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(name = "confirmed_flag", nullable = false)
    private Boolean confirmedFlag;

    @Column(name = "confirmed_code", length = 256)
    private String confirmedCode;

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

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // UserDetails implementation

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(1);

        final User user = this;
        grantedAuthorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().getName();
            }
        });

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.hashPassord;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
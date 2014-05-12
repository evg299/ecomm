package ru.ecom4u.web.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import ru.ecom4u.web.domain.db.entities.Role;
import ru.ecom4u.web.domain.db.entities.User;

import java.util.Collection;
import java.util.List;

/**
 * Created by Evgeny on 12.05.14.
 */
public class UserDetailsWrapper implements UserDetails {
    private User user;
    private List<Role> roles;

    public UserDetailsWrapper(User user) {
        this.user = user;
        this.roles = user.getRoles();
    }

    public UserDetailsWrapper(User user, List<Role> roles) {
        this.user = user;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] rolesArr = new String[this.roles.size()];
        int idx = 0;
        for (Role role : this.roles) {
            rolesArr[idx++] = "ROLE_" + role.getAuthority();
        }
        return AuthorityUtils.createAuthorityList(rolesArr);
    }

    @Override
    public String getPassword() {
        return user.getHashPassord();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

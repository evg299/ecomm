package ru.ecom4u.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.UserService;
import ru.ecom4u.web.services.HasherService;

import java.util.Collection;

/**
 * Created by Evgeny on 01.05.14.
 */
@Component
public class DBAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private HasherService hasherService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userService.getByEmail(name);

        if (null == user) {
            throw new BadCredentialsException("Username not found.");
        }

        String hash = hasherService.calculateHash(password);
        if (!hash.equals(user.getHashPassord())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + user.getRole().getAuthority());

        return new UsernamePasswordAuthenticationToken(name, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

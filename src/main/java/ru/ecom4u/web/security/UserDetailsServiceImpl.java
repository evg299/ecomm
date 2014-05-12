package ru.ecom4u.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.ecom4u.web.domain.db.entities.User;
import ru.ecom4u.web.domain.db.services.UserService;

/**
 * Created by Evgeny on 06.05.2014.
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.getByEmailOrLogin(name);
        return new UserDetailsWrapper(user);
    }
}

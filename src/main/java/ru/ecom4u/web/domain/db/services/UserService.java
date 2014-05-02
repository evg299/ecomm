package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService extends AbstractService {

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        Session session = getCurrentSession();
        return (User) session.createCriteria(User.class).add(Restrictions.eq("email", email).ignoreCase())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public User getById(Integer id) {
        Session session = getCurrentSession();
        return (User) session.get(User.class, id);
    }

    @Transactional
    public void save(User user) {
        Session session = getCurrentSession();
        session.save(user);
    }
}

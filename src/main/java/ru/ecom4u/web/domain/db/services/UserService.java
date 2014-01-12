package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ecom4u.web.domain.db.entities.User;

@Service
public class UserService extends AbstractService {

	@Transactional(readOnly = true)
	public User getByEmail(String email) {
		Session session = getCurrentSession();
		return (User) session.createCriteria(User.class)
				.add(Restrictions.eq("email", email).ignoreCase())
				.uniqueResult();
	}

}

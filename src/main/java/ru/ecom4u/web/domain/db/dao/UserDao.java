package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.ecom4u.web.domain.db.entities.User;

@Repository
@Transactional
public class UserDao extends AbstractDao {

	public List<User> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(User.class).list();
	}

	public User getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (User) hSession.get(User.class, id);
	}

	public User getByEmail(String email) {
		Session hSession = sessionFactory.getCurrentSession();
		return (User) hSession.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}

	/*********************/
	public void save(User user) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(user);
	}

	public void update(User user) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(user);
	}

	public void saveOrUpdate(User user) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(user);
	}

	public void delete(User user) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(user);

	}

}

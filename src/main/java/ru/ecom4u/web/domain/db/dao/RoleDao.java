package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Role;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RoleDao extends AbstractDao {

	public List<Role> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Role.class).list();
	}

	public Role getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Role) hSession.get(Role.class, id);
	}

	/*********************/
	public void save(Role role) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(role);
	}

	public void update(Role role) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(role);
	}

	public void saveOrUpdate(Role role) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(role);
	}

	public void delete(Role role) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(role);

	}

}

package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.PersonContact;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PersonContactDao extends AbstractDao {

	public List<PersonContact> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(PersonContact.class).list();
	}

	public PersonContact getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (PersonContact) hSession.get(PersonContact.class, id);
	}

	/*********************/
	public void save(PersonContact personContact) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(personContact);
	}

	public void update(PersonContact personContact) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(personContact);
	}

	public void saveOrUpdate(PersonContact personContact) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(personContact);
	}

	public void delete(PersonContact personContact) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(personContact);

	}

}

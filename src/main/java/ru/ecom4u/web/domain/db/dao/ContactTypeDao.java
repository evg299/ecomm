package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.ContactType;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ContactTypeDao extends AbstractDao {

	public List<ContactType> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(ContactType.class).list();
	}

	public ContactType getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (ContactType) hSession.get(ContactType.class, id);
	}

	/*********************/
	public void save(ContactType contactType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(contactType);
	}

	public void update(ContactType contactType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(contactType);
	}

	public void saveOrUpdate(ContactType contactType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(contactType);
	}

	public void delete(ContactType contactType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(contactType);

	}

}

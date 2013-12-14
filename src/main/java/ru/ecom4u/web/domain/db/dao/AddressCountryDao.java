package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.AddressCountry;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AddressCountryDao extends AbstractDao {

	public List<AddressCountry> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(AddressCountry.class).list();
	}

	public AddressCountry getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (AddressCountry) hSession.get(AddressCountry.class, id);
	}

	/*********************/
	public void save(AddressCountry addressCountry) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(addressCountry);
	}

	public void update(AddressCountry addressCountry) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(addressCountry);
	}

	public void saveOrUpdate(AddressCountry addressCountry) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(addressCountry);
	}

	public void delete(AddressCountry addressCountry) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(addressCountry);

	}

}

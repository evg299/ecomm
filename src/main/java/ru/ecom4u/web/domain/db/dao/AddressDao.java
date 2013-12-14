package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Address;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AddressDao extends AbstractDao {

	public List<Address> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Address.class).list();
	}

	public Address getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Address) hSession.get(Address.class, id);
	}

	/*********************/
	public void save(Address address) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(address);
	}

	public void update(Address address) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(address);
	}

	public void saveOrUpdate(Address address) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(address);
	}

	public void delete(Address address) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(address);

	}

}

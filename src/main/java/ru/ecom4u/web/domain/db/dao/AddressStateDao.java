package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.AddressState;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AddressStateDao extends AbstractDao {

	public List<AddressState> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(AddressState.class).list();
	}

	public AddressState getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (AddressState) hSession.get(AddressState.class, id);
	}

	/*********************/
	public void save(AddressState addressState) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(addressState);
	}

	public void update(AddressState addressState) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(addressState);
	}

	public void saveOrUpdate(AddressState addressState) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(addressState);
	}

	public void delete(AddressState addressState) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(addressState);

	}

}

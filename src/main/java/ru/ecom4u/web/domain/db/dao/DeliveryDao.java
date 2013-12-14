package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Delivery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DeliveryDao extends AbstractDao {

	public List<Delivery> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Delivery.class).list();
	}

	public Delivery getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Delivery) hSession.get(Delivery.class, id);
	}

	/*********************/
	public void save(Delivery delivery) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(delivery);
	}

	public void update(Delivery delivery) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(delivery);
	}

	public void saveOrUpdate(Delivery delivery) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(delivery);
	}

	public void delete(Delivery delivery) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(delivery);

	}

}

package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.DeliveryType;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class DeliveryTypeDao extends AbstractDao {

	public List<DeliveryType> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(DeliveryType.class).list();
	}

	public DeliveryType getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (DeliveryType) hSession.get(DeliveryType.class, id);
	}

	/*********************/
	public void save(DeliveryType deliveryType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(deliveryType);
	}

	public void update(DeliveryType deliveryType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(deliveryType);
	}

	public void saveOrUpdate(DeliveryType deliveryType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(deliveryType);
	}

	public void delete(DeliveryType deliveryType) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(deliveryType);

	}

}

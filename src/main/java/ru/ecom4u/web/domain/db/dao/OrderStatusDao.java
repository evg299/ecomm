package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.OrderStatus;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderStatusDao extends AbstractDao {

	public List<OrderStatus> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(OrderStatus.class).list();
	}

	public OrderStatus getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (OrderStatus) hSession.get(OrderStatus.class, id);
	}

	/*********************/
	public void save(OrderStatus orderStatus) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(orderStatus);
	}

	public void update(OrderStatus orderStatus) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(orderStatus);
	}

	public void saveOrUpdate(OrderStatus orderStatus) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(orderStatus);
	}

	public void delete(OrderStatus orderStatus) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(orderStatus);

	}

}

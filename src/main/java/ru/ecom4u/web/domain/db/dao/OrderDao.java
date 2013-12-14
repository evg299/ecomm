package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Order;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderDao extends AbstractDao {

	public List<Order> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Order.class).list();
	}

	public Order getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Order) hSession.get(Order.class, id);
	}

	/*********************/
	public void save(Order order) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(order);
	}

	public void update(Order order) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(order);
	}

	public void saveOrUpdate(Order order) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(order);
	}

	public void delete(Order order) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(order);

	}

}

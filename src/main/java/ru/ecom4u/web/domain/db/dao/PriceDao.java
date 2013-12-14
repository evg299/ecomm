package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Price;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PriceDao extends AbstractDao {

	public List<Price> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Price.class).list();
	}

	public Price getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Price) hSession.get(Price.class, id);
	}

	/*********************/
	public void save(Price price) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(price);
	}

	public void update(Price price) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(price);
	}

	public void saveOrUpdate(Price price) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(price);
	}

	public void delete(Price price) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(price);

	}

}

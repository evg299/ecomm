package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.Product;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDao extends AbstractDao {

	public List<Product> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(Product.class).list();
	}

	public Product getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (Product) hSession.get(Product.class, id);
	}

	/*********************/
	public void save(Product product) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(product);
	}

	public void update(Product product) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(product);
	}

	public void saveOrUpdate(Product product) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(product);
	}

	public void delete(Product product) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(product);

	}

}

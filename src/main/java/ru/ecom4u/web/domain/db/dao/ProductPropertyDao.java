package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.ProductProperty;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductPropertyDao extends AbstractDao {

	public List<ProductProperty> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(ProductProperty.class).list();
	}

	public ProductProperty getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (ProductProperty) hSession.get(ProductProperty.class, id);
	}

	/*********************/
	public void save(ProductProperty productProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(productProperty);
	}

	public void update(ProductProperty productProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(productProperty);
	}

	public void saveOrUpdate(ProductProperty productProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(productProperty);
	}

	public void delete(ProductProperty productProperty) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(productProperty);

	}

}

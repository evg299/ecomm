package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.ProductVariant;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductVariantDao extends AbstractDao {

	public List<ProductVariant> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(ProductVariant.class).list();
	}

	public ProductVariant getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (ProductVariant) hSession.get(ProductVariant.class, id);
	}

	/*********************/
	public void save(ProductVariant productVariant) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(productVariant);
	}

	public void update(ProductVariant productVariant) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(productVariant);
	}

	public void saveOrUpdate(ProductVariant productVariant) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(productVariant);
	}

	public void delete(ProductVariant productVariant) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(productVariant);

	}

}

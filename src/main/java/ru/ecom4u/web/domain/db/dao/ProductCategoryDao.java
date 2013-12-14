package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.ProductCategory;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductCategoryDao extends AbstractDao {

	public List<ProductCategory> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(ProductCategory.class).list();
	}

	public ProductCategory getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (ProductCategory) hSession.get(ProductCategory.class, id);
	}

	/*********************/
	public void save(ProductCategory productCategory) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(productCategory);
	}

	public void update(ProductCategory productCategory) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(productCategory);
	}

	public void saveOrUpdate(ProductCategory productCategory) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(productCategory);
	}

	public void delete(ProductCategory productCategory) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(productCategory);

	}

}

package ru.ecom4u.web.domain.db.dao;

import java.util.List;

import ru.ecom4u.web.domain.db.entities.ProductVariantOption;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductVariantOptionDao extends AbstractDao {

	public List<ProductVariantOption> getAll() {
		Session hSession = sessionFactory.getCurrentSession();
		return hSession.createCriteria(ProductVariantOption.class).list();
	}

	public ProductVariantOption getById(int id) {
		Session hSession = sessionFactory.getCurrentSession();
		return (ProductVariantOption) hSession.get(ProductVariantOption.class, id);
	}

	/*********************/
	public void save(ProductVariantOption productVariantOption) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.save(productVariantOption);
	}

	public void update(ProductVariantOption productVariantOption) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.update(productVariantOption);
	}

	public void saveOrUpdate(ProductVariantOption productVariantOption) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.saveOrUpdate(productVariantOption);
	}

	public void delete(ProductVariantOption productVariantOption) {
		Session hSession = sessionFactory.getCurrentSession();
		hSession.delete(productVariantOption);

	}

}

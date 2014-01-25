package ru.ecom4u.web.domain.db.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import ru.ecom4u.web.domain.db.entities.Product;

public class ProductService extends AbstractService {

	public List<Product> getProducts(int start, int lenght) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.setFirstResult(start);
		criteria.setMaxResults(lenght);
		return criteria.list();
	}
}

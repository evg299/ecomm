package ru.ecom4u.web.domain.db.services;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;
import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Product;

@Service
public class ProductService extends AbstractService {

    @Transactional(readOnly = true)
	public List<Product> getProducts(int start, int lenght) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(Product.class);
		criteria.setFirstResult(start);
		criteria.setMaxResults(lenght);
		return criteria.list();
	}

    @Transactional(readOnly = true)
    public Long countProducts(){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }
}

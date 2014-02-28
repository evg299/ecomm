package ru.ecom4u.web.domain.db.services;

import java.util.List;

import com.sun.org.apache.regexp.internal.recompile;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.controllers.dto.QueryResult;
import ru.ecom4u.web.controllers.reqvalues.CategoryOrder;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.ProductCategory;

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

    @Transactional(readOnly = true)
    public QueryResult<Product> getProductsOfCategory(ProductCategory category, int start, int lenght, CategoryOrder order) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class, "product");
        criteria.add(Restrictions.eq("productCategory", category));

        Integer countAll = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

        criteria.setProjection(null);

        if(CategoryOrder.price == order){
            criteria.createAlias("product.price", "price");
            criteria.setResultTransformer(Criteria.ROOT_ENTITY);
            criteria.addOrder(Order.asc("price.value"));
        }
        if(CategoryOrder.receipt == order){
            criteria.addOrder(Order.asc("dateOfReceipt"));
        }

        criteria.setFirstResult(start);
        criteria.setMaxResults(lenght);

        List<Product> products = criteria.list();

        return new QueryResult<Product>(countAll, products);
    }


}

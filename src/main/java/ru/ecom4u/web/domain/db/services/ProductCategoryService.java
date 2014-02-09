package ru.ecom4u.web.domain.db.services;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.ProductCategory;

import java.util.List;

@Service
public class ProductCategoryService extends AbstractService {

    @Transactional(readOnly = true)
    public ProductCategory getById(Integer id) {
        Session session = getCurrentSession();
        return (ProductCategory) session.get(ProductCategory.class, id);
    }

    @Transactional(readOnly = true)
    public ProductCategory getByUrlName(String urlName) {
        Session session = getCurrentSession();
        return (ProductCategory) session.createCriteria(ProductCategory.class).add(Restrictions.eq("urlName", urlName))
                .addOrder(Order.asc("name")).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> getAll() {
        Session session = getCurrentSession();
        return session.createCriteria(ProductCategory.class).list();
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> getRootProductCategories() {
        Session session = getCurrentSession();
        return session.createCriteria(ProductCategory.class).add(Restrictions.isNull("productCategory"))
                .addOrder(Order.asc("name")).list();
    }

    @Transactional(readOnly = true)
    public List<ProductCategory> getSubProductCategories(ProductCategory parentProductCategory) {
        Session session = getCurrentSession();
        return session.createCriteria(ProductCategory.class)
                .add(Restrictions.eq("productCategory", parentProductCategory)).addOrder(Order.asc("name")).list();
    }


}

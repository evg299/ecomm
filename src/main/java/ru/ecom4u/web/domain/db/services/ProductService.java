package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.controllers.dto.QueryResult;
import ru.ecom4u.web.controllers.reqvalues.CategoryOrder;
import ru.ecom4u.web.domain.db.entities.AuxProductCount;
import ru.ecom4u.web.domain.db.entities.AuxProductRecommended;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.ProductCategory;

import java.util.List;

@Service
public class ProductService extends AbstractService {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Transactional(readOnly = true)
    public List<Product> getProducts(int start, int lenght) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        criteria.setFirstResult(start);
        criteria.setMaxResults(lenght);
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public Long countProducts() {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class);
        return (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<Product> getProducts(List<Integer> ids) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class, "product");
        criteria.add(Restrictions.in("id", ids));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public QueryResult<Product> getProductsOfCategory(ProductCategory category, int start, int lenght, CategoryOrder order) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Product.class, "product");

        List<ProductCategory> chCategories = productCategoryService.expandBranch(category);

        criteria.add(Restrictions.in("productCategory", chCategories));

        Integer countAll = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

        criteria.setProjection(null);

        if (CategoryOrder.price == order) {
            criteria.createAlias("product.price", "price");
            criteria.setResultTransformer(Criteria.ROOT_ENTITY);
            criteria.addOrder(Order.asc("price.value"));
        }
        if (CategoryOrder.receipt == order) {
            criteria.addOrder(Order.asc("dateOfReceipt"));
        }

        criteria.setFirstResult(start);
        criteria.setMaxResults(lenght);

        List<Product> products = criteria.list();

        return new QueryResult<Product>(countAll, products);
    }

    @Transactional
    public void markSell(Product product) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(AuxProductCount.class, "auxProductCount");
        criteria.add(Restrictions.eq("product", product));
        List<AuxProductCount> auxProductCountList = criteria.list();

        AuxProductCount auxProductCount;
        if (0 == auxProductCountList.size()) {
            auxProductCount = new AuxProductCount();
            auxProductCount.setSellCount(1);
            auxProductCount.setCardCount(1);
            auxProductCount.setViewCount(1);
        } else {
            auxProductCount = auxProductCountList.get(0);
        }

        auxProductCount.setProduct(product);
        auxProductCount.setSellCount(auxProductCount.getSellCount() + 1);
        this.saveOrUpdate(auxProductCount);
    }

    @Transactional
    public void markCard(Product product) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(AuxProductCount.class, "auxProductCount");
        criteria.add(Restrictions.eq("product", product));
        List<AuxProductCount> auxProductCountList = criteria.list();

        AuxProductCount auxProductCount;
        if (0 == auxProductCountList.size()) {
            auxProductCount = new AuxProductCount();
            auxProductCount.setSellCount(0);
            auxProductCount.setCardCount(1);
            auxProductCount.setViewCount(1);
        } else {
            auxProductCount = auxProductCountList.get(0);
        }

        auxProductCount.setProduct(product);
        auxProductCount.setCardCount(auxProductCount.getCardCount() + 1);
        this.saveOrUpdate(auxProductCount);
    }

    @Transactional
    public void markView(Product product) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(AuxProductCount.class, "auxProductCount");
        criteria.add(Restrictions.eq("product", product));
        List<AuxProductCount> auxProductCountList = criteria.list();

        AuxProductCount auxProductCount;
        if (0 == auxProductCountList.size()) {
            auxProductCount = new AuxProductCount();
            auxProductCount.setSellCount(0);
            auxProductCount.setCardCount(0);
            auxProductCount.setViewCount(1);
        } else {
            auxProductCount = auxProductCountList.get(0);
        }

        auxProductCount.setProduct(product);
        auxProductCount.setViewCount(auxProductCount.getViewCount() + 1);
        this.saveOrUpdate(auxProductCount);
    }

    @Transactional
    public void markRecommended(Product product) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(AuxProductRecommended.class, "auxProductRecommended");
        criteria.add(Restrictions.eq("product", product));
        List<AuxProductRecommended> auxProductRecommendedList = criteria.list();

        if (0 == auxProductRecommendedList.size()) {
            AuxProductRecommended auxProductRecommended = new AuxProductRecommended();
            auxProductRecommended.setProduct(product);
            this.save(auxProductRecommended);
        }
    }
}

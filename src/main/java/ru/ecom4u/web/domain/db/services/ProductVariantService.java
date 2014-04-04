package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Product;
import ru.ecom4u.web.domain.db.entities.ProductVariant;
import ru.ecom4u.web.domain.db.entities.ProductVariantOption;

import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 01.04.14.
 */
@Service
public class ProductVariantService extends AbstractService {

    @Transactional(readOnly = true)
    public List<ProductVariant> getNotSelectByProduct(Product product){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(ProductVariant.class);
        criteria.add(Restrictions.eq("product", product));
        criteria.add(Restrictions.isNotNull("unit"));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<ProductVariant> getSelectByProduct(Product product){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(ProductVariant.class);
        criteria.add(Restrictions.eq("product", product));
        criteria.add(Restrictions.isNull("unit"));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<ProductVariantOption> getOptionsByProductVariant(ProductVariant productVariant) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(ProductVariantOption.class);
        criteria.add(Restrictions.eq("productVariant", productVariant));
        return criteria.list();
    }

    @Transactional(readOnly = true)
    public List<ProductVariantOption> getOptionsByProductVariantList(List<ProductVariant> productVariants) {
        if(0 == productVariants.size())
            return null;

        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(ProductVariantOption.class);
        criteria.add(Restrictions.in("productVariant", productVariants));
        return criteria.list();
    }
}

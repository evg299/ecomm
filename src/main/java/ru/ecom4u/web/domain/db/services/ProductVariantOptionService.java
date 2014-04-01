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
public class ProductVariantOptionService extends AbstractService {

    @Transactional(readOnly = true)
    public List<ProductVariantOption> getByProductVariant(ProductVariant productVariant) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(ProductVariantOption.class);
        criteria.add(Restrictions.eq("productVariant", productVariant));
        return criteria.list();
    }
}

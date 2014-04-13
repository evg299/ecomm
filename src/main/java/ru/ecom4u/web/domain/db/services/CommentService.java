package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Comment;
import ru.ecom4u.web.domain.db.entities.Product;

import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 13.04.14.
 */
@Service
public class CommentService extends AbstractService {

    @Transactional(readOnly = true)
    public List<Comment> getByProduct(Product product) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Comment.class);
        criteria.add(Restrictions.eq("product", product));
        criteria.addOrder(Order.asc("creationDate"));
        return criteria.list();
    }
}

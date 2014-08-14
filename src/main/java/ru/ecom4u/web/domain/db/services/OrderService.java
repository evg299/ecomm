package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Order;

/**
 * Created by Evgeny on 14.08.14.
 */
@Service
public class OrderService extends AbstractService
{
    @Transactional(readOnly = true)
    public Order getByUuid(String uuid)
    {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("uuid", uuid));
        return (Order) criteria.uniqueResult();
    }
}

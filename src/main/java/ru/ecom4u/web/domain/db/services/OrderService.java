package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Order;
import ru.ecom4u.web.domain.db.entities.OrderStatus;
import ru.ecom4u.web.domain.db.entities.Person;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<Order> getOrdersByPerson(Person person)
    {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.add(Restrictions.eq("person", person));
        criteria.addOrder(org.hibernate.criterion.Order.asc("creationDate"));
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
}

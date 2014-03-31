package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Unit;

import java.util.List;

/**
 * Created by Evgeny(e299792459@gmail.com) on 30.03.14.
 */
@Service
public class UnitService extends AbstractService {

    @Transactional(readOnly = true)
    public List<Unit> getAll(){
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Unit.class);
        return criteria.list();
    }
}

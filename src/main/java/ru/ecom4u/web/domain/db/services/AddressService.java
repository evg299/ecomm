package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import ru.ecom4u.web.domain.db.entities.AddressCountry;
import ru.ecom4u.web.domain.db.entities.AddressState;

/**
 * Created by Evgeny on 12.08.14.
 */
@Service
public class AddressService extends AbstractService
{
    public AddressCountry getAddressCountry(String name)
    {
        Criteria criteria = getCurrentSession().createCriteria(AddressCountry.class);
        criteria.add(Restrictions.eq("name", name).ignoreCase());
        AddressCountry result = (AddressCountry) criteria.uniqueResult();
        if (null != result)
            return result;
        else
        {
            result = new AddressCountry();
            result.setName(name);
            getCurrentSession().save(result);
            return result;
        }
    }

    public AddressState getAddressState(String name, AddressCountry country)
    {
        Criteria criteria = getCurrentSession().createCriteria(AddressCountry.class);
        criteria.add(Restrictions.eq("name", name).ignoreCase());
        criteria.add(Restrictions.eq("addressCountry", country));
        AddressState result = (AddressState) criteria.uniqueResult();
        if (null != result)
            return result;
        else
        {
            result = new AddressState();
            result.setName(name);
            result.setAddressCountry(country);
            getCurrentSession().save(result);
            return result;
        }
    }
}

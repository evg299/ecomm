package ru.ecom4u.web.domain.db.services;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ecom4u.web.domain.db.entities.Address;
import ru.ecom4u.web.domain.db.entities.AddressCountry;
import ru.ecom4u.web.domain.db.entities.AddressState;

/**
 * Created by Evgeny on 12.08.14.
 */
@Service
public class AddressService extends AbstractService
{
    @Transactional()
    public AddressCountry getAddressCountry(String name)
    {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(AddressCountry.class);
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

    @Transactional()
    public AddressState getAddressState(String name, AddressCountry country)
    {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(AddressState.class);
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

    @Transactional()
    public Address getOrCreateAddress(String addr, String apartments, String hierarhyJson, AddressState state)
    {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Address.class);
        criteria.add(Restrictions.eq("address", addr).ignoreCase());
        criteria.add(Restrictions.eq("addressState", state));

        if(null != apartments){
            criteria.add(Restrictions.eq("apartments", apartments).ignoreCase());
        } else {
            criteria.add(Restrictions.isNull("apartments"));
        }

        Address result = (Address) criteria.uniqueResult();
        if (null != result)
            return result;
        else
        {
            result = new Address();
            result.setAddress(addr);
            result.setApartments(apartments);
            result.setAddressState(state);
            result.setHierarhyJson(hierarhyJson);
            getCurrentSession().save(result);
            return result;
        }
    }
}
